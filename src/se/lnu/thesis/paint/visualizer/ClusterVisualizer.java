package se.lnu.thesis.paint.visualizer;


import com.sun.opengl.util.BufferUtil;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.AbstractGraphElement;
import se.lnu.thesis.element.GraphElementType;
import se.lnu.thesis.element.GroupElement;
import se.lnu.thesis.layout.RectangularSpiralLayout;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.nio.IntBuffer;


public class ClusterVisualizer {

    public static final Logger LOGGER = Logger.getLogger(ClusterVisualizer.class);

    public static final int BUFSIZE = 512;

    public static final double CURSOR_X_SIZE = 2.0;
    public static final double CURSOR_Y_SIZE = 2.0;

    protected Color background = Color.BLACK;

    private State vertexState = State.NORMAL;
    private AbstractGraphElement selectedElement;

    private Point cursor;

    private State graphState = State.NORMAL;
    private Graph subGraph;

    private MyGraph graph;
    private GroupElement root;

    private GLU glu = new GLU();

    private Lens lens;

    public void init() {
        root = new GroupElement();

        RectangularSpiralLayout layout = new RectangularSpiralLayout(graph, root);
        layout.compute();

        lens = new Lens();
        lens.setGraph(graph);
    }

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1.0f); // background color

        if (vertexState == State.SELECTING) {
            selectElement(drawable);
        }

        drawElements(drawable, root);

        if (vertexState == State.SELECTED && selectedElement.getType() == GraphElementType.GROUP) {
            lens.draw(drawable);
        }

        drawable.swapBuffers();
    }

    // TODO move this to GroupElemnt class
    private void drawElements(GLAutoDrawable drawable, GroupElement groupElement) {
        for (AbstractGraphElement element : groupElement.getElements()) {
            element.draw(drawable);
        }
    }

    private void selectElement(GLAutoDrawable drawable) {
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

        glu.gluPickMatrix((double) cursor.x, (double) (viewport[3] - cursor.y), CURSOR_X_SIZE, CURSOR_Y_SIZE, viewport, 0);

        drawElements(drawable, root);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glFlush();


        int hits = gl.glRenderMode(GL.GL_RENDER); // mouse hits
        selectBuffer.get(selectBuf);

        int count = selectBuf[0];

        LOGGER.info("Picked objects count: " + count);

        if (count > 0) { // some figures?
            unselect();
            select(root.getElementById(selectBuf[3]));
        } else {
            unselect();
        }

        cursor = null;
    }

    private void select(AbstractGraphElement element) {
        if (element != null) {
            this.selectedElement = element;
            this.selectedElement.setSelected(true);
            vertexState = State.SELECTED;

            LOGGER.info("Selected vertex '" + element.getObject() + "'");

            if (selectedElement.getType() == GraphElementType.GROUP) {
                lens.setGroupElement((GroupElement) selectedElement);
            }
        }
    }

    private void unselect() {
        if (selectedElement != null) {
            selectedElement.setSelected(false);
        }

        selectedElement = null;
        vertexState = State.NORMAL;
    }

    public void setCursor(Point point) {
        if (point != null) {
            this.cursor = point;
            vertexState = State.SELECTING;
        }
    }

    public void setSubGraph(Graph subGraph) {
        if (subGraph != null) {
            setSubGraph(null);
            unselect();

            this.subGraph = subGraph;
            graphState = State.SUBGRAPH;


            for (Object o : subGraph.getVertices()) {
                for (AbstractGraphElement element : root.getElements()) {
                    if (element.has(o)) {
                        element.setSubgraph(true);
                    }
                }
            }

        } else {
            this.subGraph = null;
            graphState = State.NORMAL;

            for (AbstractGraphElement element : root.getElements()) {
                element.setSubgraph(false);
            }
        }
    }

    public void setGraph(MyGraph graph) {
        this.graph = graph;
    }
}
