package se.lnu.thesis.paint;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 */
public abstract class AbstractGraphElementVisualizer implements GraphElementVisualizer {

    public static final Color DEFAULT_COLOR = Color.WHITE; // default color for graph elements visualisation
    private static final double COLOR_MAX = 256.0;

    private GraphVisualizer visualizer;
    private GLAutoDrawable drawable; // OpenGL drawing context

    private GLU glu;
    private Color color = DEFAULT_COLOR;

    public AbstractGraphElementVisualizer() {

    }

    public AbstractGraphElementVisualizer(GraphVisualizer visualizer) {
        setVisualizer(visualizer);
    }

    public AbstractGraphElementVisualizer(Color color) {
        setColor(color);
    }

    public AbstractGraphElementVisualizer(GraphVisualizer visualizer, Color color) {
        setVisualizer(visualizer);
        setColor(color);
    }

    public void draw(GLAutoDrawable drawable, Object element) {
        setDrawable(drawable); // update OpenGL drawing contex

        colorProperty(); // set element color
        drawShape(element); // draw object
    }

    protected abstract void drawShape(Object element);

    protected void colorProperty() {
        gl().glColor3d(getRed(), getGreen(), getBlue());
    }

    public GraphVisualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(GraphVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getRed() {
        return getColor().getRed() / COLOR_MAX;
    }

    public double getGreen() {
        return getColor().getGreen() / COLOR_MAX;
    }

    public double getBlue() {
        return getColor().getBlue() / COLOR_MAX;
    }

    protected GLAutoDrawable getDrawable() {
        return drawable;
    }

    protected void setDrawable(GLAutoDrawable drawable) {
        this.drawable = drawable;
    }

    protected GL gl() {
        return getDrawable().getGL();
    }

    protected GLU glu() {
        if (glu == null) {
            glu = new GLU();
        }

        return glu;
    }


}
