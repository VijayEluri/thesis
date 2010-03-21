package se.lnu.thesis.paint.node;

import se.lnu.thesis.paint.Visualizer;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class CircleNodeVisualizer extends AbstractNodeVisualizer {

    private int radius = 7;

    public CircleNodeVisualizer(Visualizer visualizer) {
        super(visualizer);
    }

    public void color() {
        getVisualizer().getApplet().fill(255);
        getVisualizer().getApplet().stroke(255);
    }

    private void drawCircle(Object node) {
        Point2D position = getVisualizer().getLayout().transform(node);

        getVisualizer().getApplet().ellipse(
                new Float(position.getX()),
                new Float(position.getY()),
                radius,
                radius);

    }

    public void drawRoot(Object root) {
        color();
        drawCircle(root);
    }

    public void drawNode(Object node) {

        // set node color
/*
        getVisualizer().getApplet().fill(255);
        getVisualizer().getApplet().stroke(255);

        drawCircle(node);
*/
    }

    public void drawLeaf(Object leaf) {
        getVisualizer().getApplet().stroke(255, 0, 0);
        getVisualizer().getApplet().fill(255, 0, 0);

        drawCircle(leaf);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
