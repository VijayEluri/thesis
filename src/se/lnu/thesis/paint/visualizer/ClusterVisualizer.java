package se.lnu.thesis.paint.visualizer;


import com.sun.opengl.util.BufferUtil;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.layout.RectangularSpiralLayout;
import se.lnu.thesis.paint.element.AbstractGraphElement;
import se.lnu.thesis.paint.element.GraphElementType;
import se.lnu.thesis.paint.element.GroupElement;
import se.lnu.thesis.paint.visualizer.element.ElementVisualizerFactory;
import se.lnu.thesis.paint.visualizer.graph.State;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
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
    private static final double LENS_RADIUS = 0.3;


    public void init() {
        root = new GroupElement();

        RectangularSpiralLayout layout = new RectangularSpiralLayout(graph, root);
        layout.compute();

//        computeGroupElementsPositions();

        normalizeGroupSize();
    }

    private void computeGroupElementsPositions() {
        PolarDendrogramLayout layout = new PolarDendrogramLayout(graph, null);
        layout.setRadius(LENS_RADIUS);

        for (AbstractGraphElement element : root.getElements()) {
            if (element.getType() == GraphElementType.GROUP) {
                GroupElement groupElement = (GroupElement) element;

                layout.setRoot(groupElement);
                layout.compute();
            }
        }

    }

    private void normalizeGroupSize() {
        int maxGroupSize = 1;
        int currentGroupSize = 1;

/*
        for (AbstractGraphElement element : root.getElements()) {
            if (element.getType() == GraphElementType.GROUP) {
                GroupElement groupElement = (GroupElement) element;

                currentGroupSize = groupElement.getSize();
                if (currentGroupSize > maxGroupSize) {
                    maxGroupSize = currentGroupSize;
                }

            }
        }
*/

        for (AbstractGraphElement element : root.getElements()) {
            if (element.getType() == GraphElementType.GROUP) {
                GroupElement groupElement = (GroupElement) element;

                groupElement.setElementVisualizer(ElementVisualizerFactory.getInstance().getRectVisualizer());

            }
        }
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

        if (vertexState == State.SELECTED) {
            drawLens(drawable);
        }

        drawable.swapBuffers();
    }

    private void drawElements(GLAutoDrawable drawable, GroupElement groupElement) {
        for (AbstractGraphElement element : groupElement.getElements()) {
            element.draw(drawable);
        }
    }

    protected void drawLens(GLAutoDrawable drawable) {
        if (selectedElement.getType() == GraphElementType.GROUP) {
            GL gl = drawable.getGL();

            gl.glColor3d(1, 0, 0);
            GLUquadric glUquadric = glu.gluNewQuadric();

            glu.gluDisk(glUquadric, 0, LENS_RADIUS, 50, 50);

            drawElements(drawable, (GroupElement) selectedElement);
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
                LOGGER.info("Computing lens..");

                PolarDendrogramLayout layout = new PolarDendrogramLayout(graph, (GroupElement) selectedElement);
                layout.setRadius(LENS_RADIUS);
                layout.compute();

                LOGGER.info("Done.");
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
