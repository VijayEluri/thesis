package se.lnu.thesis.paint.visualizer.edge;

import se.lnu.thesis.paint.element.Element;
import se.lnu.thesis.paint.element.PolarEdge;
import se.lnu.thesis.utils.DrawingUtils;

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
public class PolarDendrogramEdgeVisualizer extends LineEdgeVisualizer {

    public PolarDendrogramEdgeVisualizer(Color color) {
        super(color);
    }

    protected void drawShape(Element element) {
        PolarEdge edge = (PolarEdge) element;

        gl().glLineWidth(getLineWidth());

        if (edge.isFromRoot()) { // from root draw simple line

            drawLine(edge.getStartPosition(), edge.getEndPosition());

        } else {
            drawArc(edge);

            drawLine(edge.getDummyNodePosition(), edge.getEndPosition());
        }
    }

    protected void drawLine(Point2D start, Point2D end) {
        gl().glBegin(GL.GL_LINES);
        gl().glVertex2d(start.getX(), start.getY());
        gl().glVertex2d(end.getX(), end.getY());
        gl().glEnd();
    }

    protected void drawArc(PolarEdge edge) {
        Double sourceAngle = edge.getSourceAngle();
        Double destAngle = edge.getDestAngle();

        double startAngle = Math.min(sourceAngle, destAngle);
        double endAngle = Math.max(sourceAngle, destAngle);

        gl().glPushMatrix();
        gl().glTranslated(edge.getXCenter(), edge.getYCenter(), 0.0);

        DrawingUtils.arc(gl(), startAngle, endAngle, edge.getSourceRadius(), 10);

        gl().glPopMatrix();
    }

}