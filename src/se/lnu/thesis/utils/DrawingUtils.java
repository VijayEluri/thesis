package se.lnu.thesis.utils;

import javax.media.opengl.GL;
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

    public static final double COLOR_MAX_D = 256.0;
    public static final float COLOR_MAX_F = 256.0f;

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

    public static double colorAsDouble(int color) {
        return color / COLOR_MAX_D;
    }

    public static float colorAsFloat(int color) {
        return color / COLOR_MAX_F;
    }

    public static void colord(GL gl, Color color) {
        colord(gl, color, 1.0);
    }

    public static void colord(GL gl, Color color, double alfa) {
        gl.glColor4d(
                colorAsDouble(color.getRed()),
                colorAsDouble(color.getGreen()),
                colorAsDouble(color.getBlue()),
                alfa);
    }
}
