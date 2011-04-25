package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.element.PolarEdge;
import se.lnu.thesis.element.PolarVertex;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;
import static se.lnu.thesis.utils.GraphUtils.isLeaf;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;
import java.util.*;

public class PolarDendrogramLayout extends RadialLayout {

    public static final Logger LOGGER = Logger.getLogger(PolarDendrogramLayout.class);

    protected Map<Object, Integer> nodeLevel;

    protected int graphHeight = 0;

    protected List<Object> leafs;

    protected Map<Object, Double> nodeAngle;

    protected Point2D.Double center = new Point2D.Double(0, 0);


    public PolarDendrogramLayout() {

    }

    public PolarDendrogramLayout(Graph graph, GroupingElement root) {
        super(graph, root);
    }

    public PolarDendrogramLayout(double radius) {
        setRadius(radius);
    }


    public void compute() {
        //main work here

        init();

        computeLevels();

        extractLeafs();

        //     arrangeLeafByLevel(new NodeLevelComparator());

        computeLeafAngles();

        computeNodeAngles(root.getObject());

        computePositions();

        //    setRootCoordinate();

        computeEdges();

        root.setLayoutComputed(true);
    }

    protected void init() {
        if (graph == null) {
            throw new IllegalStateException("No graph!");
        }

        if (root == null) {
            throw new IllegalStateException("No root set for computing layout!");
        }


        if (nodeAngle == null) {
            nodeAngle = new HashMap<Object, Double>();
        } else {
            nodeAngle.clear();
        }

        if (nodeLevel == null) {
            nodeLevel = new HashMap<Object, Integer>();
        } else {
            nodeLevel.clear();
        }

        if (leafs == null) {
            leafs = new LinkedList<Object>();
        } else {
            leafs.clear();
        }
    }


    private void computeLevels() {
        for (Object node : root.getObjects()) {
            int height = GraphUtils.getDistance(graph, node, root.getObject());
            nodeLevel.put(node, height);

            if (height > graphHeight) {
                graphHeight = height;
            }
        }
    }

    protected void extractLeafs() {
        GraphUtils.getLeafs(graph, root.getObject(), leafs);
    }

/*
    protected void arrangeLeafByLevel(Comparator<Object> levelComparator) {
        Collections.sort(leafs, levelComparator);
    }
*/

    protected void computeLeafAngles() {

        double step = 360.0 / leafs.size();

        int i = 0;
        for (Object leaf : leafs) {
            double angle = step * i++;

            nodeAngle.put(leaf, angle);
        }
    }

    protected double computeNodeAngles(Object node) {
        if (nodeAngle.containsKey(node)) {
            return nodeAngle.get(node);
        } else {

            double min = 360.0;
            double max = 0.0;

            double angle;
            for (Object child : getGraph().getSuccessors(node)) {
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
        for (Object node : root.getObjects()) {
            setNodeCoordinate(node);
        }

    }

    protected void setNodeCoordinate(Object o) {

        double angle = nodeAngle.get(o);
        double radius = getNodeRadius(o);

        PolarVertex polarVertex = PolarVertex.init(o, angle, radius, center, ElementVisualizerFactory.getInstance().getCircleVisualizer());
        polarVertex.setTooltip(((MyGraph) getGraph()).getLabel(o));

        root.addElement(polarVertex);
    }

/*
    protected void setRootCoordinate() {
        root.addElement(VertexElement.init(root.getObject(), center, ElementVisualizerFactory.getInstance().getCircleVisualizer()));
    }
*/

    private void computeEdges() {
        for (Object edge : getGraph().getEdges()) {
            Object source = graph.getSource(edge);
            Object dest = graph.getDest(edge);

            if (root.has(source) && root.has(dest)) {
                root.addElement(PolarEdge.init(this, edge, source, dest));
            }
        }
    }

    public double getNodeRadius(Object node) {
        if (isLeaf(graph, node)) {
            return getRadius();
        } else {
            return getRadius() / getGraphHeight() * getNodeLevel(node);
        }
    }

    public Point2D getDummyNode(Object startNode, Object endNode) {
        Point2D result = new Point2D.Double();

        double angle = nodeAngle.get(endNode);
        double radius = getNodeRadius(startNode);

        Utils.computeOnCirclePosition(result, angle, radius, center.getX(), center.getY());

        return result;
    }

    public List<Object> getLeafs() {
        return leafs;
    }

    public Map<Object, Double> getNodeAngle() {
        return nodeAngle;
    }

    public void setNodeAngle(Map<Object, Double> nodeAngle) {
        this.nodeAngle = nodeAngle;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D.Double center) {
        this.center = center;
    }

    public void setCenter(double x, double y) {
        setCenter(new Point2D.Double(x, y));
    }

    public int getNodeLevel(Object node) {
        return nodeLevel.get(node);
    }


    public int getGraphHeight() {
        return graphHeight;
    }

    public Map<Object, Integer> getNodeLevel() {
        return nodeLevel;
    }


    private class NodeLevelComparator implements Comparator {

        public int compare(Object node1, Object node2) {

            Integer levelNode1 = PolarDendrogramLayout.this.getNodeLevel(node1);
            Integer levelNode2 = PolarDendrogramLayout.this.getNodeLevel(node2);

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

