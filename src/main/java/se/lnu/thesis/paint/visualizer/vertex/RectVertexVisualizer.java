package se.lnu.thesis.paint.visualizer.vertex;

import com.sun.opengl.util.GLUT;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.MyColor;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 12.04.2010
 * Time: 1:39:15
 */
public class RectVertexVisualizer extends CircleVertexVisualizer {

    public RectVertexVisualizer(MyColor color) {
        super(color);
    }

    public RectVertexVisualizer(MyColor color, double radius) {
        super(color, radius);
    }

    public RectVertexVisualizer(double size) {
        setRadius(size);
    }

    @Override
    protected void drawShape(Element element) {
        Point2D p = element.getPosition();

        gl().glPushMatrix();

        gl().glTranslated(p.getX(), p.getY(), 0.0);

        DrawingUtils.rect(gl(), -getRadius(), getRadius(), getRadius(), getRadius(), getRadius(), -getRadius(), -getRadius(), -getRadius());

        gl().glPopMatrix();
    }

}
