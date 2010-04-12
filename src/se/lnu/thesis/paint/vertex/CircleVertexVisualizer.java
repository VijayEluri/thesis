package se.lnu.thesis.paint.vertex;

import se.lnu.thesis.paint.AbstractGraphElementVisualizer;
import se.lnu.thesis.paint.GraphVisualizer;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class CircleVertexVisualizer extends AbstractGraphElementVisualizer {

    private int radius = 7;

    public CircleVertexVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public void draw(Object node) {
        drawCircle(node);
    }

    private void drawCircle(Object node) {
        Point2D position = getVisualizer().getLayout().transform(node);

        getVisualizer().getApplet().ellipse(
                new Float(position.getX()),
                new Float(position.getY()),
                radius,
                radius);

    }


    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
