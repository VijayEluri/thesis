package se.lnu.thesis.paint.visualizer.edge;

import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GOBundledEdge;
import se.lnu.thesis.element.PolarEdge;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUnurbs;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 21.04.11
 * Time: 23:53
 *
 *
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

        gl().glLineWidth(getLineWidth());

        drawLine(edge.getStartPosition(), edge.getDummy());

        drawCurve(edge.getDummy(), edge.getEndPosition());
    }

    protected void drawLine(Point2D start, Point2D end) {
        gl().glBegin(GL.GL_LINES);
        gl().glVertex2d(start.getX(), start.getY());
        gl().glVertex2d(end.getX(), end.getY());
        gl().glEnd();
    }

    protected void drawCurve(Point2D start, Point2D end) {
        drawLine(start, end);
/*
        GLUnurbs nurb = glu().gluNewNurbsRenderer();

        glu().gluBeginCurve(nurb);
        glu().gluNurbsCurve(nurb,
                ,
                ,
                ,
                ,
                , GL.GL_MAP1_NORMAL);
        glu().gluEndCurve(nurb);
*/
    }

}
