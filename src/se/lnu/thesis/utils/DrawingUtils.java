package se.lnu.thesis.utils;

import javax.media.opengl.GL;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.05.2010
 * Time: 19:13:50
 * <p/>
 * OpenGL drawing halping class.
 */
public class DrawingUtils {

    /**
     * Draw polygon circle
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

}
