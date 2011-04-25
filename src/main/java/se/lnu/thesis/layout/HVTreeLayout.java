package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.*;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 20.03.11
 * Time: 14:55
 */
public class HVTreeLayout extends AbstractLayout {

    public static final double DEFAULT_DISTANCE = 0.05;


    protected Point2D.Double distance = new Point2D.Double(DEFAULT_DISTANCE, DEFAULT_DISTANCE);
    protected Point2D.Double start = new Point2D.Double(0, 0);


    public HVTreeLayout() {

    }

    public HVTreeLayout(Graph graph, DimensionalContainer root) {
        super(graph, root);
    }

    public void compute() {
        checkArguments();

        Object o = getRoot().getObject();

        computePosition(o, start.getX(), start.getY());

        computeGraphArea();

        computeEdges();

        getRoot().setLayoutComputed(true);

    }

    /**
     *
     *      Compute position for vertex and recursively compute position for it's successor.
     *
     * @param o Vertex object position to compute.
     * @param x X position for current vertex.
     * @param y Y position for current vertex.
     */
    protected void computePosition(Object o, double x, double y) {

        addVertexElementToContainer(o, x, y);

        int successorCount = getGraph().getSuccessorCount(o);
        Iterator iterator = getGraph().getSuccessors(o).iterator();

        if (successorCount == 1) {
            Object successor = iterator.next();

            computePosition(successor, x + distance.getX(), y);
        }

        if (successorCount == 2) {
            Object successor1 = iterator.next();
            int successor1Area = computeArea(successor1);

            Object successor2 = iterator.next();
            int successor2Area = computeArea(successor2);

            if (successor1Area > successor2Area) {
                computePosition(successor1, x + distance.getX(), y);
                computePosition(successor2, x,                   y - (distance.getY() * successor1Area ));
            } else {
                computePosition(successor1, x,                   y - (distance.getY() * successor2Area ));
                computePosition(successor2, x + distance.getX(), y);
            }
        }

        if (successorCount > 2) {
            LOGGER.error("INCORRECT GRAPH STRUCTURE. ONLY FOR BINARY TREE!");
        }
    }

    /**
     *
     *      Create and add <code>VertexElement</code> to container.
     *      By default this method add <code>VertexElement</code> for all vertices with <code>CircleVisualizer</code>
     *
     * @param o Vertex object id.
     * @param x Vertex x position.
     * @param y Vertex y position.
     */
    protected void addVertexElementToContainer(Object o, double x, double y) {
        VertexElement vertex = VertexElement.init(o, x, y, ElementVisualizerFactory.getInstance().getCircleVisualizer());
        vertex.setTooltip(((MyGraph) getGraph()).getLabel(o));

        root.addElement(vertex);
    }

    /**
     *
     *      Return are based on how many successors has current vertex - whole amount including successors of successors.
     *
     * @param o Vertex which successors to compute to.
     * @return 1 if no successors otherwise amount of successors.
     */
    protected int computeArea(Object o) {
        int result = 1;

        Collection successors = getGraph().getSuccessors(o);
        for (Object successor : successors) {
            result += computeArea(successor);
        }

        return result;
    }

    /**
     *
     *      Compute edges for current graph.
     *
     */
    protected void computeEdges() {
        for (Object edge: graph.getEdges()) {
            addEdgeElementToConatiner(edge);
        }
    }

    /**
     *
     *      Create and add <code>EdgeElement</code> to container.
     *      By default this method add <code>EdgeElement</code> for all vertices with <code>LineEdgeVisualizer</code>
     *
     * @param edge Edge object id.
     */
    protected void addEdgeElementToConatiner(Object edge) {
        EdgeElement edgeElement = EdgeElement.init(edge, graph, root, ElementVisualizerFactory.getInstance().getLineEdgeVisializer());
        root.addElement(edgeElement);
    }

    /**
     *      Compute dimension size of current graph and set as dimension to root container.
     *      This dimension is used during drawing of background for <code>RectLens</code>
     */
    protected void computeGraphArea() {
/*
        Point2D position = root.getElementByObject(getRoot().getObject()).getPosition();

        double x = position.getX();
        double y = position.getY();
*/
        double x = start.getX();
        double y = start.getY();

        for (Element element: getRoot()) {
            if (element.getType() == ElementType.VERTEX) {
                if (element.getPosition().getX() > x) {
                    x = element.getPosition().getX();
                }

                if (element.getPosition().getY() < y) {
                    y = element.getPosition().getY();
                }
            }
        }

        Point2D.Double dimension = new Point2D.Double(Math.abs(x - start.getX()), Math.abs(y + start.getY()));
        ((DimensionalContainer) getRoot()).setDimension(dimension);
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
