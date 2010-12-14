package se.lnu.thesis.gui.properties;

import org.apache.log4j.Logger;
import se.lnu.thesis.properties.ColorSchema;
import se.lnu.thesis.properties.PropertiesHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 06.12.2010
 * Time: 19:36:20
 */
public class ColorPropertiesDialog extends JFrame implements WindowListener {

    public static final Logger LOGGER = Logger.getLogger(ColorPropertiesDialog.class);

    protected JPanel background;
    protected JPanel selection;
    protected JPanel focusing;
    protected JPanel subgraph;

    protected JPanel goLevelNumbers;
    protected JPanel goLevelLines;
    protected JPanel goLeaves;
    protected JPanel goNodes;

    protected JPanel clusterLeaves;
    protected JPanel clusterNodes;
    protected JPanel clusterEdges;

    protected JPanel lensColor;

    ColorPanelMouseAdapter panelMouseAdapter;

    public ColorPropertiesDialog() {
        createGUI();
        initColorPanels();
    }

    private void initColorPanels() {
        ColorSchema colorSchema = PropertiesHolder.getInstance().getColorSchema();

        background.setBackground(colorSchema.getBackground().asAWTColor());
        selection.setBackground(colorSchema.getSelection().asAWTColor());
        focusing.setBackground(colorSchema.getFocusing().asAWTColor());
        subgraph.setBackground(colorSchema.getSubgraph().asAWTColor());

        goLevelNumbers.setBackground(colorSchema.getGoLevelNumbers().asAWTColor());
        goLevelLines.setBackground(colorSchema.getGoLevelLines().asAWTColor());
        goLeaves.setBackground(colorSchema.getGoLeaves().asAWTColor());
        goNodes.setBackground(colorSchema.getGoNodes().asAWTColor());

        clusterLeaves.setBackground(colorSchema.getClusterLeaves().asAWTColor());
        clusterNodes.setBackground(colorSchema.getClusterNodes().asAWTColor());
        clusterEdges.setBackground(colorSchema.getClusterEdges().asAWTColor());

        lensColor.setBackground(colorSchema.getLens().asAWTColor());
    }

    protected void createGUI() {
        setLayout(new GridLayout(13, 1));

        panelMouseAdapter = new ColorPanelMouseAdapter();

        background = createColorPanel(ColorSchema.COLOR_BACKGROUND);
        add(colorPropertyPanel("Background ", background));

        selection = createColorPanel(ColorSchema.COLOR_SELECTION);
        add(colorPropertyPanel("Selection ", selection));

        focusing = createColorPanel(ColorSchema.COLOR_FOCUSING);
        add(colorPropertyPanel("Focusing ", focusing));

        subgraph = createColorPanel(ColorSchema.COLOR_SUBGRAPH);
        add(colorPropertyPanel("Subgraph ", subgraph));

        goLevelNumbers = createColorPanel(ColorSchema.COLOR_GO_LEVEL_NUMBERS);
        add(colorPropertyPanel("GO level numbers ", goLevelNumbers));

        goLevelLines = createColorPanel(ColorSchema.COLOR_GO_LEVEL_LINES);
        add(colorPropertyPanel("GO level lines ", goLevelLines));

        goLeaves = createColorPanel(ColorSchema.COLOR_GO_LEAVES);
        add(colorPropertyPanel("GO leaves ", goLeaves));

        goNodes = createColorPanel(ColorSchema.COLOR_GO_NODES);
        add(colorPropertyPanel("GO nodes ", goNodes));

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

    /**
     * FOR TESTING ONLY
     *
     * @param args
     */
    public static void main(String[] args) {
        ColorPropertiesDialog dialog = new ColorPropertiesDialog();
        dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog.showIt();
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

}
