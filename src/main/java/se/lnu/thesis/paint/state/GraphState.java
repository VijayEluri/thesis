package se.lnu.thesis.paint.state;

import com.sun.opengl.util.gl2.GLUT;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.paint.controller.GraphController;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.gl2.GLUgl2;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 16:10:37
 */
public abstract class GraphState implements Drawable {

    private GL2 gl2;
    private GLU glu;
    private GLUT glut;

    private GraphController graphController;

    private Point cursor;

    public final void draw(GLAutoDrawable drawable) {
        setGl2((GL2) drawable.getGL());

        gl2().glMatrixMode(GL2.GL_PROJECTION);
        gl2().glLoadIdentity();

        gl2().glEnable(GL.GL_LINE_SMOOTH);
        gl2().glEnable(GL.GL_BLEND);

        gl2().glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl2().glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);

        gl2().glClearDepth(1.0);                     // Enables Clearing Of The Depth Buffer
        gl2().glEnable(GL.GL_DEPTH_TEST);            // Enables Depth Testing
        gl2().glDepthFunc(GL.GL_LEQUAL);             // The Type Of Depth Test To Do

        // Really Nice Perspective Calculations
        gl2().glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);


        gl2().glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl2().glClearColor(graphController.getBackground().getRed(),
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

    /**
     * Mouse has left view port area then set current cursor position to <code>null</code>.
     */
    public void mouseExited() {
        setCursor(null);
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

    public void setGl2(GL2 gl2) {
        this.gl2 = gl2;
    }

    public void setGl(GLAutoDrawable drawable) {
        setGl2((GL2) drawable.getGL());
    }

    public GL2 gl2() {
        return gl2;
    }

    public GLU glu() {
        if (glu == null) {
            glu = new GLUgl2();
        }

        return glu;
    }

    public GLUT glut() {
        if (glut == null) {
            glut = new GLUT();
        }

        return glut;
    }
}
