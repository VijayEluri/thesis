package se.lnu.thesis.element;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:44:55
 */
public class EdgeElement extends AbstractGraphElement {

    private Object from;
    private Object to;
    private Point2D startPosition;
    private Point2D endPosition;

    public EdgeElement() {

    }

    @Override
    public boolean has(Object o) {
        return from.equals(o) || to.equals(o);
    }

    @Override
    public GraphElementType getType() {
        return GraphElementType.EDGE;
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
        return startPosition;
    }

    public void setStartPosition(Point2D startPosition) {
        this.startPosition = startPosition;
    }

    public Point2D getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Point2D endPosition) {
        this.endPosition = endPosition;
    }
}
