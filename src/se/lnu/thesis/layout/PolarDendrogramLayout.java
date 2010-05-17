package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;
import java.util.*;

public class PolarDendrogramLayout<V, E> extends RadialLayout<V, E> {

    public static final Logger LOGGER = Logger.getLogger(PolarDendrogramLayout.class);

    protected Map<V, Integer> nodeLevel;

    protected int graphHeight;

    protected V root;

    protected List<V> leafs;

    protected Map<V, Double> nodeAngle;

    protected float xCenter;
    protected float yCenter;

    public PolarDendrogramLayout(Graph graph) {
        super(graph);
    }


    public void initialize() {
        //main work here

        init();

        extractLeafs();

        arrangeLeafs(new NodeLevelComparator<V>());

        computeLeafAngles();

        computeNodeAngles(getRoot());

        computePositions();

        setRootCoordinate(getRoot());
    }

    protected void init() {
        if (nodeAngle == null) {
            nodeAngle = new HashMap<V, Double>();
        } else {
            nodeAngle.clear();
        }

        if (nodeLevel == null) {
            nodeLevel = new HashMap<V, Integer>();
        } else {
            nodeLevel.clear();
        }

        root = (V) GraphUtils.getInstance().findRoot(getGraph());
        if (root == null) {
            throw new IllegalArgumentException("No root vertex! Cannt draw unrooted graph..");
        }

        graphHeight = GraphUtils.getInstance().computeLevelsV2(getGraph(), nodeLevel);
    }

    protected void extractLeafs() {
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
    }

    protected void arrangeLeafs(Comparator<V> leafsComparator) {
        Collections.sort(leafs, leafsComparator);
    }

    protected void computeLeafAngles() {

        double step = 360.0 / leafs.size();

        int i = 0;
        for (V leaf : leafs) {
            double angle = step * i++;

            nodeAngle.put(leaf, angle);
        }
    }

    protected double computeNodeAngles(V node) {
        if (nodeAngle.containsKey(node)) {
            return nodeAngle.get(node);
        } else {

            double min = 360.0;
            double max = 0.0;

            double angle;
            for (V child : getGraph().getSuccessors(node)) {
                angle = computeNodeAngles(child);

                if (angle > max) {
                    max = angle;
                }

                if (angle < min) {
                    min = angle;
                }
            }

            angle = (max + min) / 2;
            nodeAngle.put(node, angle);

            return angle;
        }

    }


    protected void computePositions() {
        for (V node : getGraph().getVertices()) {
            setNodeCoordinate(node);
        }

    }

    protected void setRootCoordinate(V root) {
        transform(root).setLocation(xCenter, yCenter);
    }

    public double getNodeRadius(V node) {
        if (getGraph().outDegree(node) == 0) {
            return getRadius();
        } else {
            return getRadius() / getGraphHeight() * getNodeLevel(node);
        }
    }

    protected void setNodeCoordinate(V node) {

        Point2D point = transform(node);

        double angle = nodeAngle.get(node);
        double radius = getNodeRadius(node);

        Utils.computePosition(point, angle, radius, xCenter, yCenter);
    }

    public Point2D getDummyNode(V startNode, V endNode) {
        Point2D result = new Point2D.Double();

        double angle = nodeAngle.get(endNode);
        double radius = getNodeRadius(startNode);

        Utils.computePosition(result, angle, radius, xCenter, yCenter);

        return result;
    }

    public List<V> getLeafs() {
        return leafs;
    }

    public Map<V, Double> getNodeAngle() {
        return nodeAngle;
    }

    public void setNodeAngle(Map<V, Double> nodeAngle) {
        this.nodeAngle = nodeAngle;
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

    protected V getRoot() {
        return root;
    }

    public int getNodeLevel(V node) {
        return nodeLevel.get(node);
    }


    public int getGraphHeight() {
        return graphHeight;
    }

    public Map<V, Integer> getNodeLevel() {
        return nodeLevel;
    }

    public void setCenter(float x, float y) {
        setXCenter(x);
        setYCenter(y);
    }

    private class NodeLevelComparator<T> implements Comparator<T> {

        public int compare(T node1, T node2) {

            Integer levelNode1 = PolarDendrogramLayout.this.getNodeLevel((V) node1);
            Integer levelNode2 = PolarDendrogramLayout.this.getNodeLevel((V) node2);

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

