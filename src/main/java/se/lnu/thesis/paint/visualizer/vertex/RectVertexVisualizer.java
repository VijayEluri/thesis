package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.element.Element;

import javax.media.opengl.GL;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 12.04.2010
 * Time: 1:39:15
 */
public class RectVertexVisualizer extends CircleVertexVisualizer {

    public RectVertexVisualizer(Color color) {
        super(color);
    }

    public RectVertexVisualizer(Color color, double radius) {
        super(color, radius);
    }

    public RectVertexVisualizer(double size) {
        setRadius(size);
    }

    @Override
    protected void drawShape(Element element) {
        gl().glPushMatrix();

        gl().glTranslated(element.getPosition().getX(), element.getPosition().getY(), 0.0);

        gl().glPolygonMode(GL.GL_FRONT_FACE, GL.GL_FILL);
        gl().glBegin(GL.GL_QUADS);
        gl().glVertex2d(-getRadius(), getRadius());
        gl().glVertex2d(getRadius(), getRadius());
        gl().glVertex2d(getRadius(), -getRadius());
        gl().glVertex2d(-getRadius(), -getRadius());
        gl().glEnd();

        gl().glPopMatrix();
    }
}
