package se.lnu.thesis.layout;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.03.2010
 * Time: 18:26:56
 */
public class RectangularSpiralLayout<V, E> extends AbstractLayout<V, E> {

    public static final double DEFAULT_PATH_DISTANCE = 0.045;
    public static final double DEFAULT_NODE_DISTANCE = 0.02;

    public static final double DEFAULT_X_POSITION = 0;
    public static final double DEFAULT_Y_POSITION = 0;

    protected double pathDistance = DEFAULT_PATH_DISTANCE;
    protected double nodeDistance = DEFAULT_PATH_DISTANCE / 2;

    protected Point2D pathStartPosition;

    protected Set vertices;
    protected Set groupVertices;

    protected Map<V, Integer> groupSize;
    protected int maxGroupSize = 1;

    protected enum Direction {
        RIGHT, UP, LEFT, DOWN
    }


    public RectangularSpiralLayout(Graph graph) {
        super(graph);

        pathStartPosition = new Point2D.Double(DEFAULT_X_POSITION, DEFAULT_Y_POSITION);
    }


    public void initialize() {

        List<V> path = GraphUtils.getInstance().longestPath(getGraph());

        initVertices();

        int elements = 3;
        int current = 0;

        Direction direction = Direction.RIGHT;
        Point2D vector = new Point.Double(DEFAULT_PATH_DISTANCE, 0);

        Point2D pathPosition = new Point.Double(pathStartPosition.getX(), pathStartPosition.getY());
        Point2D nodePosition = new Point.Double(pathStartPosition.getX() + nodeDistance, pathStartPosition.getY() - nodeDistance);

        for (V node : path) {
            setLocation(node, pathPosition);
            vertices.add(node);

            if (!GraphUtils.getInstance().isLeaf(getGraph(), node)) {
                for (V successor : getGraph().getSuccessors(node)) {
                    if (!path.contains(successor)) {
                        setLocation(successor, nodePosition);
                        vertices.add(successor);

                        if (!GraphUtils.getInstance().isLeaf(getGraph(), successor)) {
                            groupVertices.add(successor);
                            int groupSize = GraphUtils.getInstance().dfsNodes(getGraph(), successor).size();
                            this.groupSize.put(successor, groupSize);

                            if (groupSize > maxGroupSize) {
                                maxGroupSize = groupSize;
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

    public void reset() {
        initVertices();

        setPathStartPosition(new Point2D.Double(DEFAULT_X_POSITION, DEFAULT_Y_POSITION));
        setPathDistance(DEFAULT_PATH_DISTANCE);
    }

    private void initVertices() {
        if (vertices != null) {
            vertices.clear();
        } else {
            vertices = new HashSet();
        }

        if (groupVertices != null) {
            groupVertices.clear();
        } else {
            groupVertices = new HashSet();
        }

        if (groupSize != null) {
            groupSize.clear();
        } else {
            groupSize = new HashMap<V, Integer>();
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

    public Set getGroupVertices() {
        return groupVertices;
    }

    public void setGroupVertices(Set groupVertices) {
        this.groupVertices = groupVertices;
    }

    public Set getVertices() {
        return vertices;
    }

    public void setVertices(Set vertices) {
        this.vertices = vertices;
    }

    public Map<V, Integer> getGroupSize() {
        return groupSize;
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }
}