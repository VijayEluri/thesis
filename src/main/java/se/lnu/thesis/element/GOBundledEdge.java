package se.lnu.thesis.element;

import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 21.04.11
 * Time: 23:47
 */
public class GOBundledEdge extends GOEdge {

    public static GOBundledEdge init(Object o, Object source, Object target, Point2D start, Point2D dummy, Point2D end) {
        GOBundledEdge result = new GOBundledEdge();

        result.setObject(o);

        result.setFrom(source);
        result.setTo(target);

        result.setStartPosition(start);
        result.setDummy(dummy);
        result.setEndPosition(end);

        result.setVisualizer(ElementVisualizerFactory.getInstance().getBundledEdgeVisualizer());

        result.setHighlighted(false); // do not highlight in default state
        result.setShowHighlightedEdge(true); // should ne drawn if highlighted

        return result;
    }

    protected Point2D dummy;

    public Point2D getDummy() {
        return dummy;
    }

    public void setDummy(Point2D dummy) {
        this.dummy = dummy;
    }
}
