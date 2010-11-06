package se.lnu.thesis.paint;

import com.sun.opengl.util.BufferUtil;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.Scene;
import se.lnu.thesis.paint.element.Element;
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
        if (cursor != null) {
            focusElement(drawable);
        }

        getGraphController().getRoot().drawContent(drawable);

        lens.draw(drawable);
    }

    @Override
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

        selectedElement.drawContent(drawable);

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

    @Override
    protected void focus(int id) {
        Element element = selectedElement.getElementById(id);

        if (element != null) {
            String label = getGraphController().getGraph().getLabel(element.getObject());

            LOGGER.info("Focused vertex " + element.getObject() + " [" + label + "]");

            Scene.getInstance().getMainWindow().setStatusBarText("Focused vertex " + label);

            this.focusedElement = element;
            this.focusedElement.setFocused(true);
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
