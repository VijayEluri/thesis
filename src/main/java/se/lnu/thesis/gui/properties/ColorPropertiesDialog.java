package se.lnu.thesis.gui.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.lnu.thesis.properties.ColorSchema;
import se.lnu.thesis.properties.PropertiesHolder;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 06.12.2010
 * Time: 19:36:20
 */
@org.springframework.stereotype.Component
public class ColorPropertiesDialog extends JFrame implements WindowListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(ColorPropertiesDialog.class);

    protected JPanel background;
    protected JPanel selection;
    protected JPanel focusing;
    protected JPanel subgraph;
    protected JPanel verticesTooltips;

    protected JPanel goLevelNumbers;
    protected JPanel goLevelLines;
    protected JPanel goLeaves;
    protected JPanel goNodes;
    protected JPanel goEdges;

    protected JPanel clusterLeaves;
    protected JPanel clusterNodes;
    protected JPanel clusterEdges;

    protected JPanel lensColor;

    @Autowired
    ColorPanelMouseAdapter panelMouseAdapter;

    @PostConstruct
    public void init() {
        createGUI();
        initColorPanels();
    }

    protected void createGUI() {
        setLayout(new GridLayout(14, 1));

        background = createColorPanel(ColorSchema.COLOR_BACKGROUND);
        add(colorPropertyPanel("Background ", background));

        selection = createColorPanel(ColorSchema.COLOR_SELECTION);
        add(colorPropertyPanel("Selection ", selection));

        focusing = createColorPanel(ColorSchema.COLOR_FOCUSING);
        add(colorPropertyPanel("Focusing ", focusing));

        subgraph = createColorPanel(ColorSchema.COLOR_SUBGRAPH);
        add(colorPropertyPanel("Subgraph ", subgraph));

        verticesTooltips = createColorPanel(ColorSchema.COLOR_VERTICES_TOOLTIPS);
        add(colorPropertyPanel("Tooltips ", verticesTooltips));

        goLevelNumbers = createColorPanel(ColorSchema.COLOR_GO_LEVEL_NUMBERS);
        add(colorPropertyPanel("GO level numbers ", goLevelNumbers));

        goLevelLines = createColorPanel(ColorSchema.COLOR_GO_LEVEL_LINES);
        add(colorPropertyPanel("GO level lines ", goLevelLines));

        goLeaves = createColorPanel(ColorSchema.COLOR_GO_LEAVES);
        add(colorPropertyPanel("GO leaves ", goLeaves));

        goNodes = createColorPanel(ColorSchema.COLOR_GO_NODES);
        add(colorPropertyPanel("GO nodes ", goNodes));

/*        goEdges = createColorPanel(ColorSchema.COLOR_GO_EDGES);
        add(colorPropertyPanel("GO edges ", goEdges));*/


        clusterLeaves = createColorPanel(ColorSchema.COLOR_CLUSTER_LEAVES);
        add(colorPropertyPanel("Cluster leaves ", clusterLeaves));

        clusterNodes = createColorPanel(ColorSchema.COLOR_CLUSTER_NODES);
        add(colorPropertyPanel("Cluster nodes ", clusterNodes));

        clusterEdges = createColorPanel(ColorSchema.COLOR_CLUSTER_EDGES);
        add(colorPropertyPanel("Cluster edges ", clusterEdges));

        lensColor = createColorPanel(ColorSchema.COLOR_LENS);
        add(colorPropertyPanel("Lens color ", lensColor));

        add(createCloseButton());

        pack();
        setResizable(false);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(this);
    }

    private void initColorPanels() {
        ColorSchema colorSchema = PropertiesHolder.getInstance().getColorSchema();

        background.setBackground(colorSchema.getBackground().asAWTColor());
        selection.setBackground(colorSchema.getSelection().asAWTColor());
        focusing.setBackground(colorSchema.getFocusing().asAWTColor());
        subgraph.setBackground(colorSchema.getSubgraph().asAWTColor());
        verticesTooltips.setBackground(colorSchema.getVerticesTooltips().asAWTColor());

        goLevelNumbers.setBackground(colorSchema.getGoLevelNumbers().asAWTColor());
        goLevelLines.setBackground(colorSchema.getGoLevelLines().asAWTColor());
        goLeaves.setBackground(colorSchema.getGoLeaves().asAWTColor());
        goNodes.setBackground(colorSchema.getGoNodes().asAWTColor());

        clusterLeaves.setBackground(colorSchema.getClusterLeaves().asAWTColor());
        clusterNodes.setBackground(colorSchema.getClusterNodes().asAWTColor());
        clusterEdges.setBackground(colorSchema.getClusterEdges().asAWTColor());

        lensColor.setBackground(colorSchema.getLens().asAWTColor());
    }

    private JButton createCloseButton() {
        JButton result = new JButton();

        result.setText("Close");
        result.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ColorPropertiesDialog.this.closeIt();
            }
        });

        return result;
    }

    protected JPanel colorPropertyPanel(String text, JPanel colorPanel) {
        JPanel result = new JPanel(new BorderLayout());
        result.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));

        result.add(new JLabel(text), BorderLayout.WEST);
        result.add(colorPanel, BorderLayout.EAST);

        return result;
    }

    protected JPanel createColorPanel(String id) {
        JPanel result = new ColorPanel(id);

        result.addMouseListener(panelMouseAdapter);

        return result;
    }

    public void showIt() {
        setVisible(true);
    }

    public void closeIt() {
        setVisible(false);
    }

    public void windowOpened(WindowEvent windowEvent) {
        LOGGER.debug("WINDOW OPENED");
    }

    public void windowClosing(WindowEvent windowEvent) {
        LOGGER.debug("WINDOW CLOSING");
    }

    public void windowClosed(WindowEvent windowEvent) {
        LOGGER.debug("WINDOW CLOSED");
    }

    public void windowIconified(WindowEvent windowEvent) {
        LOGGER.debug("WINDOW ICONIFIED");
    }

    public void windowDeiconified(WindowEvent windowEvent) {
        LOGGER.debug("WINDOW DEICONIFIED");
    }

    public void windowActivated(WindowEvent windowEvent) {
        LOGGER.debug("WINDOW ACTIVATED");
        initColorPanels();
    }

    public void windowDeactivated(WindowEvent windowEvent) {
        LOGGER.debug("WINDOW DEACTIVATED");
        PropertiesHolder.getInstance().save();
    }

    /**
     * FOR TESTING ONLY
     *
     * @param args
     */
    public static void main(String[] args) {
        ColorPropertiesDialog dialog = new ColorPropertiesDialog();
        dialog.init();
        dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog.showIt();
    }

}
