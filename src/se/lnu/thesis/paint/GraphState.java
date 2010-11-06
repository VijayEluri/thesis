package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.utils.DrawingUtils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 16:10:37
 */
public abstract class GraphState implements Drawable {

    public static final int BUFSIZE = 512;

    public static final double CURSOR_X_SIZE = 2.0;
    public static final double CURSOR_Y_SIZE = 2.0;

    protected GLU glu;

    private GraphController graphController;

    protected Point cursor;

    public final void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);


        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(
                DrawingUtils.colorAsFloat(graphController.getBackground().getRed()),
                DrawingUtils.colorAsFloat(graphController.getBackground().getGreen()),
                DrawingUtils.colorAsFloat(graphController.getBackground().getBlue()),
                1.0f); // background color

        drawCurrentState(drawable);

        drawable.swapBuffers();
    }

    protected abstract void drawCurrentState(GLAutoDrawable drawable);

    public void leftMouseButtonClicked(Point point) {

    }

    public void mouseMove(Point point) {
        setCursor(point);
    }

    public void setSubGraph(Graph subGraph) {
/*
        if (subGraph != null) {

            setSubGraph(null);

            this.subGraph = subGraph;

            if (root != null) {
                root.setHighlighted(subGraph.getVertices());
            }
        } else {
            this.subGraph = null;

            if (root != null) {
                root.resetHighlighting();
            }
        }
*/
    }

    public GraphController getGraphController() {
        return graphController;
    }

    public void setGraphController(GraphController graphController) {
        this.graphController = graphController;
    }

    public Point getCursor() {
        return cursor;
    }

    public void setCursor(Point cursor) {
        this.cursor = cursor;
    }

    public GLU getGlu() {
        if (glu == null) {
            glu = new GLU();
        }

        return glu;
    }

}
