package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.GraphController;

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
}
