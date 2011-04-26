package se.lnu.thesis.element;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;

import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:44:55
 */
public class EdgeElement extends VisualizableElement {

    private static final int EDGE_DRAWING_ORDER = -2;

    public static EdgeElement init(Object o, Object source, Object target, Point2D start, Point2D end, ElementVisualizer visualizer) {
        EdgeElement result = new EdgeElement();

        result.setObject(o);

        result.setFrom(source);
        result.setTo(target);

        result.setStartPosition(start);
        result.setEndPosition(end);

        result.setVisualizer(visualizer);

        return result;
    }


    public static EdgeElement init(Object o, Graph graph, Container root, ElementVisualizer visualizer) {
        EdgeElement result = new EdgeElement();

        //      result.setId(IdGenerator.next()); // edges are unselectable in future
        result.setObject(o);

        Object source = graph.getSource(o);
        result.setFrom(source);

        Object dest = graph.getDest(o);
        result.setTo(dest);

        Element element = root.getElementByObject(source);
        if (element != null) {
            result.setStartPosition(element.getPosition());
        } else {
            LOGGER.error("Cant find vertex for object '" + source + "'");
        }

        element = root.getElementByObject(dest);
        if (element != null) {
            result.setEndPosition(element.getPosition());
        } else {
            LOGGER.error("Cant find vertex for object '" + source + "'");
        }

        result.setVisualizer(visualizer);

        return result;
    }

    private Object from;
    private Object to;

    private Point2D endPosition;

    public EdgeElement() {

    }

    /**
     * Check if object <code>from</code> or <code>to</code> edge equals to corresponded object.
     *
     * @param o Object to check with.
     * @return True if any edge vertex is equal, False otherwise.
     */
    @Override
    public boolean has(Object o) {
        return from.equals(o) || to.equals(o);
    }

    /**
     * Check does BOTH edge vertices contain in objects collection
     *
     * @param objects Collection to check in
     * @return True if BOTH edge vertices contain in objects collection, False otherwise
     */
    @Override
    public boolean hasAny(Collection objects) {
        return objects.contains(from) && objects.contains(to);
    }

    public ElementType getType() {
        return ElementType.EDGE;
    }

    public int getDrawingOrder() {
        return EDGE_DRAWING_ORDER;
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public Object getTo() {
        return to;
    }

    public void setTo(Object to) {
        this.to = to;
    }

    public Point2D getStartPosition() {
        return getPosition();
    }

    public void setStartPosition(Point2D startPosition) {
        setPosition(startPosition);
    }

    public Point2D getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Point2D endPosition) {
        this.endPosition = endPosition;
    }

    @Override
    public void setHighlighted(boolean b) {
        super.setHighlighted(b);
    }
}
