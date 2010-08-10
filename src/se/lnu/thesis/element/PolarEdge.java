package se.lnu.thesis.element;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:44:55
 */
public class PolarEdge extends EdgeElement {

    private boolean isFromRoot = false;

    private Point2D dummyNode;

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

    public Point2D getDummyNode() {
        return dummyNode;
    }

    public void setDummyNode(Point2D dummyNode) {
        this.dummyNode = dummyNode;
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