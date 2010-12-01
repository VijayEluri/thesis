package se.lnu.thesis.paint.state;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.Scene;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.Lens;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GL;
import java.awt.*;
import java.nio.IntBuffer;

import com.sun.opengl.util.BufferUtil;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 18:46:11
 */
public class LensState extends NormalClusterState {

    private GroupingElement selectedElement;

    private Lens lens;

    private boolean lensInFocus = false;
    private boolean moveLens = false;

    public LensState() {

    }

    public LensState(GraphController controller, GroupingElement element) {
        super(controller);

        lens = new Lens();
        lens.setGraph(controller.getGraph());

        select(element);
    }

    @Override
    protected void drawCurrentState(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        if (getCursor() != null && !moveLens) {
            focusing(drawable, selectedElement);
        }

        if (moveLens) {
            lens.move(gl, getGlu());
        }

        getContainer().draw(drawable);

        lens.draw(drawable);
    }

    @Override
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

        lens.draw(drawable);


        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glFlush();


        int hits = gl.glRenderMode(GL.GL_RENDER);

        LOGGER.debug("There are " + hits + " ids found.");

        unfocus();

        if (hits > 0) { // founded something?
//            int id = selectBuffer.get(hits * 4 - 1); // get last id in the stack


            int buf[] = new int[BUFSIZE];
            selectBuffer.get(buf);
            int id = buf[hits * 4 - 1];

            if (id == Lens.ID) {
                LOGGER.debug("Lens circle is in focus..");

                lensInFocus = true;
            } else {
                lensInFocus = false;

                LOGGER.debug("Looking for id '" + id + "'");
                focus(id, container);
            }
        }

    }

    protected void select(GroupingElement element) {
        String label = getGraphController().getGraph().getLabel(element.getObject());

        LOGGER.info("Selected vertex " + element.getObject() + " [" + label + "]");

        Scene.getInstance().getMainWindow().setStatusBarText("Selected vertex " + label);

        this.selectedElement = element;
        this.selectedElement.setSelected(true);

        lens.setRoot(selectedElement);

        Graph subGraph = getGraphController().getSubGraph();
        if (subGraph != null) {
            selectedElement.setHighlighted(subGraph.getVertices());
        }
    }

    protected void unselect() {
        if (selectedElement != null) {
            selectedElement.setSelected(false);
        }

        selectedElement = null;
    }

    @Override
    public void leftMouseButtonClicked(Point point) {
        LOGGER.info("Hide lens");

        unfocus();
        unselect();

        getGraphController().setState(new NormalClusterState(getGraphController()));
    }

    @Override
    public void leftMouseButtonPressed(Point cursor) {
        if (lensInFocus) {
            lens.setStart(cursor);
            lens.setEnd(cursor);

            moveLens = true;
        }
    }

    @Override
    public void leftMouseButtonReleased(Point cursor) {
        moveLens = false;
        lensInFocus = false;
    }

    @Override
    public void leftMouseButtonDragged(Point cursor) {
        LOGGER.info("LEFT MOUSE BUTTON DRAGGED [" + cursor + "]");
        if (moveLens) {
            lens.setStart(lens.getEnd());
            lens.setEnd(cursor);
        }
    }

}
