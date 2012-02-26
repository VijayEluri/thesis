package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.controller.GraphController;

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

    protected boolean isLeftMouseButtonPressed = false;

    public JoglPanelAdapter() {

    }

    public JoglPanelAdapter(GraphController graphController) {
        setGraphController(graphController);
    }

    public JoglPanelAdapter(GraphController graphController, JFrame frame) {
        setGraphController(graphController);
        setFrame(frame);
    }

    public void init(GLAutoDrawable drawable) {
        LOGGER.debug("INIT");

    }

    public void dispose(GLAutoDrawable drawable) {
        LOGGER.debug("DISPOSE");
    }

    public void display(GLAutoDrawable drawable) {
        LOGGER.debug("DISPLAY");

        if (getGraphController() != null) {
            getGraphController().draw(drawable);
        }

        getFrame().repaint();
    }


    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        LOGGER.debug("RESHAPE");
        getFrame().repaint();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChange) {
        LOGGER.debug("DISPLAY CHANGED");
    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE CLICKED..");

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.debug("LEFT MOUSE BUTTON");
            graphController.leftMouseButtonClicked(mouseEvent.getPoint());
        }

        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            LOGGER.debug("RIGHT MOUSE BUTTON");
            graphController.rightMouseButtonClicked(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }

    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE PRESSED..");

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.debug("LEFT MOUSE BUTTON PRESSED");
            graphController.leftMouseButtonPressed(mouseEvent.getPoint());
            isLeftMouseButtonPressed = true;
        }

        getFrame().repaint();
    }

    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE RELEASED..");

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.debug("LEFT MOUSE BUTTON RELEASED");
            graphController.leftMouseButtonReleased(mouseEvent.getPoint());
            isLeftMouseButtonPressed = false;
        }

        getFrame().repaint();
    }

    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE ENTERED");
        getFrame().repaint();
    }

    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE EXITED");
        graphController.mouseExited();
        getFrame().repaint();
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE MOVED [" + mouseEvent.getX() + "," + mouseEvent.getY() + "]");
        graphController.mouseMove(mouseEvent.getPoint());
        getFrame().repaint();
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE DRAGGED [" + mouseEvent.getX() + "," + mouseEvent.getY() + "]");

        if (isLeftMouseButtonPressed) {
            LOGGER.debug("LEFT MOUSE BUTTON DRAGGED");
            graphController.leftMouseButtonDragged(mouseEvent.getPoint());
        }

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
