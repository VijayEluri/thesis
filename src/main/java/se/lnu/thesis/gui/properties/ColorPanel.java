package se.lnu.thesis.gui.properties;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.12.2010
 * Time: 23:50:37
 */
public class ColorPanel extends JPanel {

    private String id;

    protected ColorPanel() {

    }

    public ColorPanel(String id) {
        setId(id);

        Dimension d = new Dimension(25, 25); // color panel size

        setMinimumSize(d);
        setMaximumSize(d);
        setPreferredSize(d);
        setSize(d);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
