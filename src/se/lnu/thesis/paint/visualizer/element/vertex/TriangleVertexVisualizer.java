package se.lnu.thesis.paint.visualizer.element.vertex;

import se.lnu.thesis.paint.element.AbstractGraphElement;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.utils.DrawingUtils;

import javax.media.opengl.GL;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class TriangleVertexVisualizer extends CircleVertexVisualizer {

    public TriangleVertexVisualizer(Color color) {
        super(color);
    }

    protected void drawShape(AbstractGraphElement element) {
        gl().glPushMatrix();

        gl().glTranslated(((VertexElement) element).getPosition().getX(), ((VertexElement) element).getPosition().getY(), 0.0);

        gl().glPolygonMode(GL.GL_FRONT_FACE, GL.GL_FILL);
        DrawingUtils.circle(gl(), getRadius(), 3);
        gl().glPopMatrix();
    }

}
