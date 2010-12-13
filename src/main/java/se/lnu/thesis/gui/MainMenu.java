package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu extends JMenuBar implements ActionListener {

    public static final Logger LOGGER = Logger.getLogger(MainMenu.class);

    public static final String FILE = "File";
    public static final String OPEN_GO_GRAPH = "Open GO graph";
    public static final String OPEN_CLUSTER_GRAPH = "Open cluster graph";

    public static final String TOOLS = "Tools";
    public static final String SHOW_GENE_LIST = "Show GO gene list";
    public static final String OPEN_BACKGROUND_CHOOSER = "Set background color";


    private GraphChooser graphChooser;

    public MainMenu() {
        initFileChooser();
        createFileMenu();
        createToolsMenu();
    }

    private void initFileChooser() {
        graphChooser = new GraphChooser();
    }

    private void createFileMenu() {
        constructMenu(FILE, new String[]{OPEN_GO_GRAPH, OPEN_CLUSTER_GRAPH}, this);
    }

    private void createToolsMenu() {
        constructMenu(TOOLS, new String[]{SHOW_GENE_LIST, OPEN_BACKGROUND_CHOOSER}, this);
    }


    public void constructMenu(String menuName, String[] elements, ActionListener actionListener) {
        JMenu menu = new JMenu(menuName);

        for (String name : elements) {
            if (name.equals("-")) {
                menu.addSeparator();
            } else {
                menu.add(menuItem(name, name, actionListener));
            }
        }

        this.add(menu);
    }

    protected JMenuItem menuItem(String name, String text, ActionListener actionListener) {
        JMenuItem result = new JMenuItem();

        result.setName(name);
        result.setText(text);

        result.addActionListener(actionListener);

        return result;
    }


    public void actionPerformed(ActionEvent actionEvent) {
        String event = ((JMenuItem) actionEvent.getSource()).getName();

        LOGGER.info("EVENT: " + event);


        if (event == OPEN_GO_GRAPH) {
            MyGraph graph = graphChooser.open();
            if (graph != null) {
                Scene.getInstance().setGoGraph(graph);
            }
        }

        if (event == OPEN_CLUSTER_GRAPH) {
            MyGraph graph = graphChooser.open();
            if (graph != null) {
                Scene.getInstance().setClusterGraph(graph);
            }
        }

        if (event == SHOW_GENE_LIST) {
            Scene.getInstance().showGeneList();
        }

        if (event == OPEN_BACKGROUND_CHOOSER) {

            Color initialBackground = Scene.getInstance().getGoController().getBackground().asAWTColor();
            Color color = JColorChooser.showDialog(null, null, initialBackground);

            if (color != null) {
                LOGGER.debug("Setting new background color [" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "]");

                Scene.getInstance().getGoController().getBackground().setColor(color);
                Scene.getInstance().getClusterController().getBackground().setColor(color);

                ElementVisualizerFactory.getInstance().getLevelVisualizer().getBackground().setColor(color);
                ElementVisualizerFactory.getInstance().getLevelPreviewVisualizer().getBackground().setColor(color);
            }
        }

        Scene.getInstance().getMainWindow().repaint();
    }
}
