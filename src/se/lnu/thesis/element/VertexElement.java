package se.lnu.thesis.element;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.visualizer.element.AbstractGraphElementVisualizer;
import se.lnu.thesis.utils.IdUtils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:54:07
 */
public class VertexElement extends AbstractGraphElement {

    public static VertexElement init(Object o) {
        return init(o, null, null);
    }

    public static VertexElement init(Object o, double x, double y) {
        return init(o, x, y, null);
    }

    public static VertexElement init(Object o, Point2D position) {
        return init(o, position, null);
    }

    public static VertexElement init(Object o, Point2D position, AbstractGraphElementVisualizer visualizer) {
        if (position == null) {
            throw new IllegalArgumentException("Argument 'position' cannt be null!");
        }

        return init(o, position.getX(), position.getY(), visualizer);
    }

    public static VertexElement init(Object o, double x, double y, AbstractGraphElementVisualizer visualizer) {
        VertexElement result = new VertexElement();

        result.setId(IdUtils.next());

        result.setObject(o);
        result.setPosition(x, y);
        result.setVisualizer(visualizer);

        return result;
    }

    private Point2D position;

    /**
     * Constructor deprecated. Use static method init to create and initialize new instance.
     */
    @Deprecated
    public VertexElement() {

    }

    public GraphElementType getType() {
        return GraphElementType.NODE;
    }

    public GraphElementType checkType(Graph graph, Object o) {
        if (graph.inDegree(o) == 0) {
            return GraphElementType.ROOT;
        } else {
            if (graph.outDegree(o) == 0) {
                return GraphElementType.LEAF;
            } else {
                return GraphElementType.NODE;
            }
        }
    }


    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        if (position == null) {
            throw new IllegalArgumentException("Argument 'position' cannt be null!");
        }

        setPosition(position.getX(), position.getY());
    }

    public void setPosition(double x, double y) {
        position = new Point2D.Double(x, y);
    }


}
