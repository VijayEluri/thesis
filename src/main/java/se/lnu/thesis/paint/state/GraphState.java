package se.lnu.thesis.paint.state;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.paint.controller.GraphController;
import se.lnu.thesis.utils.MyColor;

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

    private GLU glu;

    private GraphController graphController;

    private Point cursor;

    public final void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_BLEND);

        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

        gl.glClearDepth(1.0);                     // Enables Clearing Of The Depth Buffer
        gl.glEnable(GL.GL_DEPTH_TEST);            // Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);             // The Type Of Depth Test To Do

        // Really Nice Perspective Calculations
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);


        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(graphController.getBackground().getRed(),
                graphController.getBackground().getGreen(),
                graphController.getBackground().getBlue(),
                1.0f); // background color

        drawCurrentState(drawable);

        drawable.swapBuffers();
    }

    protected abstract void drawCurrentState(GLAutoDrawable drawable);

    public void leftMouseButtonClicked(Point cursor) {
        setCursor(cursor);
    }

    public void rightMouseButtonClicked(Point cursor) {
        setCursor(cursor);
    }

    public void mouseMove(Point cursor) {
        setCursor(cursor);
    }

    public void leftMouseButtonDragged(Point cursor) {
        setCursor(cursor);
    }

    public void leftMouseButtonPressed(Point cursor) {
        setCursor(cursor);
    }

    public void leftMouseButtonReleased(Point cursor) {

    }

    public void mouseExited() {

    }

    public void setSubGraph(Graph subGraph) {
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
