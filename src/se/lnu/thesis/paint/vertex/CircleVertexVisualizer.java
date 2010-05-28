package se.lnu.thesis.paint.vertex;

import se.lnu.thesis.paint.AbstractGraphElementVisualizer;
import se.lnu.thesis.paint.GraphVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;
import se.lnu.thesis.utils.DrawingUtils;

import javax.media.opengl.GL;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class CircleVertexVisualizer extends AbstractGraphElementVisualizer {

    public static int SEGMENT_COUNT = 8;

    private double radius = 0.01;

    public CircleVertexVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public CircleVertexVisualizer(GraphWithSubgraphVisualizer visualizer, Color color) {
        super(visualizer, color);
    }

    public CircleVertexVisualizer(GraphWithSubgraphVisualizer visualizer, Color color, double radius) {
        super(visualizer, color);
        setRadius(radius);
    }

    public CircleVertexVisualizer(GraphVisualizer visualizer, Color color) {
        super(visualizer, color);
    }

    protected void drawShape(Object node) {
        gl().glPushMatrix();

        gl().glTranslated(getVisualizer().getLayout().getX(node), getVisualizer().getLayout().getY(node), 0.0);

        gl().glPolygonMode(GL.GL_FRONT_FACE, GL.GL_FILL);

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