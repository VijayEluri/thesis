package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.paint.element.EdgeElement;
import se.lnu.thesis.paint.element.GroupElement;
import se.lnu.thesis.paint.element.PolarDendrogramEdgeElement;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.element.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;
import java.util.*;

public class PolarDendrogramLayout extends RadialLayout {

    public static final Logger LOGGER = Logger.getLogger(PolarDendrogramLayout.class);

    protected Map<Object, Integer> nodeLevel;

    protected int maxDepth = 0;

    protected List<Object> leafs;
    private List<Object> nodes;

    protected Map<Object, Double> nodeAngle;
    protected Map<Object, Point2D> nodePosition;

    protected float xCenter;
    protected float yCenter;


    public PolarDendrogramLayout() {

    }

    public PolarDendrogramLayout(Graph graph, GroupElement root) {
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

        arrangeLeafs(new NodeLevelComparator());

        computeLeafAngles();

        computeNodeAngles(root.getObject());

        computePositions();

        setRootCoordinate();

        computeEdges();
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

        if (nodes == null) {
            nodes = new LinkedList<Object>();
        } else {
            nodeLevel.clear();
        }
    }


    private void computeLevels() {
        for (Object node : root.getNodes()) {
            int height = GraphUtils.getInstance().getNodeHeight(graph, node, root.getObject(), 0);
            nodeLevel.put(node, height);

            if (height > maxDepth) {
                maxDepth = height;
            }
        }
    }

    protected void extractLeafs() {
        if (leafs == null) {
            leafs = new LinkedList<Object>();
        } else {
            leafs.clear();
        }

//        nodes = GraphUtils.getInstance().dfsNodes(graph, root.getObject());
        nodes = root.getNodes();

        for (Object node : nodes) {
            if (getGraph().outDegree(node) == 0) {
                leafs.add(node);
            }
        }
    }

    protected void arrangeLeafs(Comparator<Object> leafsComparator) {
        Collections.sort(leafs, leafsComparator);
    }

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
        for (Object node : nodes) {
            setNodeCoordinate(node);
        }

    }

    protected void setNodeCoordinate(Object node) {

        double angle = nodeAngle.get(node);
        double radius = getNodeRadius(node);

        Point2D position = new Point2D.Double();
        Utils.computePosition(position, angle, radius, xCenter, yCenter);

        //nodePosition.put(node, position);
        root.addElement(new VertexElement(node, position, ElementVisualizerFactory.getInstance().getCircleVisualizer()));
    }

    protected void setRootCoordinate() {
        Point2D position = new Point2D.Double(xCenter, yCenter);

        //nodePosition.put(root, position);
        root.addElement(new VertexElement(root.getObject(), position, ElementVisualizerFactory.getInstance().getCircleVisualizer()));
    }

    // TODO implement it!
    private void computeEdges() {
        for (Object edge : getGraph().getEdges()) {
            Object source = graph.getSource(edge);
            Object dest = graph.getDest(edge);

            if (root.has(source) && root.has(dest)) {
                root.addElement(createEdge(edge, source, dest));
                //root.addElement(createPolarEdge(edge, source, dest));
            }
        }
    }

    private PolarDendrogramEdgeElement createPolarEdge(Object edge, Object source, Object dest) {
        PolarDendrogramEdgeElement resultEdgeElement = new PolarDendrogramEdgeElement();

        resultEdgeElement.setId(Utils.nextId());
        resultEdgeElement.setObject(edge);

        resultEdgeElement.setFrom(source);
        resultEdgeElement.setTo(dest);

        resultEdgeElement.setFromRoot(GraphUtils.getInstance().isRoot(graph, source));
        resultEdgeElement.setDraw(true);

        resultEdgeElement.setStartPosition(((VertexElement) (root.getElementByObject(source))).getPosition());
        resultEdgeElement.setDummyNode(getDummyNode(source, dest));
        resultEdgeElement.setEndPosition(((VertexElement) (root.getElementByObject(dest))).getPosition());

        resultEdgeElement.setSourceRadius(getNodeRadius(source));

        resultEdgeElement.setSourceAngle(getNodeAngle().get(source));
        resultEdgeElement.setDestAngle(getNodeAngle().get(dest));

        resultEdgeElement.setElementVisualizer(ElementVisualizerFactory.getInstance().getPolarDendrogramEdgeVisializer());

        return resultEdgeElement;
    }

    private EdgeElement createEdge(Object edge, Object source, Object dest) {
        EdgeElement resultEdgeElement = new EdgeElement();

        resultEdgeElement.setId(Utils.nextId());
        resultEdgeElement.setObject(edge);

        resultEdgeElement.setFrom(source);
        resultEdgeElement.setTo(dest);

        resultEdgeElement.setDraw(true);

        resultEdgeElement.setStartPosition(((VertexElement) (root.getElementByObject(source))).getPosition());
        resultEdgeElement.setEndPosition(((VertexElement) (root.getElementByObject(dest))).getPosition());

        resultEdgeElement.setElementVisualizer(ElementVisualizerFactory.getInstance().getLineEdgeVisializer());

        return resultEdgeElement;
    }

    public double getNodeRadius(Object node) {
        if (getGraph().outDegree(node) == 0) {
            return getRadius();
        } else {
            return getRadius() / getMaxDepth() * getNodeLevel(node);
        }
    }

    public Point2D getDummyNode(Object startNode, Object endNode) {
        Point2D result = new Point2D.Double();

        double angle = nodeAngle.get(endNode);
        double radius = getNodeRadius(startNode);

        Utils.computePosition(result, angle, radius, xCenter, yCenter);

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


    public int getNodeLevel(Object node) {
        return nodeLevel.get(node);
    }


    public int getMaxDepth() {
        return maxDepth;
    }

    public Map<Object, Integer> getNodeLevel() {
        return nodeLevel;
    }

    public void setCenter(float x, float y) {
        setXCenter(x);
        setYCenter(y);
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

