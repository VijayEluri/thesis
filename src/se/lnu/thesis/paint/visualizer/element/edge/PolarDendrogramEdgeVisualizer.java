package se.lnu.thesis.paint.visualizer.element.edge;

import se.lnu.thesis.element.AbstractGraphElement;
import se.lnu.thesis.element.PolarEdge;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.Utils;

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
public class PolarDendrogramEdgeVisualizer extends AbstractEdgeVisualizer {

    protected PolarDendrogramLayout layout;

    //      layout = (PolarDendrogramLayout) getVisualizer().getLayout();


    public PolarDendrogramEdgeVisualizer(Color color) {
        super(color);

//        layout = (PolarDendrogramLayout) getVisualizer().getLayout();
    }

    protected void drawShape(AbstractGraphElement element) {
        PolarEdge edge = (PolarEdge) element;

        if (edge.isFromRoot()) { // from root draw simple line

            drawLine(edge.getStartPosition(), edge.getEndPosition());

        } else {
            drawArc(edge);

            drawLine(edge.getDummyNode(), edge.getEndPosition());
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

        double startAngle = Utils.min(sourceAngle, destAngle);
        double endAngle = Utils.max(sourceAngle, destAngle);

        gl().glPushMatrix();
        gl().glTranslated(edge.getXCenter(), edge.getYCenter(), 0.0);

        DrawingUtils.arc(gl(), startAngle, endAngle, edge.getSourceRadius(), 10);

        gl().glPopMatrix();
    }

}