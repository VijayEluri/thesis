package se.lnu.thesis.paint;


import com.sun.opengl.util.BufferUtil;
import se.lnu.thesis.Scene;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.layout.RectangularSpiralLayout;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.element.*;
import se.lnu.thesis.utils.DrawingUtils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.nio.IntBuffer;


public class ClusterController extends GraphController implements Observer {

    private Lens lens;
    private Element focusedElement;

    public void init() {
        root = DimensionalContainer.init("Cluster");

        RectangularSpiralLayout layout = new RectangularSpiralLayout(graph, root);
        layout.compute();


        lens = new Lens();
        lens.setGraph(graph);
    }

    @Override
    protected void select(Element element) {
        if (element != null && element.getType() == ElementType.CONTAINER) {
            LOGGER.info("Selected vertex " + element.getObject() + " [" + graph.getLabel(element.getObject()) + "]");

            Scene.getInstance().getMainWindow().setStatusBarText("Selected vertex " + graph.getLabel(element.getObject()));

            this.selectedElement = element;
            this.selectedElement.setSelected(true);
            vertexState = State.SELECTED;

            GroupingElement groupingElement = (GroupingElement) selectedElement;
            lens.setRoot(groupingElement);

//            Scene.getInstance().getMainWindow()

            if (subGraph != null) {
                groupingElement.setHighlighted(subGraph.getVertices());
            }
        }
    }

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(
                DrawingUtils.colorAsFloat(background.getRed()),
                DrawingUtils.colorAsFloat(background.getGreen()),
                DrawingUtils.colorAsFloat(background.getBlue()),
                1.0f); // background color

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);


        if (root != null) {
            if (vertexState == State.MOVE) {
                focusElement(drawable);
            } else {
                if (vertexState == State.SELECTING) {
                    select(drawable);
                }
            }

            root.drawContent(drawable);

            if (selectedElement != null && selectedElement.getType() == ElementType.CONTAINER) {
                lens.draw(drawable);
            }
        }

        drawable.swapBuffers();
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


        if (selectedElement != null && selectedElement.getType() == ElementType.CONTAINER) {
            ((Container) selectedElement).drawContent(drawable);
        } else {
            root.drawContent(drawable);
        }

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glFlush();


        int hits = gl.glRenderMode(GL.GL_RENDER); // mouse hits
        selectBuffer.get(selectBuf);

        int count = selectBuf[0];

        //LOGGER.info("Picked objects count: " + count);

        if (count > 0) { // some figures?
            unfocus();

            if (selectedElement != null && selectedElement.getType() == ElementType.CONTAINER) {
                focus(((Container) selectedElement).getElementById(selectBuf[3]));
            } else {
                focus(root.getElementById(selectBuf[3]));
            }


        } else {
            unfocus();
        }

        cursor = null;
    }

    private void unfocus() {
        if (focusedElement != null) {
            focusedElement.setFocused(false);
        }

        focusedElement = null;
        vertexState = State.NORMAL;
    }

    private void focus(Element element) {
        if (element != null) {
            LOGGER.info("Focused vertex " + element.getObject() + " [" + graph.getLabel(element.getObject()) + "]");

            Scene.getInstance().getMainWindow().setStatusBarText("Focused vertex " + graph.getLabel(element.getObject()));

            this.focusedElement = element;
            this.focusedElement.setFocused(true);
            vertexState = State.NORMAL;
        }
    }

    public void notifyObserver(Subject subject, Object params) {
        Extractor extractor = (Extractor) params;

        this.setSubGraph(extractor.getClusterSubGraph());

        Scene.getInstance().getMainWindow().repaint();
    }

}
