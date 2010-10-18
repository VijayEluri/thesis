package se.lnu.thesis.paint;

import com.sun.opengl.util.BufferUtil;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.paint.element.Container;
import se.lnu.thesis.paint.element.Element;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.nio.IntBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.08.2010
 * Time: 0:05:35
 */
public abstract class GraphVisualizer implements Drawable, Observer {

    public static final Logger LOGGER = Logger.getLogger(GraphVisualizer.class);

    public static final int BUFSIZE = 512;

    public static final double CURSOR_X_SIZE = 3.0;
    public static final double CURSOR_Y_SIZE = 3.0;

    protected Color background = Color.BLACK;

    protected State vertexState = State.NORMAL;

    protected Element selectedElement;

    protected Point cursor;

    protected State graphState = State.NORMAL;

    protected Graph subGraph;
    protected MyGraph graph;

    protected Container root;

    protected GL gl;
    protected GLU glu;

    public abstract void init();

    public abstract void draw(GLAutoDrawable drawable);/* {
        gl = drawable.getGL();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_DEPTH_TEST);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1.0f); // background color

        if (vertexState == State.SELECTING) {
            selectElement(drawable);
        }

        root.drawContent(drawable);


        drawable.swapBuffers();
    }*/

    protected void selectElement(GLAutoDrawable drawable) {
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

        root.drawContent(drawable);

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

    protected void select(Element element) {
        if (element != null) {
            this.selectedElement = element;
            this.selectedElement.setSelected(true);
            vertexState = State.SELECTED;

            LOGGER.info("Selected vertex " + element.getObject() + " [" + graph.getLabel(element.getObject()) + "]");
        }
    }

    protected void unselect() {
        if (selectedElement != null) {
            selectedElement.setSelected(false);
        }

        selectedElement = null;
        vertexState = State.NORMAL;
    }

    public void click(Point point) {
        this.cursor = point;
        vertexState = State.SELECTING;
    }

    public void setSubGraph(Graph subGraph) {
        if (subGraph != null) {
            setSubGraph(null);
            unselect();

            this.subGraph = subGraph;
            graphState = State.SUBGRAPH;


            if (root != null) {
                root.setHighlighted(subGraph.getVertices());
            }

        } else {
            this.subGraph = null;
            graphState = State.NORMAL;

            if (root != null) {
                root.resetHighlighting();
            }
        }
    }

    public void setGraph(MyGraph graph) {
        this.graph = graph;
    }

    public GLU getGlu() {
        if (glu == null) {
            glu = new GLU();
        }

        return glu;
    }

    public void move(Point point) {
        this.cursor = point;
        vertexState = State.MOVE;
    }
}
