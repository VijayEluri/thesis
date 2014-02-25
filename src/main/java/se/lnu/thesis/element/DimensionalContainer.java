package se.lnu.thesis.element;

import com.google.common.collect.ImmutableSet;

import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.10.2010
 * Time: 14:24:22
 *
 * <p>
 * Container for drawing elements that has width and height (dimensions)
 * </p>
 */
public class DimensionalContainer extends AbstractContainer implements Dimensional {

    public static DimensionalContainer createContainer(Object o) {
        DimensionalContainer result = new DimensionalContainer();

        result.setObject(o);

        result.objects = new HashSet<Object>();

        return result;
    }

    public static DimensionalContainer createContainer(Object o, Collection objects) {
        DimensionalContainer result = new DimensionalContainer();

        result.setObject(o);

        result.objects = ImmutableSet.copyOf(objects);

        return result;
    }

    Point2D dimension;

    protected DimensionalContainer() {

    }

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

    public void setDimension(double width, double height) {
        setDimension(new Point2D.Double(width, height));
    }

    public void draw(GLAutoDrawable drawable) {
        if (isDrawn()) {
            super.drawContent(drawable);
        }
    }
}
