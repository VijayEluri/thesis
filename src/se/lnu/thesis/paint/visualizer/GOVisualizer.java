package se.lnu.thesis.paint.visualizer;

import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.GroupElement;
import se.lnu.thesis.layout.LevelsLayout;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 23:43:07
 */
public class GOVisualizer extends GraphVisualizer {

    protected Color background = Color.BLACK;

    private MyGraph graph;
    private GroupElement root;

    public void init() {
        LOGGER.info("Initializing..");

        root = new GroupElement();


        LevelsLayout layout = new LevelsLayout(graph, root);
        layout.compute();

        LOGGER.info("Done.");
    }

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1.0f); // background color

        root.drawElements(drawable);

        drawable.swapBuffers();
    }

    public void setGraph(MyGraph graph) {
        this.graph = graph;
    }
}
