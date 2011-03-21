package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.element.PolarVertex;
import se.lnu.thesis.element.VertexElement;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 20.03.11
 * Time: 14:55
 *
 *
 *
 */
public class HVTreeLayout extends AbstractLayout {

    protected Point2D.Double distance = new Point2D.Double(0.01, 0.01);

    protected Point2D.Double start = new Point2D.Double(0, 0);

    public HVTreeLayout() {

    }

    public HVTreeLayout(Graph graph, GroupingElement root) {
        super(graph, root);
    }

    public void compute() {
        Object object = getRoot().getObject();

        computePosition(object, start);

    }

    protected int computePosition(Object o, Point2D p) {
/*
        int successorCount = getGraph().getSuccessorCount(o);

        if (successorCount == 0) {
            return 0;
        }

        if (successorCount == 1) {
            Object successor = getGraph().getSuccessors(o).iterator().next();

            VertexElement vertex = VertexElement.init(successor, start.getX() + distance.getX(), start.getY(), ElementVisualizerFactory.getInstance().getCircleVisualizer());
            root.addElement(vertex);

            return 1;
        }
*/

        return 0;
    }

    public Point2D.Double getStart() {
        return start;
    }

    public void setStart(Point2D.Double start) {
        this.start = start;
    }

    public Point2D.Double getDistance() {
        return distance;
    }

    public void setDistance(Point2D.Double distance) {
        this.distance = distance;
    }
}
