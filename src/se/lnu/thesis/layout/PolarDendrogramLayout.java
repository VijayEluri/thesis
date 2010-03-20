package se.lnu.thesis.layout;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;
import java.util.*;

public class PolarDendrogramLayout<V, E> extends CircleLayout<V, E> {

    public static final Logger LOGGER = Logger.getLogger(PolarDendrogramLayout.class);

    protected Map<V, Double> node_angle;
    protected Map<V, Integer> node_level;

    protected List<V> leafs;

    protected int graphHeight;

    protected float xCenter;
    protected float yCenter;


    public PolarDendrogramLayout(Graph graph) {
        super(graph);              // TODO maybe use Tree insteed of Graph??

        node_angle = new HashMap<V, Double>();
        node_level = new HashMap<V, Integer>();
    }


    public void initialize() {
        //main work here

        node_angle.clear();
        node_level.clear();

        V root = (V) GraphUtils.getInstance().findRoot(getGraph());
        if (root == null) {
            throw new IllegalArgumentException("No root node! Cannt draw unrooted graph..");
        }

        graphHeight = GraphUtils.getInstance().computeLevelsV2(getGraph(), node_level);

        computeLeafAngles();

        computeAngles(root);

        computePositions();

        setRootCoordinate(root);

    }

    private void computeLeafAngles() {

        arrangeLeafs();

        double step = 360.0 / leafs.size();

        int i = 0;
        for (V leaf : leafs) {
            double angle = step * i++;

            node_angle.put(leaf, angle);
        }

    }

    private void arrangeLeafs() {
        if (leafs == null) {
            leafs = new LinkedList<V>();
        } else {
            leafs.clear();
        }

        for (V node : getGraph().getVertices()) {
            if (getGraph().outDegree(node) == 0) {
                leafs.add(node);
            }
        }

        Collections.sort(leafs, new LevelComparator<V>());
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

        Point2D point = transform(node);

        double angle = node_angle.get(node);
        double radius = getNodeRadius(node);

        Utils.computePosition(point, angle, radius, xCenter, yCenter);

    }

    public Point2D.Float getDummyNode(V startNode, V endNode) {
        Point2D.Float result = new Point2D.Float();

        double angle = node_angle.get(endNode);
        double radius = getNodeRadius(startNode);

        Utils.computePosition(result, angle, radius, xCenter, yCenter);

        return result;
    }

    private void setRootCoordinate(V root) {
        transform(root).setLocation(xCenter, yCenter);
    }

    public double getNodeRadius(V node) {
        if (getGraph().outDegree(node) == 0) {
            return getRadius();
        } else {
            return getRadius() / graphHeight * node_level.get(node);
        }
    }

    public int getGraphHeight() {
        return graphHeight;
    }

    public Map<V, Double> getNode_angle() {
        return node_angle;
    }

    public void setNode_angle(Map<V, Double> node_angle) {
        this.node_angle = node_angle;
    }

    public Map<V, Integer> getNode_level() {
        return node_level;
    }

    public void setNode_level(Map<V, Integer> node_level) {
        this.node_level = node_level;
    }

    public List<V> getLeafs() {
        return leafs;
    }

    public float getXCenter() {
        return xCenter;
    }

    public void setXCenter(float xCenter) {
        this.xCenter = xCenter;
    }

    public float getYCenter() {
        return yCenter;
    }

    public void setYCenter(float yCenter) {
        this.yCenter = yCenter;
    }

    private class LevelComparator<T> implements Comparator<T> {

        public int compare(T node1, T node2) {

            Integer levelNode1 = PolarDendrogramLayout.this.getNode_level().get(node1);
            Integer levelNode2 = PolarDendrogramLayout.this.getNode_level().get(node2);

            if (levelNode1 < levelNode2) {
                return -1;
            }

            if (levelNode1 > levelNode2) {
                return 1;
            }

            return 0;
        }
    }

}

