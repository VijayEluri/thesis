package se.lnu.thesis.paint.edge;

import se.lnu.thesis.layout.AbstractPolarDendrogramLayout;
import se.lnu.thesis.paint.AbstractGraphElementVisualizer;
import se.lnu.thesis.paint.Visualizer;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 0:46:41
 * <p/>
 * Simple implementation of the edge visualisation as line
 */
public class PolarDendrogramEdgeVisualizer extends AbstractGraphElementVisualizer {

    private AbstractPolarDendrogramLayout layout;

    public PolarDendrogramEdgeVisualizer(Visualizer visualizer) {
        super(visualizer);

        layout = (AbstractPolarDendrogramLayout) getVisualizer().getLayout();
    }

    public void draw(Object edge) {

        getVisualizer().getApplet().stroke(255); // set edge color to white
        getVisualizer().getApplet().noFill();


        Object source = getVisualizer().getGraph().getSource(edge);
        Object dest = getVisualizer().getGraph().getDest(edge);

        Point2D start = getVisualizer().getLayout().transform(getVisualizer().getGraph().getSource(edge));
        Point2D end = getVisualizer().getLayout().transform(getVisualizer().getGraph().getDest(edge));

        // from root draw simple line
        if (GraphUtils.getInstance().isRoot(getVisualizer().getGraph(), source)) {

            drawLine(start, end);

        } else {
            Point2D dummyNode = layout.getDummyNode(source, dest);

            drawLine(dummyNode, end);

            drawArc(source, dest);
        }


    }

    private void drawLine(Point2D start, Point2D end) {
        getVisualizer().getApplet().line(
                new Float(start.getX()),
                new Float(start.getY()),
                new Float(end.getX()),
                new Float(end.getY()));
    }

    private void drawArc(Object source, Object dest) {
        Double sourceAngle = (Double) layout.getNodeAngle().get(source);
        Double destAngle = (Double) layout.getNodeAngle().get(dest);

        float radius = new Float(layout.getNodeRadius(source)) * 2;

        double startAngle = Utils.min(sourceAngle, destAngle);
        double endAngle = Utils.max(sourceAngle, destAngle);

        getVisualizer().getApplet().arc(
                layout.getXCenter(),
                layout.getYCenter(),
                radius,
                radius,
                new Float(Utils.inRadians(startAngle)),
                new Float(Utils.inRadians(endAngle)));
    }

}