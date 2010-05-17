package se.lnu.thesis.paint.vertex;

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
public class TriangleVertexVisualizer extends CircleVertexVisualizer {

    public TriangleVertexVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public TriangleVertexVisualizer(GraphWithSubgraphVisualizer visualizer, Color color) {
        super(visualizer, color);
    }

    protected void drawShape(Object node) {
        gl().glPushMatrix();

        gl().glTranslated(getVisualizer().getLayout().getX(node), getVisualizer().getLayout().getY(node), 0.0);

        gl().glPolygonMode(GL.GL_FRONT_FACE, GL.GL_FILL);
        DrawingUtils.circle(gl(), getRadius(), 3);
        gl().glPopMatrix();
    }

}
