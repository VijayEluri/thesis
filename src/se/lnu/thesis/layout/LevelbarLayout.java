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
public class LevelbarLayout extends UniformDistributionLayout {

    private ImmutableCollection objects;

    private Collection leaves;
    private Collection nodes;

    private Point2D.Double leavesStartPosition;
    private Point2D.Double leavesDimension;

    private Point2D.Double nodesStartPosition;
    private Point2D.Double nodesDimension;

    public LevelbarLayout(Graph graph) {
        super(graph);
    }

    public LevelbarLayout(Graph graph, Container root) {
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
        double percentage = leaves.size() * 100 / objects.size();
        leavesDimension = new Point2D.Double(dimension.getX() / 100 * percentage, dimension.getY());

        percentage = 100 - percentage;
        nodesDimension = new Point2D.Double(dimension.getX() / 100 * percentage, dimension.getY());

        leavesStartPosition = new Point2D.Double(getStart().getX(), getStart().getY());
        nodesStartPosition = new Point2D.Double(getStart().getX() + leavesDimension.getX(), getStart().getY());
    }

    private void computePositions() {
        // compute leaves positions
        compute(leaves, leavesStartPosition, leavesDimension, root);

        // compute nodes positions
        compute(nodes, nodesStartPosition, nodesDimension, root);
    }

    private void compute(Collection objects, Point2D start, Point2D dimension, Container container) {
        if (objects.size() > 0) {
            LevelPreviewLayout layout = new LevelPreviewLayout(graph); // TODO THINK ABOUT IT!

            layout.setStart(start);
            layout.setDimension(dimension);
            layout.setObjects(objects);
            layout.setRoot(container);

            layout.compute();
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
}
