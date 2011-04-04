package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.layout.HierarchyLayout;
import se.lnu.thesis.layout.HierarchyLayout2;
import se.lnu.thesis.paint.lens.RadialLens;
import se.lnu.thesis.paint.lens.RectLens;
import se.lnu.thesis.properties.PropertiesHolder;
import se.lnu.thesis.core.MyGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class MainMenu extends JMenuBar implements ActionListener, ItemListener {

    public static final Logger LOGGER = Logger.getLogger(MainMenu.class);

    public static final String FILE = "File";
    public static final String OPEN_GO_GRAPH = "Open GO graph";
    public static final String OPEN_CLUSTER_GRAPH = "Open cluster graph";


    public static final String GENE_ONTOLOGY = "Gene Ontology";
    public static final String LEVEL_LAYOUT = "Levels layout";
    public static final String LEAF_BOTTOM_LAYOUT = "Leaf bottom layout";
    public static final String SHOW_UNCONNECTED_COMPONENTS = "Show unconnected components";

    public static final String CLUSTER = "Cluster";
    public static final String RADIAL_LENS = "Radial lens";
    public static final String RECT_LENS = "Rectangular lens";

    public static final String TOOLS = "Tools";
    public static final String SHOW_GENE_LIST = "Show GO gene list";
    public static final String DEFAULT_BLACK_COLOR_SCHEMA = "Default black color schema";
    public static final String COLOR_PROPERTIES = "Color properties";

    //    public static final String DEFAULT_WHITE_COLOR_SCHEMA = "Default white color schema";


    private GraphChooser graphChooser;

    protected JMenu geneOntologyMenu;
    protected JCheckBoxMenuItem showUnconnectedComponentsMenuItem;


    public MainMenu() {
        initFileChooser();
        createFileMenu();
        createGOMenu();
        createClusterMenu();
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

        JRadioButtonMenuItem levelLayoutMenuItem = new JRadioButtonMenuItem();
        levelLayoutMenuItem.setName(LEVEL_LAYOUT);
        levelLayoutMenuItem.setText(LEVEL_LAYOUT);
        levelLayoutMenuItem.addActionListener(this);
        levelLayoutMenuItem.setSelected(true);
        geneOntologyMenu.add(levelLayoutMenuItem);

        JRadioButtonMenuItem leafBottomLayoutMenuItem = new JRadioButtonMenuItem();
        leafBottomLayoutMenuItem.setName(LEAF_BOTTOM_LAYOUT);
        leafBottomLayoutMenuItem.setText(LEAF_BOTTOM_LAYOUT);
        leafBottomLayoutMenuItem.addActionListener(this);
        geneOntologyMenu.add(leafBottomLayoutMenuItem);

        ButtonGroup group = new ButtonGroup();
        group.add(levelLayoutMenuItem);
        group.add(leafBottomLayoutMenuItem);

        geneOntologyMenu.addSeparator();

        showUnconnectedComponentsMenuItem = new JCheckBoxMenuItem(SHOW_UNCONNECTED_COMPONENTS);
        showUnconnectedComponentsMenuItem.setName(SHOW_UNCONNECTED_COMPONENTS);
        showUnconnectedComponentsMenuItem.setText(SHOW_UNCONNECTED_COMPONENTS);
        showUnconnectedComponentsMenuItem.addItemListener(this);
        showUnconnectedComponentsMenuItem.setSelected(true);
        geneOntologyMenu.add(showUnconnectedComponentsMenuItem);


        this.add(geneOntologyMenu);
    }

    private void createClusterMenu() {
        JMenu clusterMenu = new JMenu(CLUSTER);

        JRadioButtonMenuItem radialLensMenuItem = new JRadioButtonMenuItem();
        radialLensMenuItem.setName(RADIAL_LENS);
        radialLensMenuItem.setText(RADIAL_LENS);
        radialLensMenuItem.addActionListener(this);
        radialLensMenuItem.setSelected(true);
        clusterMenu.add(radialLensMenuItem);

        JRadioButtonMenuItem rectangularLensMenuItem = new JRadioButtonMenuItem();
        rectangularLensMenuItem.setName(RECT_LENS);
        rectangularLensMenuItem.setText(RECT_LENS);
        rectangularLensMenuItem.addActionListener(this);
        clusterMenu.add(rectangularLensMenuItem);

        ButtonGroup group = new ButtonGroup();
        group.add(radialLensMenuItem);
        group.add(rectangularLensMenuItem);

        this.add(clusterMenu);
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
            switchGeneOntologyLayout(new HierarchyLayout());
        }

        if (event == LEAF_BOTTOM_LAYOUT) {
            switchGeneOntologyLayout(new HierarchyLayout2());
        }

        if (event == RADIAL_LENS) {
            Scene.getInstance().setRadialLens();
        }

        if (event == RECT_LENS) {
            Scene.getInstance().setRectLens();
        }

        if (event == COLOR_PROPERTIES) {
            Scene.getInstance().getColorPropertiesDialog().showIt();
        }

        Scene.getInstance().getMainWindow().repaint();
    }

    public void switchGeneOntologyLayout(HierarchyLayout layout) {
        Scene.getInstance().resetSubgraphHighlighting();

        Scene.getInstance().getGoController().setGraphLayout(layout);
        Scene.getInstance().getGoController().init();

        if (!showUnconnectedComponentsMenuItem.isSelected()) {
            GOGraphContainer goGraphContainer = (GOGraphContainer) Scene.getInstance().getGoController().getRoot();
            goGraphContainer.hideUnconnectedComponents();
        }
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        GOGraphContainer goGraphContainer = (GOGraphContainer) Scene.getInstance().getGoController().getRoot();

        if (itemEvent.getItem() instanceof JCheckBoxMenuItem) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                if (goGraphContainer != null) {
                    goGraphContainer.showUnconnectedComponents();
                }
            }

            if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
                goGraphContainer.hideUnconnectedComponents();
            }
        }
    }
}
