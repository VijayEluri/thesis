package se.lnu.thesis.paint.element;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.10.2010
 * Time: 14:24:22
 */
public class DimensionalCompositeElement extends AbstractCompositeElement implements Dimensional {

    Point2D dimension;

    public Point2D getDimension() {
        return dimension;
    }

    public void setDimension(Point2D d) {
        if (d != null) {
            if (dimension == null) {
                dimension = new Point2D.Double();
            }

            dimension.setLocation(d.getX(), d.getY());
        }
    }
}
