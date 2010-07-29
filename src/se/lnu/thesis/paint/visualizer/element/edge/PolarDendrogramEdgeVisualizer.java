package se.lnu.thesis.paint.visualizer.element.edge;

import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.element.AbstractGraphElement;

import java.awt.*;

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
/*
        Object sourceNode = source(edge);
        Object destNode = dest(edge);

        if (GraphUtils.getInstance().isRoot(getVisualizer().getGraph(), sourceNode)) { // from root draw simple line

            drawLine(p(sourceNode), p(destNode));

        } else {
            Point2D dummyNode = layout.getDummyNode(sourceNode, destNode);

            drawArc(sourceNode, destNode);

            drawLine(dummyNode, p(destNode));
        }
*/
    }

    /*  protected void drawLine(Point2D start, Point2D end) {
        gl().glBegin(GL.GL_LINES);
        gl().glVertex2d(start.getX(), start.getY());
        gl().glVertex2d(end.getX(), end.getY());
        gl().glEnd();
    }

    protected void drawArc(Object sourceNode, Object destNode) {
        Double sourceAngle = (Double) layout.getNodeAngle().get(sourceNode);
        Double destAngle = (Double) layout.getNodeAngle().get(destNode);

        double startAngle = Utils.min(sourceAngle, destAngle);
        double endAngle = Utils.max(sourceAngle, destAngle);

        gl().glPushMatrix();
        gl().glTranslated(layout.getXCenter(), layout.getYCenter(), 0.0);

        DrawingUtils.arc(gl(), startAngle, endAngle, layout.getNodeRadius(sourceNode), 10);

        gl().glPopMatrix();
    }*/

}