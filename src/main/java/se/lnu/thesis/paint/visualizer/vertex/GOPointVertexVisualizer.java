package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.element.Element;
import se.lnu.thesis.paint.visualizer.AbstractElementVisualizer;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public class GOPointVertexVisualizer extends AbstractElementVisualizer {

    public static final int DEFAULT_SEGMENT_COUNT = 10;
    public static final double DEFAULT_POINTING_RADIUSE = 0.015;
    public static final float RING_THICKNESS = 1.0f;


    private double pointingRadius = DEFAULT_POINTING_RADIUSE;
    private int segmentCount = DEFAULT_SEGMENT_COUNT;

    public GOPointVertexVisualizer(MyColor color) {
        super(color);
    }

    protected void drawShape(Element element) {
        gl().glBegin(GL.GL_POINTS);
        gl().glVertex2d(element.getPosition().getX(), element.getPosition().getY());
        gl().glEnd();

        if (element.isSelected()) {
            gl().glPushMatrix();
            gl().glTranslated(element.getPosition().getX(), element.getPosition().getY(), 0.0);
            DrawingUtils.ring(gl(), pointingRadius, segmentCount, RING_THICKNESS);
            gl().glPopMatrix();
        }

    }


}