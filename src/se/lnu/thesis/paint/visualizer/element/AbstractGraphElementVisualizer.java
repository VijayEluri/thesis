package se.lnu.thesis.paint.visualizer.element;

import se.lnu.thesis.element.AbstractGraphElement;
import se.lnu.thesis.utils.Utils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 */
public abstract class AbstractGraphElementVisualizer implements GraphElementVisualizer {

    public static final Color DEFAULT_MAIN_COLOR = Color.WHITE;      // default color for graph elements visualization
    public static final Color DEFAULT_SUBGRAPH_COLOR = Color.YELLOW; // default subgraph color
    public static final Color DEFAULT_SEECTION_COLOR = Color.GREEN;  // default selection color


    private GLAutoDrawable drawable; // OpenGL drawing context

    private GLU glu;

    private Color mainColor = DEFAULT_MAIN_COLOR;
    private Color selectionColor = DEFAULT_SEECTION_COLOR;
    private Color subgraphColor = DEFAULT_SUBGRAPH_COLOR;

    public AbstractGraphElementVisualizer() {

    }

    public AbstractGraphElementVisualizer(Color mainColor) {
        setMainColor(mainColor);
    }

    public void draw(GLAutoDrawable drawable, AbstractGraphElement element) {
        setDrawable(drawable); // update OpenGL drawing contex

        gl().glPushName(element.getId()); // set element id

        drawingColor(element); // set element color
        drawShape(element); // draw object

        gl().glPopName();
    }

    protected abstract void drawShape(AbstractGraphElement element);


    protected void drawingColor(AbstractGraphElement element) {
        if (element.isSelected()) {
            color(getSelectionColor());
        } else {
            if (element.isSubgraph()) {
                color(getSubgraphColor());
            } else {
                color(getMainColor());
            }
        }
    }

    protected void color(Color color) {
        gl().glColor3d(Utils.asDouble(color.getRed()),
                Utils.asDouble(color.getGreen()),
                Utils.asDouble(color.getBlue()));
    }

    public Color getSubgraphColor() {
        return subgraphColor;
    }

    public void setSubgraphColor(Color subgraphColor) {
        this.subgraphColor = subgraphColor;
    }

    public Color getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
    }

    public Color getMainColor() {
        return mainColor;
    }

    public void setMainColor(Color mainColor) {
        this.mainColor = mainColor;
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
