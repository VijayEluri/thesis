package se.lnu.thesis.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 06.12.2010
 * Time: 19:36:20
 *
 *
 *
 */
public class ColorPropertiesDialog extends JFrame {

    private Dimension d = new Dimension(25, 25);

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

    public ColorPropertiesDialog() {
        initGUI();        
    }

    protected void initGUI() {
        setLayout(new GridLayout(12, 1));

        background = initColorPanel();
        add(colorPropertyPanel("Background ", background));

        selection = initColorPanel();
        add(colorPropertyPanel("Selection ", selection));

        focusing = initColorPanel();
        add(colorPropertyPanel("Focusing ", focusing));

        subgraph = initColorPanel();
        add(colorPropertyPanel("Subgraph ", subgraph));

        goLevelNumbers = initColorPanel();
        add(colorPropertyPanel("GO level numbers ", goLevelNumbers));

        goLevelLines = initColorPanel();
        add(colorPropertyPanel("GO level lines ", goLevelLines));

        goLeaves = initColorPanel();
        add(colorPropertyPanel("GO leaves ", goLeaves));

        goNodes = initColorPanel();
        add(colorPropertyPanel("GO nodes ", goNodes));

        clusterLeaves = initColorPanel();
        add(colorPropertyPanel("Cluster leaves ", clusterLeaves));

        clusterNodes = initColorPanel();
        add(colorPropertyPanel("Cluster nodes ", clusterNodes));

        clusterEdges = initColorPanel();
        add(colorPropertyPanel("Cluster edges ", clusterEdges));       

        lensColor = initColorPanel();
        add(colorPropertyPanel("Lens color ", lensColor));

        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    protected JPanel colorPropertyPanel(String text, JPanel colorPanel) {
        JPanel result = new JPanel(new BorderLayout());
        result.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));

        result.add(new JLabel(text), BorderLayout.WEST);
        result.add(colorPanel, BorderLayout.EAST);

        return result;
    }

    protected JPanel initColorPanel() {
        JPanel result = new JPanel();

        result.setMinimumSize(d);
        result.setMaximumSize(d);
        result.setPreferredSize(d);
        result.setSize(d);

        return result;
    }

    public void showIt() {
        setVisible(true);
    }

    /**
     *              FOR TESTING ONLY
     * @param args
     */
    public static void main(String[] args) {
        ColorPropertiesDialog dialog = new ColorPropertiesDialog();
        dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog.showIt();        
    }

}
