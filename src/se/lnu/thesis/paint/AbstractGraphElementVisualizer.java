package se.lnu.thesis.paint;

import processing.core.PApplet;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 */
public abstract class AbstractGraphElementVisualizer implements GraphElementVisualizer {

    public static final Color DEFAULT_COLOR = Color.WHITE; // default color for graph elements visualisation

    private GraphVisualizer visualizer;

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

    public void draw(Object element) {
        colorProperty();
        drawShape(element);
    }

    protected abstract void drawShape(Object element);

    protected void colorProperty() {
        canvas().fill(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), getColor().getAlpha());
        canvas().stroke(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), getColor().getAlpha());
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

    public PApplet canvas() {
        return getVisualizer().getApplet();
    }
}
