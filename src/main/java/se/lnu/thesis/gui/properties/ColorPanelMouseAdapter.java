package se.lnu.thesis.gui.properties;

import org.springframework.beans.factory.annotation.Autowired;
import se.lnu.thesis.Scene;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.properties.ColorSchema;
import se.lnu.thesis.properties.PropertiesHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.12.2010
 * Time: 23:32:36
 */
@org.springframework.stereotype.Component
public class ColorPanelMouseAdapter extends MouseAdapter {

    @Autowired
    private Scene scene;

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) { // LEFT MOUSE BUTTON
            ColorPanel colorPanel = (ColorPanel) mouseEvent.getSource();

            Color initialBackground = colorPanel.getBackground();
            Color color = JColorChooser.showDialog(null, null, initialBackground);

            if (color != null) {
                colorPanel.setBackground(color);

                if (colorPanel.getId() == ColorSchema.COLOR_BACKGROUND) {
                    scene.getGoController().getBackground().setColor(color);
                    scene.getClusterController().getBackground().setColor(color);

                    ElementVisualizerFactory.getInstance().getLevelVisualizer().getBackground().setColor(color);
                    ElementVisualizerFactory.getInstance().getLevelPreviewVisualizer().getBackground().setColor(color);

                } else {
                    PropertiesHolder.getInstance().getColorSchema().getColor(colorPanel.getId()).setColor(color);
                }
            }

            scene.getMainWindow().repaint();
        }
    }

}
