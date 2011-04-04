package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.element.*;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphTraversalUtils;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.03.2010
 * Time: 18:26:56
 */
public class RectangularSpiralLayout extends AbstractLayout {

    public static final Logger LOGGER = Logger.getLogger(RectangularSpiralLayout.class);

    public static final double DEFAULT_PATH_DISTANCE = 0.045;
    public static final double DEFAULT_NODE_DISTANCE = 0.02;

    public static final double DEFAULT_START_X_POSITION = 0;
    public static final double DEFAULT_START_Y_POSITION = 0;

    protected double pathDistance = DEFAULT_PATH_DISTANCE;
    protected double nodeDistance = DEFAULT_PATH_DISTANCE / 2;

    protected Point2D pathStartPosition = new Point2D.Double(DEFAULT_START_X_POSITION, DEFAULT_START_Y_POSITION);

    protected int maxGroupElementSize = Integer.MIN_VALUE;
    protected int minGroupElementSize = Integer.MAX_VALUE;

    protected enum Direction {
        RIGHT, UP, LEFT, DOWN

    }

    public RectangularSpiralLayout(Graph graph, Container root) {
        super(graph, root);
    }

    public void compute() {
        LOGGER.info("Computing layout..");

        computeVertexPosition();
        normalizeGroupingElementsSize();

        computeEdgePositions();
    }

    private void computeVertexPosition() {
        List<Object> path = GraphUtils.getLongestPath(getGraph());

        int elements = 3;
        int current = 0;

        Direction direction = Direction.RIGHT;
        Point2D vector = new Point.Double(DEFAULT_PATH_DISTANCE, 0);

        Point2D pathPosition = new Point.Double(pathStartPosition.getX(), pathStartPosition.getY());
        Point2D nodePosition = new Point.Double(pathStartPosition.getX() + nodeDistance, pathStartPosition.getY() - nodeDistance);

        for (Object node : path) {

            root.getObjects().add(node);

            if (GraphUtils.isLeaf(getGraph(), node)) {

                addLeaf(node, pathPosition);

            } else {
                addNode(node, pathPosition);

                for (Object successor : getGraph().getSuccessors(node)) {
                    if (!path.contains(successor)) {

                        if (GraphUtils.isLeaf(getGraph(), successor)) {

                            addLeaf(successor, nodePosition);

                        } else {

                            addGroupingNode(successor, nodePosition);

                        }

                        root.getObjects().add(successor);
                    }
                }

            }

            if (current < elements) {
                if (isChangeDirection(current, elements)) {
                    direction = changeDirection(vector, direction);
                }

                current++;

                move(pathPosition, vector);
                move(nodePosition, vector);
            } else {
                elements += 2;
                current = 0;
                direction = changeDirection(vector, direction);

                move(pathPosition, vector);
                move(nodePosition, vector);
            }
        }
    }

    private void addGroupingNode(Object o, Point2D p) {
        GroupingElement groupingElement = GroupingElement.init(o, p, null, GraphTraversalUtils.dfs(graph, o));

        root.addElement(groupingElement);

        maxGroupElementSize = Math.max(maxGroupElementSize, groupingElement.getSize());
        minGroupElementSize = Math.min(minGroupElementSize, groupingElement.getSize());
    }

    private void addNode(Object o, Point2D p) {
        root.addElement(VertexElement.init(o, p, ElementVisualizerFactory.getInstance().getPointVisualizer()));
    }

    private void addLeaf(Object o, Point2D p) {
        root.addElement(VertexElement.init(o, p, ElementVisualizerFactory.getInstance().getCircleVisualizer()));
    }

    protected boolean isChangeDirection(int current, int length) {
        return current == (length / 2);
    }

    protected void move(Point2D p, Point2D vector) {
        p.setLocation(p.getX() + vector.getX(), p.getY() + vector.getY());
    }

    protected Direction changeDirection(Point2D vector, Direction direction) {
        if (direction == Direction.RIGHT) { // was RIGHT? set UP
            vector.setLocation(0, pathDistance);
            return Direction.UP;
        }

        if (direction == Direction.UP) { // was UP? set LEFT
            vector.setLocation(-pathDistance, 0);
            return Direction.LEFT;
        }

        if (direction == Direction.LEFT) { // was LEFT? set DOWN
            vector.setLocation(0, -pathDistance);
            return Direction.DOWN;
        }

        if (direction == Direction.DOWN) { // was DOWN? set RIGHT
            vector.setLocation(pathDistance, 0);
            return Direction.RIGHT;
        }

        return null;
    }

    private void normalizeGroupingElementsSize() {
        for (Iterator<Element> i = root.getElements(); i.hasNext();) {
            Element element = i.next();

            if (element instanceof GroupingElement) {
                ((GroupingElement) element).setVisualizer(ElementVisualizerFactory.getInstance().getRectVisualizer(minGroupElementSize, maxGroupElementSize, ((Container) element).getSize()));
            }
        }
    }

    private void computeEdgePositions() {
        for (Object edge : getGraph().getEdges()) {
            Object source = graph.getSource(edge);
            Object dest = graph.getDest(edge);

            if (root.has(source) && root.has(dest)) { // is this edge part of the backbone spiral?

                // is it backbone edge?
                if (root.getElementByObject(source).getType() != ElementType.CONTAINER && root.getElementByObject(dest).getType() != ElementType.CONTAINER) {

                    root.addElement(BackboneEdgeElement.init(edge, graph, root));

                } else { // it's edge from backbone to grouping element
                    root.addElement(EdgeElement.init(edge, graph, root, ElementVisualizerFactory.getInstance().getLineEdgeVisializer()));
                }

            }
        }
    }

    public double getPathDistance() {
        return pathDistance;
    }

    public void setPathDistance(double pathDistance) {
        this.pathDistance = pathDistance;
    }

    public Point2D getPathStartPosition() {
        return pathStartPosition;
    }

    public void setPathStartPosition(Point2D pathStartPosition) {
        this.pathStartPosition = pathStartPosition;
    }

}