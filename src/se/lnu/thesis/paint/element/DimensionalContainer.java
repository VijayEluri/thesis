package se.lnu.thesis.paint.element;

import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.10.2010
 * Time: 14:24:22
 */
public class DimensionalContainer extends AbstractContainer implements Dimensional {

    public static DimensionalContainer init(Object o) {
        DimensionalContainer result = new DimensionalContainer();

        result.setObject(o);

        result.objects = new HashSet<Object>();

        return result;
    }

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

    public void draw(GLAutoDrawable drawable) {
        if (isDrawed()) {
            drawContent(drawable);
        }
    }
}
