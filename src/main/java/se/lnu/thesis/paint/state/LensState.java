package se.lnu.thesis.paint.state;

import com.sun.opengl.util.BufferUtil;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.paint.controller.GraphController;
import se.lnu.thesis.paint.lens.Lens;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;
import java.nio.IntBuffer;

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

        lens = getGraphController().getScene().getLens();
        lens.setGraph(controller.getGraph());

        select(element);
    }

    @Override
    protected void drawCurrentState(GLAutoDrawable drawable) {
        setGl(drawable);

        if (getCursor() != null && !moveLens) {
            focusing(drawable, selectedElement);
        }

        if (moveLens) {
            lens.move(gl2(), glu());
        }

        getContainer().draw(drawable);

        lens.draw(drawable);
    }

    @Override
    protected void focusing(GLAutoDrawable drawable, Container container) {
        setGl(drawable);

        IntBuffer selectBuffer = BufferUtil.newIntBuffer(BUFSIZE);

        int viewport[] = new int[4];

        gl2().glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

        gl2().glSelectBuffer(BUFSIZE, selectBuffer);
        gl2().glRenderMode(GL2.GL_SELECT);

        gl2().glInitNames();

        gl2().glMatrixMode(GL2.GL_PROJECTION);
        gl2().glPushMatrix();
        gl2().glLoadIdentity();

        glu().gluPickMatrix(getCursor().getX(), (viewport[3] - getCursor().getY()), CURSOR_X_SIZE, CURSOR_Y_SIZE, viewport, 0);

        lens.draw(drawable);


        gl2().glMatrixMode(GL2.GL_PROJECTION);
        gl2().glPopMatrix();
        gl2().glFlush();


        int hits = gl2().glRenderMode(GL2.GL_RENDER);

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

    public void select(GroupingElement element) {
        String label = getGraphController().getGraph().getLabel(element.getObject());

        LOGGER.info("Selected vertex " + element.getObject() + " [" + label + "]");

        getGraphController().getScene().getMainWindow().setClusterStatusBarText("Cluster: selected vertex " + label);

        this.selectedElement = element;
        this.selectedElement.setSelected(true);

        lens.setRoot(selectedElement);

        Graph subGraph = getGraphController().getSubGraph();
        if (subGraph != null) {
            selectedElement.setHighlighted(subGraph.getVertices());
        }
    }

    public void unselect() {
        if (selectedElement != null) {
            selectedElement.setSelected(false);
        }

        selectedElement = null;

        getGraphController().getScene().getMainWindow().setClusterStatusBarText("");
    }

    @Override
    public void leftMouseButtonClicked(Point point) {
        LOGGER.info("Hide lens");

        unfocus();
        unselect();

        getGraphController().setNormalState();
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
