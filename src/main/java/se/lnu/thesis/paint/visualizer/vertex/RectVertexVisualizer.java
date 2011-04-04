package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.element.Element;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.MyColor;

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
        gl().glPushMatrix();

        gl().glTranslated(element.getPosition().getX(), element.getPosition().getY(), 0.0);

        DrawingUtils.rect(gl(), -getRadius(), getRadius(), getRadius(), getRadius(), getRadius(), -getRadius(), -getRadius(), -getRadius());

        gl().glPopMatrix();
    }

}
