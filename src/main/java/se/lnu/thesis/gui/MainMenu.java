package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.layout.HierarchyLayout;
import se.lnu.thesis.layout.HierarchyLayout2;
import se.lnu.thesis.properties.PropertiesHolder;
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

    public static final String GENE_ONTOLOGY = "Gene Ontology";

    public static final String TOOLS = "Tools";
    public static final String SHOW_GENE_LIST = "Show GO gene list";

    public static final String LEVEL_LAYOUT = "Levels layout";
    public static final String LEAF_BOTTOM_LAYOUT = "Leaf bottom layout";

    public static final String DEFAULT_BLACK_COLOR_SCHEMA = "Default black color schema";


    //    public static final String DEFAULT_WHITE_COLOR_SCHEMA = "Default white color schema";
    public static final String COLOR_PROPERTIES = "Color properties";

    private GraphChooser graphChooser;

    protected JMenu geneOntologyMenu;
    protected JRadioButtonMenuItem levelLayoutMenuItem;
    protected JRadioButtonMenuItem leafBottomLayoutMenuItem;


    public MainMenu() {
        initFileChooser();
        createFileMenu();
        createGOMenu();
        createToolsMenu();
    }

    private void initFileChooser() {
        graphChooser = new GraphChooser();
    }

    private void createFileMenu() {
        constructMenu(FILE, new String[]{OPEN_GO_GRAPH, OPEN_CLUSTER_GRAPH}, this);
    }

    protected void createGOMenu() {
        geneOntologyMenu = new JMenu(GENE_ONTOLOGY);
        geneOntologyMenu.setEnabled(false);

        levelLayoutMenuItem = new JRadioButtonMenuItem ();
        levelLayoutMenuItem.setName(LEVEL_LAYOUT);
        levelLayoutMenuItem.setText(LEVEL_LAYOUT);
        levelLayoutMenuItem.addActionListener(this);
        levelLayoutMenuItem.setSelected(true);
        geneOntologyMenu.add(levelLayoutMenuItem);

        leafBottomLayoutMenuItem = new JRadioButtonMenuItem ();
        leafBottomLayoutMenuItem.setName(LEAF_BOTTOM_LAYOUT);
        leafBottomLayoutMenuItem.setText(LEAF_BOTTOM_LAYOUT);
        leafBottomLayoutMenuItem.addActionListener(this);
        geneOntologyMenu.add(leafBottomLayoutMenuItem);

        ButtonGroup group = new ButtonGroup();
        group.add(levelLayoutMenuItem);
        group.add(leafBottomLayoutMenuItem);

        geneOntologyMenu.addSeparator();



        this.add(geneOntologyMenu);
    }

    private void createToolsMenu() {
        constructMenu(TOOLS, new String[]{SHOW_GENE_LIST, "-", DEFAULT_BLACK_COLOR_SCHEMA, /*DEFAULT_WHITE_COLOR_SCHEMA,*/ COLOR_PROPERTIES}, this);
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

                geneOntologyMenu.setEnabled(true);
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

        if (event == DEFAULT_BLACK_COLOR_SCHEMA) {
            PropertiesHolder.getInstance().getColorSchema().useDefaultBlackSchema();
            PropertiesHolder.getInstance().save();
        }

/*
        if (event == DEFAULT_WHITE_COLOR_SCHEMA) {

        }
*/
        if (event == LEVEL_LAYOUT) {
            Scene.getInstance().getGoController().setGraphLayout(new HierarchyLayout());
            Scene.getInstance().getGoController().init();
            Scene.getInstance().getClusterController().setSubGraph(null); // reset all subgraph highlighting
        }

        if (event == LEAF_BOTTOM_LAYOUT) {
            Scene.getInstance().getGoController().setGraphLayout(new HierarchyLayout2());
            Scene.getInstance().getGoController().init();
            Scene.getInstance().getClusterController().setSubGraph(null); // reset all subgraph highlighting
        }

        if (event == COLOR_PROPERTIES) {
            Scene.getInstance().getColorPropertiesDialog().showIt();
        }

        Scene.getInstance().getMainWindow().repaint();
    }
}
