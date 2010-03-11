package layout;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.geom.Point2D;

public class RadialLayoutV2<V,E> extends CircleLayout<V,E> {

    public static final double PIdev180 = 0.017444;

    protected Map<V, Double> node_angle;
    protected Map<V, Integer> node_level;

    private double radius;

    protected int level;

    Stack stack;
    Set visited;
    List result;

    public RadialLayoutV2(Graph graph) {
        super(graph);

        node_angle = new HashMap<V, Double>();
        node_level = new HashMap<V, Integer>();

        stack = new Stack();
        visited = new HashSet();
        result = new ArrayList();
    }


    public void initialize() {
        //main work here

        node_angle.clear();
        node_level.clear();

        double height = getSize().getHeight();
        double width = getSize().getWidth();

        if (getRadius() <= 0) {
            setRadius(0.45 * (height < width ? height : width));
        }

        V root = getRoot();
        if (root == null) {
            throw new IllegalArgumentException("No root node! Cannt draw unrooted draph..");
        }

        computeAngles(root, 0, 360);

        computeLevels();

        computePositions();

        positRoot(root);

    }


    private void computeLevels() {

        int currentLevel = 0;

        for (V node : getGraph().getVertices()) {
            List<V> list = invertDfsNodes(node);

            currentLevel = list.size();

            if (node_level.get(node) != null) {
                if (currentLevel > node_level.get(node)) {
                    node_level.put(node, currentLevel);
                }
            } else {
                node_level.put(node, currentLevel);
            }

            if (currentLevel > level) {
                level = currentLevel;
            }
        }

        System.out.println("Graph level " + level);
    }

    public List<V> invertDfsNodes(V node) {

        stack.clear();
        visited.clear();
        result.clear();


        stack.push(node);

        while (!stack.isEmpty()) {
            V next = (V) stack.pop();

            if (visited.add(next)) {

                result.add(next);

                for (V neighbor : getGraph().getPredecessors(next)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }

            }
        }

        return result;
    }

    public List<V> dfsNodes(V node) {

        stack.clear();
        visited.clear();
        result.clear();


        stack.push(node);

        while (!stack.isEmpty()) {
            V next = (V) stack.pop();

            if (visited.add(next)) {

                result.add(next);

                for (V neighbor : getGraph().getSuccessors(next)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }

            }
        }

        return result;
    }

    private void positRoot(V root) {
        Point2D coord = transform(root);
        coord.setLocation(getSize().getWidth()  / 2, getSize().getHeight() / 2);
    }

    protected void computeAngles(V node, double from, double to) {

        node_angle.put(node, from + ((to - from) / 2.0));

/*
        if (getGraph().outDegree(node) == 0) {
            System.out.print("*");
        }

        System.out.println("'" + node + "' [" + from + " to " + to + "] ~" + (to-from) + " =" + node_angle.get(node));
*/

        double childCount = getGraph().getSuccessorCount(node);

        if (childCount > 0) {
            double range = (to - from) / childCount;

            int i=0;
            for (V child : getGraph().getSuccessors(node)) {

                double start = from + (range * i);
                computeAngles(child, start, start+range);

                i++;
            }
        }




    }

    protected void computePositions() {
        for (V node : getGraph().getVertices()) {
            Point2D coord = transform(node);

            double angle = node_angle.get(node);

            double x = Math.cos(toRadians(angle)) * nodeRadius(node) + getSize().getWidth() / 2;
            double y = Math.sin(toRadians(angle)) * nodeRadius(node) + getSize().getHeight() / 2;

/*
            double x = Math.cos(angle) * nodeRadius(node) + getSize().getWidth() / 2;
            double y = Math.sin(angle) * nodeRadius(node) + getSize().getHeight() / 2;
*/

       //     System.out.println(angle + " [" + x + "," + y + "]");

            coord.setLocation(x,y);
        }
    }

    private double nodeRadius(V node) {
//        if (getGraph().outDegree(node) == 0) {
  //          return getRadius();
    //    } else {
            return getRadius() / level * node_level.get(node);
      //  }
    }

    private double toRadians(double angle) {
        return angle * PIdev180;
    }

    protected V getRoot() {
        for (V v : getGraph().getVertices()) {
            if (getGraph().inDegree(v) == 0) {
                return v;
            }
        }

        return null;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}