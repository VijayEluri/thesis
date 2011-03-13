package se.lnu.thesis.element;

import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:44:55
 */
public class PolarEdge extends EdgeElement {

    public static PolarEdge init(PolarDendrogramLayout layout, Object o, Object from, Object to) {
        return PolarEdge.init(
                o,
                from,
                to,

                GraphUtils.isRoot(layout.getGraph(), from),

                layout.getRoot().getElementByObject(from).getPosition(),
                layout.getDummyNode(from, to),
                layout.getRoot().getElementByObject(to).getPosition(),

                layout.getNodeRadius(from),

                layout.getNodeAngle().get(from),
                layout.getNodeAngle().get(to),

                ElementVisualizerFactory.getInstance().getPolarDendrogramEdgeVisializer()
        );
    }

    public static PolarEdge init(Object o,
                                 Object from,
                                 Object to,
                                 boolean isFromRoot,
                                 Point2D start,
                                 Point2D dummyNode,
                                 Point2D end,
                                 double fromRadius,
                                 double fromAngle,
                                 double toAngle,
                                 ElementVisualizer visualizer) {

        PolarEdge result = new PolarEdge();

        result.setObject(o);

        result.setFrom(from);
        result.setTo(to);

        result.setFromRoot(isFromRoot);

        result.setStartPosition(start);
        result.setDummyNodePosition(dummyNode);
        result.setEndPosition(end);

        result.setSourceRadius(fromRadius);

        result.setSourceAngle(fromAngle);
        result.setDestAngle(toAngle);

        result.setVisualizer(visualizer);

        return result;
    }

    private boolean isFromRoot = false;

    private Point2D dummyNodePosition;

    private double sourceRadius;
    private Double sourceAngle;
    private Double destAngle;
    private double xCenter;
    private double yCenter;

    public PolarEdge() {

    }

    public boolean isFromRoot() {
        return isFromRoot;
    }

    public void setFromRoot(boolean fromRoot) {
        isFromRoot = fromRoot;
    }

    public Point2D getDummyNodePosition() {
        return dummyNodePosition;
    }

    public void setDummyNodePosition(Point2D dummyNodePosition) {
        this.dummyNodePosition = dummyNodePosition;
    }

    public double getSourceRadius() {
        return sourceRadius;
    }

    public Double getSourceAngle() {
        return sourceAngle;
    }

    public void setSourceRadius(double sourceRadius) {
        this.sourceRadius = sourceRadius;
    }

    public void setSourceAngle(Double sourceAngle) {
        this.sourceAngle = sourceAngle;
    }

    public void setDestAngle(Double destAngle) {
        this.destAngle = destAngle;
    }

    public Double getDestAngle() {
        return destAngle;
    }

    public double getXCenter() {
        return xCenter;
    }

    public void setXCenter(double xCenter) {
        this.xCenter = xCenter;
    }

    public double getYCenter() {
        return yCenter;
    }

    public void setYCenter(double yCenter) {
        this.yCenter = yCenter;
    }

}