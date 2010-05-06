package se.lnu.thesis.paint.edge;

import se.lnu.thesis.paint.AbstractGraphElementVisualizer;
import se.lnu.thesis.paint.GraphVisualizer;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 27.04.2010
 * Time: 18:40:34
 */
public abstract class AbstractEdgeVisualizer extends AbstractGraphElementVisualizer {

    public AbstractEdgeVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public AbstractEdgeVisualizer(GraphVisualizer visualizer, Color color) {
        super(visualizer, color);
    }

    public Object source(Object edge) {
        return getVisualizer().getGraph().getSource(edge);
    }

    public Object dest(Object edge) {
        return getVisualizer().getGraph().getDest(edge);
    }

    public Point2D p(Object node) {
        return getVisualizer().getLayout().transform(node);
    }

}
