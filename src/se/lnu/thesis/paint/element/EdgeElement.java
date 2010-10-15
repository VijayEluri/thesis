package se.lnu.thesis.paint.element;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;

import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:44:55
 */
public class EdgeElement extends AbstractElement {

    private static final int EDGE_DRAWING_ORDER = -1;

    public static EdgeElement init(Object o, Object source, Object target, Point2D start, Point2D end, ElementVisualizer visualizer) {
        EdgeElement result = new EdgeElement();

//        result.setId(IdUtils.next()); // edges are unselectable in future
        result.setObject(o);

        result.setFrom(source);
        result.setTo(target);

        result.setDrawed(true);

        result.setStartPosition(start);
        result.setEndPosition(end);

        result.setVisualizer(visualizer);

        return result;
    }


    public static EdgeElement init(Object o, Graph graph, CompositeElement root) {
        EdgeElement result = new EdgeElement();

        //      result.setId(IdUtils.next()); // edges are unselectable in future
        result.setObject(o);

        Object source = graph.getSource(o);
        result.setFrom(source);

        Object dest = graph.getDest(o);
        result.setTo(dest);

        result.setDrawed(false);

        Element element = root.getElementByObject(source);
        if (element != null) {
            result.setStartPosition(element.getPosition());
        } else {
            LOGGER.error("Cant find element for object '" + source + "'");
        }

        element = root.getElementByObject(dest);
        if (element != null) {
            result.setEndPosition(element.getPosition());
        } else {
            LOGGER.error("Cant find element for object '" + source + "'");
        }

        result.setVisualizer(ElementVisualizerFactory.getInstance().getLineEdgeVisializer());

        return result;
    }

    private Object from;
    private Object to;

    private Point2D endPosition;

    public EdgeElement() {

    }

    @Override
    public boolean has(Object o) {
        return from.equals(o) || to.equals(o);
    }


    @Override
    public boolean hasAny(Collection nodes) {
        return nodes.contains(from) && nodes.contains(to);
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
        highlighted = b;
        drawed = b;
    }
}