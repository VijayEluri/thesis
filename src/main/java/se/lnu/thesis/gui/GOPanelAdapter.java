package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.GOController;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 17:31:14
 */
public class GOPanelAdapter extends JoglPanelAdapter {

    public static final Logger LOGGER = Logger.getLogger(GOPanelAdapter.class);

    public GOPanelAdapter(GraphController graphController) {
        setGraphController(graphController);
    }

    public GOPanelAdapter(GraphController graphController, JFrame frame) {
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

        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            LOGGER.debug("RIGHT MOUSE BUTTON");
            graphController.rightMouseButtonClicked(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        LOGGER.debug("MOUSE EXITED");
        
        graphController.mouseExited();

        getFrame().repaint();
    }
}
