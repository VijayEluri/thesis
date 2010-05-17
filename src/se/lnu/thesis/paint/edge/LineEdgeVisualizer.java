package se.lnu.thesis.paint.edge;

import se.lnu.thesis.paint.GraphVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;

import javax.media.opengl.GL;
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

        gl().glBegin(GL.GL_LINES);
        gl().glVertex2d(start.getX(), start.getY());
        gl().glVertex2d(end.getX(), end.getY());
        gl().glEnd();

    }

}
