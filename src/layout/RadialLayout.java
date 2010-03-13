package layout;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import graph.GraphUtils;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadialLayout<V, E> extends CircleLayout<V, E> {

    public static final double PIdev180 = 0.017444;

    protected Map<V, Double> node_angle;
    protected Map<V, Integer> node_level;

    private double radius;

    protected int level;


    public RadialLayout(Graph graph) {
        super(graph);

        node_angle = new HashMap<V, Double>();
        node_level = new HashMap<V, Integer>();
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

        V root = (V) GraphUtils.getInstance().findRoot(getGraph());
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
            List<V> list = GraphUtils.getInstance().invertDfsNodes(getGraph(), node);

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

        System.out.println("Graph levels " + level);
    }


    private void positRoot(V root) {
        Point2D coord = transform(root);
        coord.setLocation(getSize().getWidth() / 2, getSize().getHeight() / 2);
    }

    protected void computeAngles(V node, double from, double to) {

        node_angle.put(node, from + ((to - from) / 2.0));

        double childCount = getGraph().getSuccessorCount(node);

        if (childCount > 0) {
            double range = (to - from) / childCount;

            int i = 0;
            for (V child : getGraph().getSuccessors(node)) {

                double start = from + (range * i);
                computeAngles(child, start, start + range);

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

            coord.setLocation(x, y);
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


}