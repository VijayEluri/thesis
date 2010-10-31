package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.paint.element.DimensionalContainer;
import se.lnu.thesis.paint.element.Element;
import se.lnu.thesis.paint.element.LevelPreview;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.utils.DrawingUtils;

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
public class LevelVisualizer implements ElementVisualizer {

    public static final int DEFAULT_LINE_LENGTH = 2; // 2% of the whole horizontal level length
    public static final float DEFAULT_LINE_THICKNESS = 1.5f;
    public static final Color DEFAULT_LINE_COLOR = Color.GRAY;

    public static final Color DEFAULT_LEVEL_BACKGROUND = Color.BLACK;

    private int lineLength = DEFAULT_LINE_LENGTH; // length in percents
    private float lineThickness = DEFAULT_LINE_THICKNESS;
    private Color lineColor = DEFAULT_LINE_COLOR;

    private Color background = DEFAULT_LEVEL_BACKGROUND;

    protected GL gl;

    public void draw(GLAutoDrawable drawable, Element element) {
        gl = drawable.getGL(); // update GL context

        LevelPreview levelPreview = (LevelPreview) element;

        if (levelPreview.isDrawed()) {

            if (element.getId() != null) {
                gl.glPushName(element.getId()); // set id
            }

            drawLevelBox(levelPreview);

            drawLevelLines(levelPreview);

            levelPreview.drawContent(drawable);

            if (element.getId() != null) {
                gl.glPopName();
            }
        }
    }

    protected void drawLevelBox(DimensionalContainer container) {
        Point2D p = container.getPosition();
        Point2D d = container.getDimension();

        DrawingUtils.colord(gl, getBackground());
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2d(p.getX(), p.getY());
        gl.glVertex2d(p.getX() + d.getX(), p.getY());
        gl.glVertex2d(p.getX() + d.getX(), p.getY() - d.getY());
        gl.glVertex2d(p.getX(), p.getY() - d.getY());

        gl.glEnd();
    }

    protected void drawLevelLines(DimensionalContainer container) {
        Point2D p = container.getPosition();
        Point2D d = container.getDimension();

        double x_length = container.getDimension().getX() * (lineLength / 100.0);

        gl.glLineWidth(lineThickness);
        DrawingUtils.colord(gl, lineColor); // lines colord


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

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
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
