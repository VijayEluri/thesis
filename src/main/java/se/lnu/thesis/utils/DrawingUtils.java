package se.lnu.thesis.utils;

import org.apache.log4j.Logger;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.05.2010
 * Time: 19:13:50
 * <p/>
 * OpenGL drawing halping class.
 */
public class DrawingUtils {

    public static final Logger LOGGER = Logger.getLogger(DrawingUtils.class);

    /**
     * Draw circle polygon
     *
     * @param gl       GL context to draw
     * @param radius   Circle radius
     * @param segments Number of outer elements
     */
    public static void circle(GL2 gl, double radius, int segments) {

        double step = 360.0 / segments;
        double angle;

        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < segments; i++) {
            angle = step * i;

            double x = Math.cos(Utils.inRadians(angle)) * radius;
            double y = Math.sin(Utils.inRadians(angle)) * radius;

            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    /**
     * Draw ring
     *
     * @param gl        GL context to draw
     * @param radius    Circle radius
     * @param segments  Number of outer elements
     * @param thickness Ring thickness
     */
    public static void ring(GL2 gl, double radius, int segments, float thickness) {

        double step = 360.0 / segments;
        double angle;

        gl.glLineWidth(thickness);

        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < segments; i++) {
            angle = step * i;

            double x = Math.cos(Utils.inRadians(angle)) * radius;
            double y = Math.sin(Utils.inRadians(angle)) * radius;

            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    public static void arc(GL2 gl, double startAngle, double endAngle, double radius, int segments) {

        double step = (endAngle - startAngle) / segments;
        double angle;

        gl.glBegin(GL.GL_LINE_STRIP);
        for (int i = 0; i <= segments; i++) {
            angle = startAngle + (i * step);

            double x = Math.cos(Utils.inRadians(angle)) * radius;
            double y = Math.sin(Utils.inRadians(angle)) * radius;

            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    public static void spiral(GL2 gl, double step, int elements) {

        final double a = 1.0;

        double angle;

        gl.glBegin(GL.GL_LINE_STRIP);
        //gl2.glBegin(GL.GL_POINTS);
        for (int i = 0; i < elements; i++) {
            angle = Utils.inRadians(i * step);

            double x = a * angle * Math.cos(angle);
            double y = a * angle * Math.sin(angle);

            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    /**
     * Draw quadratic Bezier curve based on start, control point and end with specified line thickness.
     *
     * @param gl            OpenGL drawing context.
     * @param start         Start point position
     * @param controlPoint  Control point position
     * @param end           End point position
     * @param lineThickness Line thickness
     */
    public static void quadraticBezierCurve(GL2 gl, Point2D start, Point2D controlPoint, Point2D end, float lineThickness) {
        gl.glLineWidth(lineThickness);

        gl.glBegin(GL.GL_LINE_STRIP);
        for (double t = 0.0; t <= 1.0; t += 0.001) {
            Point2D p = pointOnQuadranticBezierCurve(start, controlPoint, end, t);
            gl.glVertex2d(p.getX(), p.getY());
        }
        gl.glEnd();

    }

    /**
     * Compute point on quadratic Bezier curve.
     *
     * @param start        Start point position.
     * @param controlPoint Control point position.
     * @param end          End point position.
     * @param t            t is in range 0..1
     * @return Return point position based on three points and t v.
     */
    public static Point2D pointOnQuadranticBezierCurve(Point2D start, Point2D controlPoint, Point2D end, double t) {
        Point2D result = new Point2D.Double();

        result.setLocation(
                Math.pow((1 - t), 2) * start.getX() + 2 * (1 - t) * t * controlPoint.getX() + Math.pow(t, 2) * end.getX(),
                Math.pow((1 - t), 2) * start.getY() + 2 * (1 - t) * t * controlPoint.getY() + Math.pow(t, 2) * end.getY());

        return result;
    }

    /**
     * Draw cubic Bezier curve based on start, control point and end with specified line thickness.
     *
     * @param gl            OpenGL drawing context.
     * @param start         Start point position
     * @param controlPoint1 First control point position
     * @param controlPoint2 Second control point position
     * @param end           End point position
     * @param lineThickness Line thickness
     */
    public static void cubicBezierCurve(GL2 gl, Point2D start, Point2D controlPoint1, Point2D controlPoint2, Point2D end, float lineThickness) {
        gl.glLineWidth(lineThickness);

        gl.glBegin(GL.GL_LINE_STRIP);
        for (double t = 0.0; t <= 1.0; t += 0.001) {
            Point2D p = pointOnCubicBezierCurve(start, controlPoint1, controlPoint2, end, t);
            gl.glVertex2d(p.getX(), p.getY());
        }
        gl.glEnd();
    }


    /**
     * Compute point on quadratic Bezier curve.
     *
     * @param start         Start point position.
     * @param controlPoint1 First control point position.
     * @param controlPoint2 Second control point position.
     * @param end           End point position.
     * @param t             t is in range 0..1
     * @return
     */
    public static Point2D pointOnCubicBezierCurve(Point2D start, Point2D controlPoint1, Point2D controlPoint2, Point2D end, double t) {
        Point2D result = new Point2D.Double();

        result.setLocation(
                Math.pow((1 - t), 3) * start.getX() + 3 * Math.pow((1 - t), 2) * t * controlPoint1.getX() + 3 * (1 - t) * Math.pow(t, 2) * controlPoint2.getX() + Math.pow(t, 3) * end.getX(),
                Math.pow((1 - t), 3) * start.getY() + 3 * Math.pow((1 - t), 2) * t * controlPoint1.getY() + 3 * (1 - t) * Math.pow(t, 2) * controlPoint2.getY() + Math.pow(t, 3) * end.getY());

        return result;
    }

    /**
     * Convert point coordinates from the window coordinate system to the OpenGL world coordinates.
     *
     * @param gl    Current OpenGL context
     * @param glu   GLU library
     * @param point Point coordinates in window
     * @return converted point in OpenGL world coordinate system
     */
    public static double[] window2world(GL2 gl, GLU glu, Point point) {
        int viewport[] = new int[4];       // [0,0,960,1020]
        double mvmatrix[] = new double[16];  // [1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0]
        double projmatrix[] = new double[16];  // [1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,1.0]

        double wcoord[] = new double[4]; // wx, wy, wz

        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, mvmatrix, 0);
        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projmatrix, 0);


        double x = point.getX();
        double y = viewport[3] - point.getY(); /* note viewport[3] is height of window in pixels */


        glu.gluUnProject(x, y, 0.0, mvmatrix, 0, projmatrix, 0, viewport, 0, wcoord, 0);

        LOGGER.debug("Cursot window coordinates: [" + point.getX() + ";" + point.getY() + "]");
        LOGGER.debug("Cursor World coordinates: [" + wcoord[0] + ";" + wcoord[1] + "]");

        return wcoord;
    }


    /**
     * Draw rectangle by coordinates of the left lower corner, width and height
     *
     * @param gl     OpenGL drawing context
     * @param x0     X coordinate of the left lower corner
     * @param y0     Y coordinate of the left lower corner
     * @param width  Rectangle width
     * @param height Rectangle height
     */
    public static void rect(GL2 gl, double x0, double y0, double width, double height) {
        rect(gl,
                x0, y0,
                x0 + width, y0,
                x0 + width, y0 + height,
                x0, y0 + height);
    }

    /**
     * Draw rectangle by four corner coordinates.
     * <p/>
     * (x3,y3) ------- (x2,y2)
     * |               |
     * |               |
     * |               |
     * |               |
     * (x0,y0) ------- (x1,y1)
     *
     * @param gl OpenGL drawing context
     * @param x0 X coordinate of the left lower corner
     * @param y0 Y coordinate of the left lower corner
     * @param x1 X coordinate of the right lower corner
     * @param y1 Y coordinate of the right lower corner
     * @param x2 X coordinate of the right upper corner
     * @param y2 Y coordinate of the right upper corner
     * @param x3 X coordinate on the left upper corner
     * @param y3 Y coordinate of the left upper corner
     */
    public static void rect(GL2 gl, double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
        gl.glPolygonMode(GL.GL_FRONT_FACE, GL2.GL_FILL);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2d(x0, y0);
        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glEnd();
    }
}
