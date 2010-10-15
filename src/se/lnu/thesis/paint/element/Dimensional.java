package se.lnu.thesis.paint.element;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.10.2010
 * Time: 14:22:30
 * <p/>
 * Interface for elements who has dimension and contains elements inside
 */
public interface Dimensional {

    Point2D getDimension();

    void setDimension(Point2D d);

}
