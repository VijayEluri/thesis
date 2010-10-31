package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.paint.element.*;
import se.lnu.thesis.paint.element.Container;
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

    public static final double DEFAULT_X_POSITION = 0;
    public static final double DEFAULT_Y_POSITION = 0;

    protected double pathDistance = DEFAULT_PATH_DISTANCE;
    protected double nodeDistance = DEFAULT_PATH_DISTANCE / 2;

    protected Point2D pathStartPosition = new Point2D.Double(DEFAULT_X_POSITION, DEFAULT_Y_POSITION);

    protected int maxGroupSize = 1;

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
                root.addElement(VertexElement.init(node, pathPosition, ElementVisualizerFactory.getInstance().getCircleVisualizer()));
            } else {
                root.addElement(VertexElement.init(node, pathPosition, ElementVisualizerFactory.getInstance().getPointVisualizer()));

                for (Object successor : getGraph().getSuccessors(node)) {
                    if (!path.contains(successor)) {
                        if (GraphUtils.isLeaf(getGraph(), successor)) {
                            root.addElement(VertexElement.init(successor, nodePosition, ElementVisualizerFactory.getInstance().getCircleVisualizer()));
                        } else {
                            GroupingElement groupingElement = GroupingElement.init(successor, nodePosition, null, GraphTraversalUtils.dfs(graph, successor));
                            root.addElement(groupingElement);

                            maxGroupSize = Math.max(maxGroupSize, groupingElement.getSize());
                        }

                        root.getObjects().add(successor);
                    }
                }

            }

            if (current < elements - 1) {
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
            }
        }
    }

    private void normalizeGroupingElementsSize() {
        for (Iterator<Element> i = root.getElements(); i.hasNext();) {
            Element element = i.next();

            if (element instanceof GroupingElement) {
                ((GroupingElement) element).setVisualizer(ElementVisualizerFactory.getInstance().getRectVisualizer(maxGroupSize, ((Container) element).getSize()));
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
                    root.addElement(EdgeElement.init(edge, graph, root));
                }

            }
        }
    }

    protected boolean isChangeDirection(int current, int groupSize) {
        return current == (groupSize / 2);
    }

    protected void move(Point2D p, Point2D vector) {
        p.setLocation(p.getX() + vector.getX(), p.getY() + vector.getY());
    }

    protected Direction changeDirection(Point2D vector, Direction direction) {
        if (direction == Direction.RIGHT) { // was RIGHT? set UP
            vector.setLocation(0, DEFAULT_PATH_DISTANCE);
            return Direction.UP;
        }

        if (direction == Direction.UP) { // was UP? set LEFT
            vector.setLocation(-DEFAULT_PATH_DISTANCE, 0);
            return Direction.LEFT;
        }

        if (direction == Direction.LEFT) { // was LEFT? set DOWN
            vector.setLocation(0, -DEFAULT_PATH_DISTANCE);
            return Direction.DOWN;
        }

        if (direction == Direction.DOWN) { // was DOWN? set RIGHT
            vector.setLocation(DEFAULT_PATH_DISTANCE, 0);
            return Direction.RIGHT;
        }

        return null;
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