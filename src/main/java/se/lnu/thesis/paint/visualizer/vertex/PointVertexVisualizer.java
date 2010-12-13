package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.element.Element;
import se.lnu.thesis.paint.visualizer.AbstractElementVisualizer;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class PointVertexVisualizer extends AbstractElementVisualizer {

    public PointVertexVisualizer(MyColor color) {
        super(color);
    }

    protected void drawShape(Element element) {
        gl().glBegin(GL.GL_POINTS);
        gl().glVertex2d(element.getPosition().getX(), element.getPosition().getY());
        gl().glEnd();
    }
}