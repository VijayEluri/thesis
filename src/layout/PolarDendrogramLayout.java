package layout;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import graph.GraphUtils;
import utils.Utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PolarDendrogramLayout<V, E> extends CircleLayout<V, E> {

    protected Map<V, Double> node_angle;
    protected Map<V, Integer> node_level;

    protected int levels;


    public PolarDendrogramLayout(Graph graph) {
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
            throw new IllegalArgumentException("No root node! Cannt draw unrooted graph..");
        }

        computeLeafPositions(root);

        computeLevels(); // TODO delete it when use Tree insteed of Graph

        //node_angle.put(root, 0.0);

        computeAngles(root);

        computePositions();

        setRootCoordinate(root);

    }

    private void computeLeafPositions(V root) {

        List<V> leafs = new LinkedList<V>();

        /*
            TODO maybe use this way without dfs?
            for (V node : getGraph().getVertices()) {
         */

        List<V> result = GraphUtils.getInstance().dfsNodes(getGraph(), root);
        for (V node : result) {
            if (getGraph().outDegree(node) == 0) { // is it leaf?
                leafs.add(node);
            } else {
                transform(node).setLocation(0, 0);
            }
        }


        double step = 360.0 / leafs.size();

        for (int i = 0; i < leafs.size(); i++) {
            V leaf = leafs.get(i);

            double angle = step * i;

            node_angle.put(leaf, angle);
            //    setNodeCoordinate(leaf);
        }


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

            if (currentLevel > levels) {
                levels = currentLevel;
            }
        }
    }

    protected double computeAngles(V node) {
        if (node_angle.containsKey(node)) {
            return node_angle.get(node);
        } else {

            double min = 360.0;
            double max = 0.0;

            double angle;
            for (V child : getGraph().getSuccessors(node)) {
                angle = computeAngles(child);

                if (angle > max) {
                    max = angle;
                }

                if (angle < min) {
                    min = angle;
                }
            }

            angle = (max + min) / 2;
            node_angle.put(node, angle);

            return angle;
        }

    }


    protected void computePositions() {
        for (V node : getGraph().getVertices()) {
            setNodeCoordinate(node);
        }

    }


    private void setNodeCoordinate(V node) {

        double angle = node_angle.get(node);
        double radius = nodeRadius(node);

        double x = Math.cos(Utils.inRadians(angle)) * radius + getSize().getWidth() / 2;
        double y = Math.sin(Utils.inRadians(angle)) * radius + getSize().getHeight() / 2;

        transform(node).setLocation(x, y);

        System.out.println(node + " " + angle);
    }

    private void setRootCoordinate(V root) {
        transform(root).setLocation(getSize().getWidth() / 2, getSize().getHeight() / 2);
    }

    private double nodeRadius(V node) {
        if (getGraph().outDegree(node) == 0) {
            return getRadius();
        } else {
            return getRadius() / levels * node_level.get(node);
        }
    }

    public int getLevels() {
        return levels;
    }
}