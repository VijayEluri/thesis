package se.lnu.thesis.layout;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.10.2010
 * Time: 16:00:39
 */
public class LevelBarLayout extends UniformDistributionLayout {

    public static final double DEFAULT_LEFT_BORDER = 0.06;
    public static final double DEFAULT_RIGHT_BORDER = 0.01;
    public static final double DEFAULT_TOP_BORDER = 0.02;
    public static final double DEFAULT_BOTTOM_BORDER = 0.02;

    private ImmutableCollection objects;

    private Collection leaves;
    private Collection nodes;

    private Point2D leavesStartPosition;
    private Point2D leavesDimension;

    private Point2D nodesStartPosition;
    private Point2D nodesDimension;

    private double leftBorder = DEFAULT_LEFT_BORDER;
    private double rightBorder = DEFAULT_RIGHT_BORDER;
    private double topBorder = DEFAULT_TOP_BORDER;
    private double bottomBorder = DEFAULT_BOTTOM_BORDER;

    /**
     * This layout is used to compute positions for leafs and nodes parts
     */
    private UniformDistributionLayout partsLayout;

    public LevelBarLayout() {

    }

    public LevelBarLayout(Graph graph) {
        super(graph);
    }

    public LevelBarLayout(Graph graph, Container root) {
        super(graph, root);
    }

    public void compute() {

        extractLeavesAndNodes();

        computeSpace();

        computePositions();
    }

    private void extractLeavesAndNodes() {
        for (Object o : objects) {
            if (GraphUtils.isLeaf(graph, o)) {
                leaves.add(o);
            } else {
                nodes.add(o);
            }
        }

        if (objects.size() != (leaves.size() + nodes.size())) {
            LOGGER.error("Some elements are lost!");
        }
    }

    private void computeSpace() {
        Point2D d = new Point2D.Double(getDimension().getX() - (leftBorder + rightBorder), getDimension().getY() - (topBorder + bottomBorder)); // layout dimension including border
        Point2D p = new Point2D.Double(getStart().getX() + leftBorder, getStart().getY() - topBorder); // start position including border


        double percentage = leaves.size() * 100 / objects.size();
        leavesDimension = new Point2D.Double(d.getX() / 100 * percentage, d.getY());

        percentage = 100 - percentage;
        nodesDimension = new Point2D.Double(d.getX() / 100 * percentage, d.getY());

        leavesStartPosition = new Point2D.Double(p.getX(), p.getY());
        nodesStartPosition = new Point2D.Double(p.getX() + leavesDimension.getX(), p.getY());
    }

    private void computePositions() {
        // compute leaves positions
        compute(leaves, leavesStartPosition, leavesDimension, root);

        // compute nodes positions
        compute(nodes, nodesStartPosition, nodesDimension, root);
    }

    private void compute(Collection objects, Point2D start, Point2D dimension, Container container) {
        if (objects.size() > 0) {
            if (partsLayout == null) {
                throw new IllegalStateException("Layout for leafs and nodes didnt set!");
            }

            partsLayout.setGraph(graph);

            partsLayout.setRoot(container);
            partsLayout.setObjects(objects);

            partsLayout.setStart(start);
            partsLayout.setDimension(dimension);

            partsLayout.compute();
            partsLayout.reset();
        }
    }

    public void setObjects(Collection objects) {
        this.objects = ImmutableSet.copyOf(objects);

        if (this.leaves == null) {
            this.leaves = new HashSet();
        } else {
            this.leaves.clear();
        }

        if (this.nodes == null) {
            this.nodes = new HashSet();
        } else {
            this.nodes.clear();
        }
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(double leftBorder) {
        this.leftBorder = leftBorder;
    }

    public double getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(double rightBorder) {
        this.rightBorder = rightBorder;
    }

    public double getTopBorder() {
        return topBorder;
    }

    public void setTopBorder(double topBorder) {
        this.topBorder = topBorder;
    }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(double bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public UniformDistributionLayout getPartsLayout() {
        return partsLayout;
    }

    public void setPartsLayout(UniformDistributionLayout partsLayout) {
        this.partsLayout = partsLayout;
    }
}
