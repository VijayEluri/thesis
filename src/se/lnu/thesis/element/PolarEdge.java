package se.lnu.thesis.element;

import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.visualizer.element.ElementVisualizerFactory;
import static se.lnu.thesis.utils.GraphUtils.isRoot;
import se.lnu.thesis.utils.IdUtils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:44:55
 */
public class PolarEdge extends EdgeElement {

    public static PolarEdge init(PolarDendrogramLayout layout, Object o) {
        PolarEdge result = new PolarEdge();

        result.setId(IdUtils.next());
        result.setObject(o);

        Object source = layout.getGraph().getSource(o);
        result.setFrom(source);

        Object target = layout.getGraph().getDest(o);
        result.setTo(target);

        result.setFromRoot(isRoot(layout.getGraph(), source));

        result.setStartPosition(((VertexElement) (layout.getRoot().getElementByObject(source))).getPosition());
        result.setDummyNodePosition(layout.getDummyNode(source, target));
        result.setEndPosition(((VertexElement) (layout.getRoot().getElementByObject(target))).getPosition());

        result.setSourceRadius(layout.getNodeRadius(source));

        result.setSourceAngle(layout.getNodeAngle().get(source));
        result.setDestAngle(layout.getNodeAngle().get(target));

        result.setVisualizer(ElementVisualizerFactory.getInstance().getPolarDendrogramEdgeVisializer());

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