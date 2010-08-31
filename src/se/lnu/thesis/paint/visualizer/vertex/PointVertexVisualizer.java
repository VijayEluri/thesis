package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.paint.element.AbstractGraphElement;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.AbstractGraphElementVisualizer;

import javax.media.opengl.GL;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class PointVertexVisualizer extends AbstractGraphElementVisualizer {

    public PointVertexVisualizer(Color color) {
        super(color);
    }

    protected void drawShape(AbstractGraphElement element) {
        gl().glBegin(GL.GL_POINTS);
        gl().glVertex2d(((VertexElement) element).getPosition().getX(), ((VertexElement) element).getPosition().getY());
        gl().glEnd();
    }
}