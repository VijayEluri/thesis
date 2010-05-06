package se.lnu.thesis.paint.edge;

import se.lnu.thesis.layout.AbstractPolarDendrogramLayout;
import se.lnu.thesis.paint.GraphVisualizer;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 0:46:41
 * <p/>
 * Simple implementation of the edge visualisation as line
 */
public class PolarDendrogramEdgeVisualizer extends AbstractEdgeVisualizer {

    protected AbstractPolarDendrogramLayout layout;

    public PolarDendrogramEdgeVisualizer(GraphVisualizer visualizer) {
        super(visualizer);

        layout = (AbstractPolarDendrogramLayout) getVisualizer().getLayout();
    }

    public PolarDendrogramEdgeVisualizer(GraphVisualizer visualizer, Color color) {
        super(visualizer, color);

        layout = (AbstractPolarDendrogramLayout) getVisualizer().getLayout();
    }

    protected void drawShape(Object edge) {
        Object sourceNode = source(edge);
        Object destNode = dest(edge);

        if (GraphUtils.getInstance().isRoot(getVisualizer().getGraph(), sourceNode)) { // from root draw simple line

            drawLine(p(sourceNode), p(destNode));

        } else {
            Point2D dummyNode = layout.getDummyNode(sourceNode, destNode);

            drawArc(sourceNode, destNode);

            drawLine(dummyNode, p(destNode));
        }
    }

    protected void drawLine(Point2D start, Point2D end) {
        canvas().line(
                new Float(start.getX()),
                new Float(start.getY()),
                new Float(end.getX()),
                new Float(end.getY()));
    }

    protected void drawArc(Object sourceNode, Object destNode) {
        Double sourceAngle = (Double) layout.getNodeAngle().get(sourceNode);
        Double destAngle = (Double) layout.getNodeAngle().get(destNode);

        Float radius = new Float(layout.getNodeRadius(sourceNode)) * 2;

        double startAngle = Utils.min(sourceAngle, destAngle);
        double endAngle = Utils.max(sourceAngle, destAngle);


        canvas().noFill();
        canvas().arc(
                layout.getXCenter(),
                layout.getYCenter(),
                radius,
                radius,
                new Float(Utils.inRadians(startAngle)),
                new Float(Utils.inRadians(endAngle)));
    }

}