package se.lnu.thesis.gui;

import org.apache.log4j.Logger;

import java.awt.event.MouseEvent;

import se.lnu.thesis.paint.GraphController;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.10.2010
 * Time: 16:17:10
 */
public class ClusterPanelAdapter extends JoglPanelAdapter {

    public static final Logger LOGGER = Logger.getLogger(ClusterPanelAdapter.class);

    protected boolean isLeftMouseButtonPressed = false;

    public ClusterPanelAdapter(GraphController graphController, JFrame frame) {
        super(graphController, frame);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE MOVED [" + mouseEvent.getX() + "," + mouseEvent.getY() + "]");

        graphController.mouseMove(mouseEvent.getPoint());

        getFrame().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE CLICKED..");

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.debug("LEFT MOUSE BUTTON");
            graphController.leftMouseButtonClicked(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE PRESSED..");

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.debug("LEFT MOUSE BUTTON PRESSED");
            graphController.leftMouseButtonPressed(mouseEvent.getPoint());
            isLeftMouseButtonPressed = true;
        }

        getFrame().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE RELEASED..");

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.debug("LEFT MOUSE BUTTON RELEASED");
            graphController.leftMouseButtonReleased(mouseEvent.getPoint());
            isLeftMouseButtonPressed = false;
        }

        getFrame().repaint();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE DRAGGED [" + mouseEvent.getX() + "," + mouseEvent.getY() + "]");

        if (isLeftMouseButtonPressed) {
            LOGGER.debug("LEFT MOUSE BUTTON DRAGGED");
            graphController.leftMouseButtonDragged(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }
}
