package se.lnu.thesis.paint.visualizer.edge;

import se.lnu.thesis.paint.element.EdgeElement;
import se.lnu.thesis.paint.element.Element;

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

    public static final float DEFAULT_LINE_WIDTH = 0.1f;

    protected float lineWidth = DEFAULT_LINE_WIDTH;

    public LineEdgeVisualizer(Color color) {
        super(color);
    }

    protected void drawShape(Element edgeElement) {
        EdgeElement edge = (EdgeElement) edgeElement;

        Point2D start = edge.getStartPosition();
        Point2D end = edge.getEndPosition();

        gl().glBegin(GL.GL_LINES);
        gl().glLineWidth(lineWidth);
        gl().glVertex2d(start.getX(), start.getY());
        gl().glVertex2d(end.getX(), end.getY());
        gl().glEnd();

    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
}
