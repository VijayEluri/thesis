package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.paint.element.DimensionalContainer;
import se.lnu.thesis.paint.element.Element;
import se.lnu.thesis.paint.element.LevelPreview;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.utils.Utils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 22.10.2010
 * Time: 16:13:07
 */
public class LevelPreviewVisualizer implements ElementVisualizer {

    public static final int DEFAULT_LINE_LENGTH = 2; // 2% of the whole horizontal level length
    public static final float DEFAULT_LINE_THICKNESS = 1.5f;
    public static final Color DEFAULT_LINE_COLOR = Color.GRAY;

    private int lineLength = DEFAULT_LINE_LENGTH; // length in percents
    private float lineThickness = DEFAULT_LINE_THICKNESS;
    private Color lineColor = DEFAULT_LINE_COLOR;

    public void draw(GLAutoDrawable drawable, Element element) {
        LevelPreview levelPreview = (LevelPreview) element;

        if (levelPreview.isDrawed()) {
            GL gl = drawable.getGL();

            if (element.getId() != null) {
                gl.glPushName(element.getId()); // set tag id
            }

            drawLevelLines(drawable, levelPreview);

            levelPreview.drawContent(drawable);

            if (element.getId() != null) {
                gl.glPopName();
            }
        }
    }

    protected void drawLevelLines(GLAutoDrawable drawable, DimensionalContainer container) {
        Point2D p = container.getPosition();
        Point2D d = container.getDimension();

        double x_length = container.getDimension().getX() * (lineLength / 100.0);

        GL gl = drawable.getGL();

        gl.glLineWidth(lineThickness);
        Utils.color(gl, lineColor); // lines color


        gl.glBegin(GL.GL_LINES);

        // left up
        gl.glVertex2d(p.getX(), p.getY());
        gl.glVertex2d(p.getX() + x_length, p.getY());

        // right up
        gl.glVertex2d((p.getX() + d.getX()) - x_length, p.getY());
        gl.glVertex2d(p.getX() + d.getX(), p.getY());

        // left down
        gl.glVertex2d(p.getX(), p.getY() - d.getY());
        gl.glVertex2d(p.getX() + x_length, p.getY() - d.getY());

        // right down
        gl.glVertex2d((p.getX() + d.getX()) - x_length, p.getY() - d.getY());
        gl.glVertex2d(p.getX() + d.getX(), p.getY() - d.getY());

        gl.glEnd();
    }

    public int getLineLength() {
        return lineLength;
    }

    /**
     * Set level line length in percents
     *
     * @param lineLength In percent from whole level length
     */
    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public float getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }
}
