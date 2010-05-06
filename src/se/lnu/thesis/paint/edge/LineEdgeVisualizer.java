package se.lnu.thesis.paint.edge;

import se.lnu.thesis.paint.GraphVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 0:46:41
 * <p/>
 * Simple implementation of the edge visualisation as line
 */
public class LineEdgeVisualizer extends AbstractEdgeVisualizer {

    public LineEdgeVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public LineEdgeVisualizer(GraphWithSubgraphVisualizer visualizer, Color color) {
        super(visualizer, color);
    }

    protected void drawShape(Object edge) {
        Point2D start = p(source(edge));
        Point2D end = p(dest(edge));

        canvas().line(
                new Float(start.getX()),
                new Float(start.getY()),
                new Float(end.getX()),
                new Float(end.getY()));
    }

}
