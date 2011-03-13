package se.lnu.thesis.utils;

import org.apache.log4j.Logger;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import java.awt.*;

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
    public static void circle(GL gl, double radius, int segments) {

        double step = 360.0 / segments;
        double angle;

        gl.glBegin(GL.GL_POLYGON);
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
    public static void ring(GL gl, double radius, int segments, float thickness) {

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

    public static void arc(GL gl, double startAngle, double endAngle, double radius, int segments) {

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

    public static void spiral(GL gl, double step, int elements) {

        final double a = 1.0;

        double angle;

        gl.glBegin(GL.GL_LINE_STRIP);
        //gl.glBegin(GL.GL_POINTS);
        for (int i = 0; i < elements; i++) {
            angle = Utils.inRadians(i * step);

            double x = a * angle * Math.cos(angle);
            double y = a * angle * Math.sin(angle);

            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

/*
    public static void colord(GL gl, Color color) {
        colord(gl, color, 1.0);
    }

    public static void colord(GL gl, Color color, double alfa) {
        gl.glColor4f(
                MyColor.colorAsDouble(color.getRed()),
                MyColor.colorAsDouble(color.getGreen()),
                MyColor.colorAsDouble(color.getBlue()),
                alfa);
    }
*/

    /**
     * Convert point coordinates from the window coordinate system to the OpenGL world coordinates.
     *
     * @param gl    Current OpenGL context
     * @param glu   GLU library
     * @param point Point coordinates in window
     * @return converted point in OpenGL world coordinate system
     */
    public static double[] window2world(GL gl, GLU glu, Point point) {
        int viewport[] = new int[4];
        double mvmatrix[] = new double[16];
        double projmatrix[] = new double[16];

        double wcoord[] = new double[4]; // wx, wy, wz

        gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
        gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, mvmatrix, 0);
        gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, projmatrix, 0);

        /* note viewport[3] is height of window in pixels */
        double x = point.getX();
        double y = point.getY();


        glu.gluUnProject(x, viewport[3] - y - 1, 0.0, mvmatrix, 0, projmatrix, 0, viewport, 0, wcoord, 0);

        LOGGER.debug("Cursot window coordinates: [" + point.getX() + ";" + point.getY() + "]");
        LOGGER.debug("Cursor World coordinates: [" + wcoord[0] + ";" + wcoord[1] + "]");

        return wcoord;
    }

    public static void square(GL gl, double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
        gl.glPolygonMode(GL.GL_FRONT_FACE, GL.GL_FILL);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2d(x0, y0);
        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glEnd();
    }
}
