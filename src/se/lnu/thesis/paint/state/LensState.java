package se.lnu.thesis.paint.state;

import com.sun.opengl.util.BufferUtil;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.Scene;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.Lens;
import se.lnu.thesis.paint.element.Container;
import se.lnu.thesis.paint.element.GroupingElement;

import javax.media.opengl.GL;
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

    protected LensState() {

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

        if (getCursor() != null) {
            focusing(drawable, selectedElement);
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

        getGlu().gluPickMatrix((double) getCursor().getX(), (double) (viewport[3] - getCursor().getY()), CURSOR_X_SIZE, CURSOR_Y_SIZE, viewport, 0);

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
            } else {
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
}
