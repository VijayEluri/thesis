package se.lnu.thesis.paint.state;

import com.sun.opengl.util.BufferUtil;
import org.apache.log4j.Logger;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.element.Level;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;
import java.nio.IntBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 16:10:16
 */
public class NormalGOState extends GraphState {

    public static final Logger LOGGER = Logger.getLogger(NormalGOState.class);

    protected Level current;

    protected NormalGOState() {

    }

    public NormalGOState(GraphController controller) {
        setGraphController(controller);
    }


    protected void drawCurrentState(GLAutoDrawable drawable) {
        if (getGraphController().getRoot() != null) {

            if (cursor != null) {
                focusLevel(drawable);
            }

            getGraphController().getRoot().drawContent(drawable);
        }
    }

    protected void focusLevel(GLAutoDrawable drawable) {
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
        int id = selectBuf[3];

        if (count > 0) { // something?
            unfocusLevel();
            focusLevel(id);
        } else {
            unfocusLevel();
        }

        cursor = null;
    }

    protected void unfocusLevel() {
        if (current != null) {
            current.setFocused(false);
        }

        current = null;
        cursor = null;
    }

    protected void focusLevel(int id) {
        Level level = (Level) getGraphController().getRoot().getElementById(id);

        if (level != null) {
            LOGGER.debug("Focused level " + level.getObject());

            current = level;
            current.setFocused(true);
        }
    }

    /**
     * Left mouse button been clicked
     *
     * @param point Cursor positon
     */
    public void leftMouseButtonClicked(Point point) {
        if (current != null) {
            LOGGER.info("Zoom in. Current level " + current);
            getGraphController().setState(new ZoomGOState(getGraphController(), current));
            unfocusLevel();
        }
    }

    /**
     * Mouse cursor been moved to position
     *
     * @param point Mouse cursor current position
     */
    public void mouseMove(Point point) {
        setCursor(point);
    }
}
