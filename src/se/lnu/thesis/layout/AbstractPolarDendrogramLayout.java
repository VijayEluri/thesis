package se.lnu.thesis.layout;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 17:16:43
 * <p/>
 * Basic class for polar dendrogram implementation
 */
public abstract class AbstractPolarDendrogramLayout<V, E> extends CircleLayout<V, E> {

    protected List<V> leafs;

    protected Map<V, Double> nodeAngle;

    protected float xCenter;
    protected float yCenter;


    public AbstractPolarDendrogramLayout(Graph<V, E> graph) {
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
    }

    protected abstract V getRoot();

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

    public abstract int getNodeLevel(V node);

    public abstract int getGraphHeight();

    protected void setNodeCoordinate(V node) {

        Point2D point = transform(node);

        double angle = nodeAngle.get(node);
        double radius = getNodeRadius(node);

        Utils.computePosition(point, angle, radius, xCenter, yCenter);
    }

    public Point2D.Float getDummyNode(V startNode, V endNode) {
        Point2D.Float result = new Point2D.Float();

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

    private class NodeLevelComparator<T> implements Comparator<T> {

        public int compare(T node1, T node2) {

            Integer levelNode1 = AbstractPolarDendrogramLayout.this.getNodeLevel((V) node1);
            Integer levelNode2 = AbstractPolarDendrogramLayout.this.getNodeLevel((V) node2);

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
