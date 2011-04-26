package se.lnu.thesis.paint.visualizer.edge;

import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GOBundledEdge;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 21.04.11
 * Time: 23:53
 */
public class BundledEdgeVisualizer extends LineEdgeVisualizer {

    public BundledEdgeVisualizer(MyColor color) {
        super(color);
    }

    public BundledEdgeVisualizer(MyColor color, float width) {
        super(color, width);
    }

    protected void drawShape(Element element) {
        GOBundledEdge edge = (GOBundledEdge) element;

        drawLine(edge.getStartPosition(), edge.getDummy());

        drawCurve(edge.getDummy(), edge.getEndPosition());
    }

    /**
     * Draw straight line from vertex to dummy node on the top the the level
     *
     * @param start Edge start position.
     * @param end   Dummy node position.
     */
    protected void drawLine(Point2D start, Point2D end) {
        gl().glLineWidth(getLineWidth());
        gl().glBegin(GL.GL_LINES);
        gl().glVertex2d(start.getX(), start.getY());
        gl().glVertex2d(end.getX(), end.getY());
        gl().glEnd();
    }

    /**
     * Draw Bezier curve from dummy node to target vertex.
     *
     * @param start Dummy node position
     * @param end   Edge end position
     */
    protected void drawCurve(Point2D start, Point2D end) {
        Point2D controlPoint = new Point2D.Double(start.getX(), end.getY());

        DrawingUtils.quadraticBezierCurve(gl(), start, controlPoint, end, getLineWidth());
    }

}
