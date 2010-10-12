package se.lnu.thesis.paint.element;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 17:20:17
 * To change this template use File | Settings | File Templates.
 */
public interface Positionable {
    Point2D getPosition();

    void setPosition(Point2D p);
}
