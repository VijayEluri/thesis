package se.lnu.thesis.paint.state;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.gl2.GLUT;
import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.properties.PropertiesHolder;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.nio.IntBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 10.11.2010
 * Time: 3:45:59
 * <p/>
 * <p/>
 * This class contains methods to pick some element
 */
public abstract class FocusableState extends GraphState {

    public static final Logger LOGGER = Logger.getLogger(FocusableState.class);

    public static final int BUFSIZE = 512;

    public static final double CURSOR_X_SIZE = 1.0;
    public static final double CURSOR_Y_SIZE = 1.0;

    private Container container;
    private Element current;

    protected void drawCurrentState(GLAutoDrawable drawable) {
        if (getContainer() != null) {

            if (getCursor() != null) {
                focusing(drawable, getContainer());
            }

            getContainer().drawContent(drawable);

            if (getCurrent() != null) {
                drawTooltip(drawable, getCurrent());
            }
        }
    }

    /**
     * Draw tooltip for focused element
     *
     * @param drawable OpenGL drawing context
     * @param element  Focused element
     */
    public void drawTooltip(GLAutoDrawable drawable, Element element) {   // TODO ,ove it to separate class
        GL2 gl = (GL2) drawable.getGL();
        if (element.isFocused()) {
            MyColor color = PropertiesHolder.getInstance().getColorSchema().getVerticesTooltips();
            gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());

            Point2D p = element.getPosition();
            gl.glRasterPos2d(p.getX(), p.getY());
            glut().glutBitmapString(GLUT.BITMAP_8_BY_13, element.getTooltip());
        }
    }

    protected void focusing(GLAutoDrawable drawable, Container container) {
        GL2 gl = (GL2) drawable.getGL();

        IntBuffer selectBuffer = BufferUtil.newIntBuffer(BUFSIZE);

        int viewport[] = new int[4];

        gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

        gl.glSelectBuffer(BUFSIZE, selectBuffer);
        gl.glRenderMode(GL2.GL_SELECT);

        gl.glInitNames();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        glu().gluPickMatrix(getCursor().getX(), (viewport[3] - getCursor().getY()), CURSOR_X_SIZE, CURSOR_Y_SIZE, viewport, 0);


        container.drawContent(drawable);


        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glFlush();


        int hits = gl.glRenderMode(GL2.GL_RENDER);

        LOGGER.debug("There are " + hits + " ids found.");

        if (hits > 0) { // founded something?
            int id = selectBuffer.get(hits * 4 - 1); // get last id in the stack

            LOGGER.debug("Looking for id '" + id + "'");

            unfocus();
            focus(id, container);
        } else {
            unfocus();
        }

    }

    protected void focus(int id, Container container) {
        Element element = container.getElementById(id);

        if (element != null) {
            setCurrent(element);
            getCurrent().setFocused(true);

            String label = getGraphController().getGraph().getLabel(getCurrent().getObject());

            LOGGER.debug("Focused vertex " + getCurrent().getObject() + " [" + label + "]");

            Scene.getInstance().getMainWindow().setStatusBarText("Focused vertex " + label);
        }

    }

    public void unfocus() {
        if (getCurrent() != null) {
            getCurrent().setFocused(false);
        }

        setCurrent(null);

        Scene.getInstance().getMainWindow().setStatusBarText("");
    }

    /**
     * Mouse has left view port area then set current cursor position to <code>null</code>.
     * And unfocus elements.
     */
    @Override
    public void mouseExited() {
        super.mouseExited();
        unfocus();
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Element getCurrent() {
        return current;
    }

    public void setCurrent(Element current) {
        this.current = current;
    }
}
