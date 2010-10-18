package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.paint.GraphVisualizer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.MouseListener;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.05.2010
 * Time: 0:48:58
 * <p/>
 * JOGL window
 */
public abstract class JoglListener implements GLEventListener, MouseListener {


    public static final Logger LOGGER = Logger.getLogger(JoglListener.class);

    protected GraphVisualizer visualizer;

    public JoglListener() {

    }

    public JoglListener(GraphVisualizer visualizer) {
        setVisualizer(visualizer);
    }

    public void init(GLAutoDrawable drawable) {

    }

    public void display(GLAutoDrawable drawable) {
        if (getVisualizer() != null) {
            getVisualizer().draw(drawable);
        }
    }


    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int heigh) {
        GL gl = drawable.getGL();

        int length;
        int shift = 0;

        if (heigh < width) {
            length = heigh;
            shift = (width - heigh) / 2;
        } else {
            length = width;
        }

        gl.glViewport(shift, 0, length, length);

        Scene.getInstance().getMainWindow().repaint();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChange) {
    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
/*
        visualizer.setCursor(mouseEvent.getPoint());

        Scene.getInstance().getMainWindow().repaint();
*/
    }

    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        visualizer.setCursor(mouseEvent.getPoint());

        Scene.getInstance().getMainWindow().repaint();
    }

    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
    }

    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
    }

    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
    }

    public void setVisualizer(GraphVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public GraphVisualizer getVisualizer() {
        return visualizer;
    }
}
