package se.lnu.thesis.paint.state;

import com.sun.opengl.util.BufferUtil;
import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.paint.GOController;
import se.lnu.thesis.paint.GraphController;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 17:55:49
 * <p/>
 * <p/>
 * Gene Ontology zooming levels state
 */
public class ZoomGOState extends FocusableState {

    public static final Logger LOGGER = Logger.getLogger(ZoomGOState.class);

    private List<Level> levels;

    public ZoomGOState(GraphController controller, Level level) {
        setGraphController(controller);

        level.setFocused(false);

        levels = new LinkedList<Level>();
        int topLevelIndex = ((GOGraphContainer) controller.getRoot()).getZoomedLevels(level, levels);

        Scene.getInstance().getMainWindow().setScrollBarValue(topLevelIndex);
        Scene.getInstance().getMainWindow().showSrollBar();
    }

    @Override
    protected void drawCurrentState(GLAutoDrawable drawable) {
        if (getCursor() != null) {
            focusing(drawable);
        }

        drawTopLevel(drawable);

        drawMidLevel(drawable);

        drawBottomLevel(drawable);
    }

    private void drawTopLevel(GLAutoDrawable drawable) {
        Level level = levels.get(0);
        shiftAndDrawLevel(drawable, level, level.getDimension().getY());
    }

    private void drawMidLevel(GLAutoDrawable drawable) {
        Level level = levels.get(1);
        shiftAndDrawLevel(drawable, level, 0);
    }

    private void drawBottomLevel(GLAutoDrawable drawable) {
        Level level = levels.get(2);
        shiftAndDrawLevel(drawable, level, -level.getDimension().getY());
    }

    private void shiftAndDrawLevel(GLAutoDrawable drawable, Level level, double shiftY) {
        GL gl = drawable.getGL();

        gl.glPushMatrix();
        gl.glTranslated(0.0, shiftY, 0.0);
        level.drawContent(drawable);
        gl.glPopMatrix();
    }

    protected void focusing(GLAutoDrawable drawable) {
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


        drawTopLevel(drawable);
        drawMidLevel(drawable);
        drawBottomLevel(drawable);


        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glFlush();


        int hits = gl.glRenderMode(GL.GL_RENDER);

        LOGGER.debug("There are " + hits + " ids found.");

        if (hits > 0) { // founded something?
            int id = selectBuffer.get(hits * 4 - 1); // get last id in the stack

            LOGGER.debug("Looking for id '" + id + "'");

            unfocus();
            for (Level level : levels) {
                focus(id, level);
            }
        } else {
            unfocus();
        }

    }

    @Override
    public void leftMouseButtonClicked(Point cursor) {
        GOController goController = (GOController) getGraphController();

        Scene.getInstance().getMainWindow().setCursor(Cursor.WAIT_CURSOR);

        if (getCurrent() == null) { // no  focused element than skip selection
            goController.unselect();

            Scene.getInstance().getExtractor().extractSubGraphs(null, null, null);
        } else {                    // there is focused element then select it
            goController.select(getCurrent());

            Scene.getInstance().getExtractor().extractSubGraphs(Scene.getInstance().getGoGraph(), Scene.getInstance().getClusterGraph(), getCurrent().getObject());
        }

        Scene.getInstance().getGeneListDialog().notifyObservers();

        Scene.getInstance().getMainWindow().setCursor(Cursor.DEFAULT_CURSOR);
    }

    @Override
    public void rightMouseButtonClicked(Point cursor) {
        LOGGER.info("Zoom out to default view");

        Scene.getInstance().getMainWindow().hideScrollBar();
        getGraphController().setState(new NormalGOState(getGraphController()));
    }

    public void scrollBarValueChanged(int index) {
        ((GOGraphContainer) getGraphController().getRoot()).getZoomedLevels(index, levels);
    }
}
