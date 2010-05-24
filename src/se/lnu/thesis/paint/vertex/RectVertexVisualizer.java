package se.lnu.thesis.paint.vertex;

import se.lnu.thesis.paint.GraphVisualizer;

import javax.media.opengl.GL;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 12.04.2010
 * Time: 1:39:15
 */
public class RectVertexVisualizer extends CircleVertexVisualizer {

    public RectVertexVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public RectVertexVisualizer(GraphVisualizer visualizer, Color color) {
        super(visualizer, color);
    }

    @Override
    protected void drawShape(Object node) {
        gl().glPushMatrix();

        gl().glTranslated(getVisualizer().getLayout().getX(node), getVisualizer().getLayout().getY(node), 0.0);

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
