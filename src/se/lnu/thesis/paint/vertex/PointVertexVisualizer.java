package se.lnu.thesis.paint.vertex;

import se.lnu.thesis.paint.AbstractGraphElementVisualizer;
import se.lnu.thesis.paint.GraphVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;

import javax.media.opengl.GL;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class PointVertexVisualizer extends AbstractGraphElementVisualizer {

    public PointVertexVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public PointVertexVisualizer(GraphWithSubgraphVisualizer visualizer, Color color) {
        super(visualizer, color);
    }

    protected void drawShape(Object node) {
        gl().glBegin(GL.GL_POINTS);
        gl().glVertex2d(getVisualizer().getLayout().getX(node), getVisualizer().getLayout().getY(node));
        gl().glEnd();
    }
}