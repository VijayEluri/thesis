package se.lnu.thesis.paint.visualizer;

import com.sun.opengl.util.GLUT;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.properties.ColorSchema;
import se.lnu.thesis.properties.PropertiesHolder;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 */
public abstract class AbstractElementVisualizer implements ElementVisualizer {

    protected ColorSchema colorSchema = PropertiesHolder.getInstance().getColorSchema(); // TODO move initialization to constructor

    private GLAutoDrawable drawable; // OpenGL drawing context

    private GLU glu;
    private GLUT glut;

    private MyColor mainColor;
    private MyColor selectionColor = colorSchema.getSelection(); // TODO move initialization to constructor
    private MyColor subgraphColor = colorSchema.getSubgraph(); // TODO move initialization to constructor
    private MyColor focusedColor = colorSchema.getFocusing();// TODO move initialization to constructor

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

        drawingColor(element); // set drawing color
        drawShape(element); // draw object

        if (element.getId() != null) {
            gl().glPopName();
        }

    }

    protected abstract void drawShape(Element element);

    protected void drawingColor(Element element) {
        if (element.isFocused()) {
            setCurrentDrawingColor(getFocusedColor());
        } else {
            if (element.isSelected()) {
                setCurrentDrawingColor(getSelectionColor());
            } else {
                if (element.isHighlighted()) {
                    setCurrentDrawingColor(getSubgraphColor());
                } else {
                    setCurrentDrawingColor(getMainColor());
                }
            }
        }
    }

    public void setCurrentDrawingColor(MyColor color) {
        gl().glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlfa());
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

    public GL gl() {
        return getDrawable().getGL();
    }

    public GLU glu() {
        if (glu == null) {
            glu = new GLU();
        }

        return glu;
    }

    public GLUT glut() {
        if (glut == null) {
            glut = new GLUT();
        }

        return glut;
    }
}
