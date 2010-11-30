package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.GraphController;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.05.2010
 * Time: 0:48:58
 * <p/>
 * JOGL window
 */
public class JoglPanelAdapter implements GLEventListener, MouseListener, MouseMotionListener {

    public static final Logger LOGGER = Logger.getLogger(JoglPanelAdapter.class);

    private JFrame frame;

    protected GraphController graphController;

    public JoglPanelAdapter() {

    }

    public JoglPanelAdapter(GraphController graphController) {
        setGraphController(graphController);
    }

    public void init(GLAutoDrawable drawable) {

    }

    public void display(GLAutoDrawable drawable) {
        if (getGraphController() != null) {
            getGraphController().draw(drawable);
        }

        getFrame().repaint();
    }


    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

/*
        int length, shift;
        if (width > height) {
            length = height;
            shift = (width - height) / 2;

            gl.glViewport(0, shift, length, length);
        } else {
            length = width;
            shift = (height - width) / 2;

            gl.glViewport(shift, 0, length, length);
        }
*/
        gl.glViewport(0, 0, width, height);

        getFrame().repaint();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChange) {
    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        getFrame().repaint();
    }

    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        getFrame().repaint();
    }

    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        getFrame().repaint();
    }

    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
        getFrame().repaint();
    }

    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
        getFrame().repaint();
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        getFrame().repaint();
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        getFrame().repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setGraphController(GraphController graphController) {
        this.graphController = graphController;
    }

    public GraphController getGraphController() {
        return graphController;
    }
}
