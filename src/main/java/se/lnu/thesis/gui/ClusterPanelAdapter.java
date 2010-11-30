package se.lnu.thesis.gui;

import org.apache.log4j.Logger;

import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.10.2010
 * Time: 16:17:10
 */
public class ClusterPanelAdapter extends JoglPanelAdapter {

    public static final Logger LOGGER = Logger.getLogger(ClusterPanelAdapter.class);

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        LOGGER.debug("Mouse moves [" + mouseEvent.getX() + "," + mouseEvent.getY() + "]");
        graphController.mouseMove(mouseEvent.getPoint());

        getFrame().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        LOGGER.info("Mouse clicked..");

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.info("LEFT MOUSE BUTTON");
            graphController.leftMouseButtonClicked(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.info("LEFT MOUSE BUTTON PRESSED");
            graphController.leftMouseButtonPressed(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.info("LEFT MOUSE BUTTON RELEASED");
            graphController.leftMouseButtonReleased(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.info("LEFT MOUSE BUTTON DRAGGED");
            graphController.leftMouseButtonDragged(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }
}
