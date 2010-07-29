package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.EdgeElement;
import se.lnu.thesis.paint.element.GroupElement;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.element.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.03.2010
 * Time: 18:26:56
 */
public class RectangularSpiralLayout extends AbstractLayout {


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

    public RectangularSpiralLayout(Graph graph, GroupElement root) {
        super(graph, root);
    }

    public void compute() {
        computeVertexPosition();

        computeEdgePositions();
    }

    private void computeEdgePositions() {
        for (Object edge : getGraph().getEdges()) {
            Object source = graph.getSource(edge);
            Object dest = graph.getDest(edge);

            if (root.has(source) && root.has(dest)) {

                EdgeElement edgeElement = new EdgeElement();
                edgeElement.setId(Utils.nextId());
                edgeElement.setObject(edge);
                edgeElement.setFrom(source);
                edgeElement.setTo(dest);
                edgeElement.setDraw(false);
                edgeElement.setStartPosition(((VertexElement) (root.getElementByObject(source))).getPosition());
                edgeElement.setEndPosition(((VertexElement) (root.getElementByObject(dest))).getPosition());
                edgeElement.setElementVisualizer(ElementVisualizerFactory.getInstance().getLineEdgeVisializer());

                root.addElement(edgeElement);
            }
        }
    }

    private void computeVertexPosition() {
        List<Object> path = GraphUtils.getInstance().longestPath(getGraph());

        int elements = 3;
        int current = 0;

        Direction direction = Direction.RIGHT;
        Point2D vector = new Point.Double(DEFAULT_PATH_DISTANCE, 0);

        Point2D pathPosition = new Point.Double(pathStartPosition.getX(), pathStartPosition.getY());
        Point2D nodePosition = new Point.Double(pathStartPosition.getX() + nodeDistance, pathStartPosition.getY() - nodeDistance);

        for (Object node : path) {

            if (GraphUtils.getInstance().isLeaf(getGraph(), node)) {
                root.addElement(new VertexElement(getGraph(), node, pathPosition, ElementVisualizerFactory.getInstance().getCircleVisualizer()));
            } else {
                root.addElement(new VertexElement(getGraph(), node, pathPosition, ElementVisualizerFactory.getInstance().getPointVisualizer()));

                for (Object successor : getGraph().getSuccessors(node)) {
                    if (!path.contains(successor)) {
                        if (GraphUtils.getInstance().isLeaf(getGraph(), successor)) {
                            root.addElement(new VertexElement(getGraph(), successor, nodePosition, ElementVisualizerFactory.getInstance().getCircleVisualizer()));
                        } else {
                            GroupElement groupElement = new GroupElement(getGraph(), successor, nodePosition, null);
                            root.addElement(groupElement);

                            int groupSize = groupElement.getSize();

                            if (groupSize > this.maxGroupSize) {
                                this.maxGroupSize = groupSize;
                            }
                        }
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

    public int getMaxGroupSize() {
        return this.maxGroupSize;
    }
}