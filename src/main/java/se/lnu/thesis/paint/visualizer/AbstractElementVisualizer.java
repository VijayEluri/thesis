package se.lnu.thesis.paint.visualizer;

import se.lnu.thesis.element.Element;
import se.lnu.thesis.utils.MyColor;
import se.lnu.thesis.properties.ColorSchema;
import se.lnu.thesis.properties.PropertiesHolder;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 */
public abstract class AbstractElementVisualizer implements ElementVisualizer {

    private ColorSchema colorSchema = PropertiesHolder.getInstance().getColorSchema();

    private GLAutoDrawable drawable; // OpenGL drawing context

    private GLU glu;

    private MyColor mainColor;
    private MyColor selectionColor = colorSchema.getSelection();
    private MyColor subgraphColor = colorSchema.getSubgraph();
    private MyColor focusedColor = colorSchema.getFocusing();

    protected AbstractElementVisualizer() {

    }

    public AbstractElementVisualizer(MyColor mainColor) {
        this.mainColor = mainColor;
    }

    public void draw(GLAutoDrawable drawable, Element element) {
        setDrawable(drawable); // update OpenGL drawing contex

        if (element.getId() != null) {
            gl().glPushName(element.getId()); // set tag id
        }

        drawingColor(element); // set tag levelBackgroud
        drawShape(element); // draw object

        if (element.getId() != null) {
            gl().glPopName();
        }
    }

    protected abstract void drawShape(Element element);

    protected void drawingColor(Element element) {
        if (element.isFocused()) {
            gl().glColor3f(getFocusedColor().getRed(), getFocusedColor().getGreen(), getFocusedColor().getBlue());
        } else {
            if (element.isSelected()) {
                gl().glColor3f(getSelectionColor().getRed(), getSelectionColor().getGreen(), getSelectionColor().getBlue());
            } else {
                if (element.isHighlighted()) {
                    gl().glColor3f(getSubgraphColor().getRed(), getSubgraphColor().getGreen(), getSubgraphColor().getBlue());
                } else {
                    gl().glColor3f(getMainColor().getRed(), getMainColor().getGreen(), getMainColor().getBlue());
                }
            }
        }
    }

    public MyColor getSubgraphColor() {
        return subgraphColor;
    }

    public MyColor getSelectionColor() {
        return selectionColor;
    }

    public MyColor getMainColor() {
        return mainColor;
    }

    public MyColor getFocusedColor() {
        return focusedColor;
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
