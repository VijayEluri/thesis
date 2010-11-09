package se.lnu.thesis.paint.state;

import com.sun.opengl.util.BufferUtil;
import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.element.Element;
import se.lnu.thesis.paint.element.ElementType;
import se.lnu.thesis.paint.element.GroupingElement;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;
import java.nio.IntBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 18:04:51
 */
public class NormalClusterState extends GraphState {

    public static final Logger LOGGER = Logger.getLogger(NormalClusterState.class);

    protected Element focusedElement;

    protected NormalClusterState() {
    }

    public NormalClusterState(GraphController controller) {
        setGraphController(controller);
    }

    protected void drawCurrentState(GLAutoDrawable drawable) {
        if (getGraphController().getRoot() != null) {

            if (cursor != null) {
                focusElement(drawable);
            }

            getGraphController().getRoot().drawContent(drawable);
        }
    }

    protected void focusElement(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        int selectBuf[] = new int[BUFSIZE];
        IntBuffer selectBuffer = BufferUtil.newIntBuffer(BUFSIZE);

        int viewport[] = new int[4];

        gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

        gl.glSelectBuffer(BUFSIZE, selectBuffer);
        gl.glRenderMode(GL.GL_SELECT);

        gl.glInitNames();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        getGlu().gluPickMatrix((double) cursor.x, (double) (viewport[3] - cursor.y), CURSOR_X_SIZE, CURSOR_Y_SIZE, viewport, 0);

        getGraphController().getRoot().drawContent(drawable);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glFlush();


        int hits = gl.glRenderMode(GL.GL_RENDER); // mouse hits
        selectBuffer.get(selectBuf);

        int count = selectBuf[0];

        if (count > 0) { // some figures?
            unfocus();
            focus(selectBuf[3]);
        } else {
            unfocus();
        }

        cursor = null;
    }

    protected void focus(int id) {
        Element element = getGraphController().getRoot().getElementById(id);

        if (element != null) {
            String label = getGraphController().getGraph().getLabel(element.getObject());

            LOGGER.info("Focused vertex " + element.getObject() + " [" + label + "]");

            Scene.getInstance().getMainWindow().setStatusBarText("Focused vertex " + label);

            this.focusedElement = element;
            this.focusedElement.setFocused(true);
        }
    }

    protected void unfocus() {
        if (focusedElement != null) {
            focusedElement.setFocused(false);
        }

        focusedElement = null;
    }

    @Override
    public void leftMouseButtonClicked(Point point) {
        LOGGER.info("Show lens");

        if (focusedElement != null && focusedElement.getType() == ElementType.CONTAINER) {
            getGraphController().setState(new LensState(getGraphController(), (GroupingElement) focusedElement));
            unfocus();
        }
    }


}
