package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.element.Element;
import se.lnu.thesis.paint.visualizer.AbstractElementVisualizer;
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
public class CircleVertexVisualizer extends AbstractElementVisualizer {

    public static int SEGMENT_COUNT = 8;

    private double radius = 0.01;

    protected CircleVertexVisualizer() {

    }

    public CircleVertexVisualizer(MyColor color) {
        super(color);
    }

    public CircleVertexVisualizer(MyColor color, double radius) {
        super(color);
        setRadius(radius);
    }

    protected void drawShape(Element element) {
        gl().glPushMatrix();

        gl().glTranslated(element.getPosition().getX(), element.getPosition().getY(), 0.0);

        gl().glPolygonMode(GL.GL_FRONT_FACE, GL2.GL_FILL);

        DrawingUtils.circle(gl(), getRadius(), SEGMENT_COUNT);

        gl().glPopMatrix();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}