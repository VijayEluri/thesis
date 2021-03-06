package se.lnu.thesis.paint.visualizer.graph_level;


import com.jogamp.opengl.util.gl2.GLUT;
import se.lnu.thesis.element.DimensionalContainer;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.paint.visualizer.AbstractElementVisualizer;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 22.10.2010
 * Time: 16:13:07
 */
public class LevelVisualizer extends AbstractElementVisualizer {

    public static final int DEFAULT_LINE_LENGTH = 2; // 2% of the whole horizontal level length
    public static final float DEFAULT_LINE_THICKNESS = 1.5f;

    private int lineLength = DEFAULT_LINE_LENGTH; // length in percents
    private float lineThickness = DEFAULT_LINE_THICKNESS;

    private MyColor lineColor = colorSchema.getGoLevelLines();
    private MyColor focusingColor = colorSchema.getFocusing();
    private MyColor levelNumberColor = colorSchema.getGoLevelNumbers();

    private MyColor background = colorSchema.getBackground();

    @Override
    public void draw(GLAutoDrawable drawable, Element element) {
        setDrawable(drawable);

        DimensionalContainer container = (DimensionalContainer) element;

        if (container.isDrawn()) {

            if (element.getId() != null) {
                gl().glPushName(element.getId()); // set id
            }

            drawLevelBackground(container);

            drawLevelNumber(container);

            if (element.isFocused()) {
                drawLevelBorderBox(container);
            } else {
                drawLevelLines(container);
            }

            if (element.getId() != null) {
                gl().glPopName();
            }

            drawContent(drawable, container);
        }
    }

    @Deprecated
    protected void drawShape(Element element) {
    }

    protected void drawContent(GLAutoDrawable drawable, DimensionalContainer container) {
        for (Element element : container) {
            element.draw(drawable);
        }
    }

    protected void drawLevelBorderBox(DimensionalContainer container) {
        Point2D p = container.getPosition();
        Point2D d = container.getDimension();

        gl().glLineWidth(lineThickness);
        setCurrentDrawingColor(getFocusingColor());

        gl().glBegin(GL.GL_LINES);

        // top
        gl().glVertex2d(p.getX(), p.getY());
        gl().glVertex2d(p.getX() + d.getX(), p.getY());

        // right
        gl().glVertex2d(p.getX() + d.getX(), p.getY());
        gl().glVertex2d(p.getX() + d.getX(), p.getY() - d.getY());

        // down
        gl().glVertex2d(p.getX(), p.getY() - d.getY());
        gl().glVertex2d(p.getX() + d.getX(), p.getY() - d.getY());

        // left
        gl().glVertex2d(p.getX(), p.getY());
        gl().glVertex2d(p.getX(), p.getY() - d.getY());

        gl().glEnd();
    }

    protected void drawLevelBackground(DimensionalContainer container) {
        Point2D p = container.getPosition();
        Point2D d = container.getDimension();

        setCurrentDrawingColor(getBackground());

        gl().glBegin(GL2.GL_POLYGON);
        gl().glVertex2d(p.getX(), p.getY());
        gl().glVertex2d(p.getX() + d.getX(), p.getY());
        gl().glVertex2d(p.getX() + d.getX(), p.getY() - d.getY());
        gl().glVertex2d(p.getX(), p.getY() - d.getY());

        gl().glEnd();
    }

    protected void drawLevelLines(DimensionalContainer container) {
        Point2D p = container.getPosition();
        Point2D d = container.getDimension();

        double x_length = container.getDimension().getX() * (lineLength / 100.0);

        gl().glLineWidth(lineThickness);
        setCurrentDrawingColor(getLineColor());


        gl().glBegin(GL.GL_LINES);

        // left up
        gl().glVertex2d(p.getX(), p.getY());
        gl().glVertex2d(p.getX() + x_length, p.getY());

        // right up
        gl().glVertex2d((p.getX() + d.getX()) - x_length, p.getY());
        gl().glVertex2d(p.getX() + d.getX(), p.getY());

        // left down
        gl().glVertex2d(p.getX(), p.getY() - d.getY());
        gl().glVertex2d(p.getX() + x_length, p.getY() - d.getY());

        // right down
        gl().glVertex2d((p.getX() + d.getX()) - x_length, p.getY() - d.getY());
        gl().glVertex2d(p.getX() + d.getX(), p.getY() - d.getY());

        gl().glEnd();
    }

    protected void drawLevelNumber(DimensionalContainer container) {
        Point2D p = new Point2D.Double(container.getPosition().getX(), container.getPosition().getY() - container.getDimension().getY() / 2);

        setCurrentDrawingColor(getLevelNumberColor());

        gl().glRasterPos2d(p.getX(), p.getY());
        glut().glutBitmapString(GLUT.BITMAP_8_BY_13, container.getObject().toString());
    }

    public MyColor getBackground() {
        return background;
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

    public MyColor getLineColor() {
        return lineColor;
    }

    public MyColor getFocusingColor() {
        return focusingColor;
    }

    public MyColor getLevelNumberColor() {
        return levelNumberColor;
    }

}
