package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.element.AbstractElement;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class TriangleVertexVisualizer extends CircleVertexVisualizer {

    public TriangleVertexVisualizer(MyColor color) {
        super(color);
    }

    protected void drawShape(AbstractElement element) {
        gl().glPushMatrix();

        gl().glTranslated(element.getPosition().getX(), element.getPosition().getY(), 0.0);

        gl().glPolygonMode(GL.GL_FRONT_FACE, GL2.GL_FILL);
        DrawingUtils.circle(gl(), getRadius(), 3);
        gl().glPopMatrix();
    }

}
