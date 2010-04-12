package se.lnu.thesis.paint.edge;

import se.lnu.thesis.paint.AbstractGraphElementVisualizer;
import se.lnu.thesis.paint.GraphVisualizer;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 0:46:41
 * <p/>
 * Simple implementation of the edge visualisation as line
 */
public class LineEdgeVisualizer extends AbstractGraphElementVisualizer {

    public LineEdgeVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }

    public void draw(Object edge) {

        Point2D start = getVisualizer().getLayout().transform(getVisualizer().getGraph().getSource(edge));
        Point2D end = getVisualizer().getLayout().transform(getVisualizer().getGraph().getDest(edge));

        getVisualizer().getApplet().line(

                new Float(start.getX()),
                new Float(start.getY()),
                new Float(end.getX()),
                new Float(end.getY()));

    }

}
