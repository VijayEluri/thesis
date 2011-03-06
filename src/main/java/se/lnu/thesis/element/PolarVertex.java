package se.lnu.thesis.element;

import se.lnu.thesis.paint.visualizer.AbstractElementVisualizer;
import se.lnu.thesis.utils.IdGenerator;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 06.08.2010
 * Time: 15:34:35
 */
public class PolarVertex extends VertexElement {

    public static PolarVertex init(Object o, double angle, double radius, Point2D.Double center, AbstractElementVisualizer visualizer) {
        Point2D position = new Point2D.Double();
        Utils.computeOnCirclePosition(position, angle, radius, center.getX(), center.getY());

        PolarVertex result = new PolarVertex();

        result.setId(IdGenerator.next());

        result.setObject(o);
        result.setPosition(position);
        result.setVisualizer(visualizer);

        result.setAngle(angle);
        result.setRadius(radius);
        result.setCenter(center);

        return result;
    }

    private double angle;
    private double radius;
    private Point2D.Double center;

    @Deprecated
    public PolarVertex() {

    }

    public double getAngle() {
        return angle;
    }

    public double getRadius() {
        return radius;
    }

    public Point2D.Double getCenter() {
        return center;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setCenter(Point2D.Double center) {
        this.center = center;
    }

}
