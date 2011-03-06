package se.lnu.thesis.element;

import se.lnu.thesis.paint.visualizer.AbstractElementVisualizer;
import se.lnu.thesis.utils.IdGenerator;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:54:07
 */
public class VertexElement extends VisualizableElement {

    public static final int VERTEX_DRAWING_ORDER = 1;

    /**
     * Create graph vertex and set id automaticaly
     *
     * @param o Vertex object
     * @return New graph vertex
     */
    public static VertexElement init(Object o) {
        return init(o, null, null);
    }

    /**
     * Create new graph vertex and set id automaticaly
     *
     * @param o Vertex object
     * @param x X position
     * @param y Y position
     * @return New graph vertex
     */
    public static VertexElement init(Object o, double x, double y) {
        return init(o, x, y, null);
    }

    /**
     * Create graph vertex and set id automaticaly
     *
     * @param o        Vertex object
     * @param position Vertex position
     * @return New graph vertex
     */
    public static VertexElement init(Object o, Point2D position) {
        return init(o, position, null);
    }

    /**
     * Create graph vertex and set id automaticaly
     *
     * @param o          Vertex object
     * @param position   Vertex position
     * @param visualizer Element visualizer
     * @return New graph vertex
     */
    public static VertexElement init(Object o, Point2D position, AbstractElementVisualizer visualizer) {
        if (position == null) {
            throw new IllegalArgumentException("Argument 'position' cannt be null!");
        }

        return init(o, position.getX(), position.getY(), visualizer);
    }

    /**
     * Create graph vertex and set id automaticaly.
     *
     * @param o          Vertex object
     * @param x          X position
     * @param y          Y position
     * @param visualizer Element visualizer
     * @return New graph vertex
     */
    public static VertexElement init(Object o, double x, double y, AbstractElementVisualizer visualizer) {
        VertexElement result = new VertexElement();

        result.setId(IdGenerator.next());

        result.setObject(o);
        result.setPosition(new Point2D.Double(x, y));
        result.setVisualizer(visualizer);

        return result;
    }

    /**
     * Create graph vertex without id. So this element will be unselectable in future
     *
     * @param o          Vertex object
     * @param x          X position
     * @param y          Y position
     * @param visualizer Element visualizer
     * @return New graph vertex
     */
    public static VertexElement initUnidentifiable(Object o, double x, double y, AbstractElementVisualizer visualizer) {
        VertexElement result = new VertexElement();

        result.setObject(o);
        result.setPosition(new Point2D.Double(x, y));
        result.setVisualizer(visualizer);

        return result;
    }

    public VertexElement() {

    }

    public ElementType getType() {
        return ElementType.NODE;
    }

    public int getDrawingOrder() {
        return VERTEX_DRAWING_ORDER;
    }

}
