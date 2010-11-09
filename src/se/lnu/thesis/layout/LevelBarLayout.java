package se.lnu.thesis.layout;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.Container;
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

    private static final Point2D DEFAULT_BORDER = new Point2D.Double(0.02, 0.02);

    private ImmutableCollection objects;

    private Collection leaves;
    private Collection nodes;

    private Point2D leavesStartPosition;
    private Point2D leavesDimension;

    private Point2D nodesStartPosition;
    private Point2D nodesDimension;

    private Point2D border = DEFAULT_BORDER;

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
        Point2D d = new Point2D.Double(getDimension().getX() - (border.getX() * 2), getDimension().getY() - (border.getY() * 2)); // layout dimension including border
        Point2D p = new Point2D.Double(getStart().getX() + border.getX(), getStart().getY() - border.getY()); // start position including border


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

    public Point2D getBorder() {
        return border;
    }

    public void setBorder(Point2D border) {
        this.border = border;
    }

    public void setBorder(double x, double y) {
        if (this.border == null) {
            this.border = new Point2D.Double();
        }

        this.border.setLocation(x, y);
    }

    public UniformDistributionLayout getPartsLayout() {
        return partsLayout;
    }

    public void setPartsLayout(UniformDistributionLayout partsLayout) {
        this.partsLayout = partsLayout;
    }
}
