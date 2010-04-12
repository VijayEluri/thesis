package se.lnu.thesis.paint.vertex;

import se.lnu.thesis.paint.GraphVisualizer;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 12.04.2010
 * Time: 1:39:15
 */
public class RectVertexVisualizer extends CircleVertexVisualizer {

    public RectVertexVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public void draw(Object vertex) {
        Point2D position = getVisualizer().getLayout().transform(vertex);

        getVisualizer().getApplet().rectMode(getVisualizer().getApplet().CENTER);

        getVisualizer().getApplet().rect(
                new Float(position.getX()),
                new Float(position.getY()),
                getRadius(),
                getRadius());

    }

}
