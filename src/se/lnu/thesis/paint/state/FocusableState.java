package se.lnu.thesis.paint.state;

import com.sun.opengl.util.BufferUtil;
import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.Element;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
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
        }
    }

    protected void focusing(GLAutoDrawable drawable, Container container) {
        GL gl = drawable.getGL();

        IntBuffer selectBuffer = BufferUtil.newIntBuffer(BUFSIZE);

        int viewport[] = new int[4];

        gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

        gl.glSelectBuffer(BUFSIZE, selectBuffer);
        gl.glRenderMode(GL.GL_SELECT);

        gl.glInitNames();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        getGlu().gluPickMatrix(getCursor().getX(), (viewport[3] - getCursor().getY()), CURSOR_X_SIZE, CURSOR_Y_SIZE, viewport, 0);


        container.drawContent(drawable);


        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glFlush();


        int hits = gl.glRenderMode(GL.GL_RENDER);

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
        }

        if (getCurrent() != null) {

            String label = getGraphController().getGraph().getLabel(getCurrent().getObject());

            LOGGER.debug("Focused vertex " + getCurrent().getObject() + " [" + label + "]");

            Scene.getInstance().getMainWindow().setStatusBarText("Focused vertex " + label);
        }

    }

    protected void unfocus() {
        if (getCurrent() != null) {
            getCurrent().setFocused(false);
        }

        setCurrent(null);
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
