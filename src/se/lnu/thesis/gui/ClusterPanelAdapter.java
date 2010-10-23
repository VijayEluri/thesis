package se.lnu.thesis.gui;

import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.10.2010
 * Time: 16:17:10
 */
public class ClusterPanelAdapter extends JoglPanelAdapter {


    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        LOGGER.debug("Mouse moves [" + mouseEvent.getX() + "," + mouseEvent.getY() + "]");
        graphController.move(mouseEvent.getPoint());

        getFrame().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        LOGGER.info("Mouse clicked..");

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            LOGGER.info("LEFT MOUSE BUTTON");
            graphController.click(mouseEvent.getPoint());
        }

        getFrame().repaint();
    }
}
