package se.lnu.thesis.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import se.lnu.thesis.Scene;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.event.ClusterGraphLoaded;
import se.lnu.thesis.event.GOGraphLoaded;
import se.lnu.thesis.event.RepaintWindowEvent;
import se.lnu.thesis.gui.properties.ColorPropertiesDialog;
import se.lnu.thesis.layout.HierarchyLayout;
import se.lnu.thesis.layout.LeafsBottomHierarchyLayout;
import se.lnu.thesis.paint.controller.GOController;
import se.lnu.thesis.properties.PropertiesHolder;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

@Component
public class MainMenu extends JMenuBar implements ActionListener, ItemListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(MainMenu.class);

    public static final String FILE = "File";
    public static final String OPEN_GO_GRAPH = "Open GO graph";
    public static final String OPEN_CLUSTER_GRAPH = "Open cluster graph";


    public static final String GENE_ONTOLOGY = "Gene Ontology";
    public static final String LEVEL_LAYOUT = "Levels layout";
    public static final String LEAF_BOTTOM_LAYOUT = "Leaf bottom layout";
    public static final String SHOW_UNCONNECTED_COMPONENTS = "Show unconnected components";
    public static final String SHOW_SUBGRAPH_EDGES = "Show subgraph edges";

    public static final String CLUSTER = "Cluster";
    public static final String RADIAL_LENS = "Radial lens";
    public static final String RECT_LENS = "Rectangular lens";

    public static final String TOOLS = "Tools";
    public static final String SHOW_GENE_LIST = "Show GO gene list";
    public static final String DEFAULT_BLACK_COLOR_SCHEMA = "Default black color schema";
    public static final String COLOR_PROPERTIES = "Color properties";

    @Autowired
    private GraphChooser graphChooser;

    protected JMenu geneOntologyMenu;
    protected JCheckBoxMenuItem showUnconnectedComponentsMenuItem;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private Scene scene;

    @Autowired
    private GeneListDialog geneListDialog;

    @Autowired
    private ColorPropertiesDialog colorPropertiesDialog;

    @Autowired
    private ApplicationEventPublisher publisher;


    public MainMenu() {

    }

    @PostConstruct
    public void init() {
        createFileMenu();
        createGOMenu();
        createClusterMenu();
        createToolsMenu();
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

        JCheckBoxMenuItem showSubgraphEdgesMenuItem = new JCheckBoxMenuItem(SHOW_SUBGRAPH_EDGES);
        showSubgraphEdgesMenuItem.setName(SHOW_SUBGRAPH_EDGES);
        showSubgraphEdgesMenuItem.setText(SHOW_SUBGRAPH_EDGES);
        showSubgraphEdgesMenuItem.addItemListener(this);
        showSubgraphEdgesMenuItem.setSelected(true);
        geneOntologyMenu.add(showSubgraphEdgesMenuItem);


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
                publisher.publishEvent(new GOGraphLoaded(this, graph));

                geneOntologyMenu.setEnabled(true);
            }
        }

        if (event == OPEN_CLUSTER_GRAPH) {
            MyGraph graph = graphChooser.open();
            if (graph != null) {
                publisher.publishEvent(new ClusterGraphLoaded(this, graph));
            }
        }

        if (event == SHOW_GENE_LIST) {
            geneListDialog.setVisible(true);
        }

        if (event == DEFAULT_BLACK_COLOR_SCHEMA) {
            PropertiesHolder.getInstance().getColorSchema().useDefaultBlackSchema();
            PropertiesHolder.getInstance().save();
        }

        if (event == LEVEL_LAYOUT) {
            switchGeneOntologyLayout(new HierarchyLayout());
        }

        if (event == LEAF_BOTTOM_LAYOUT) {
            switchGeneOntologyLayout(new LeafsBottomHierarchyLayout());
        }

        if (event == RADIAL_LENS) {
            scene.setRadialLens();
        }

        if (event == RECT_LENS) {
            scene.setRectLens();
        }

        if (event == COLOR_PROPERTIES) {
            colorPropertiesDialog.showIt();
        }

        applicationEventPublisher.publishEvent(new RepaintWindowEvent(this));
    }

    /**
     * TODO change all this direct calls to the event which <code>GOController</code> class should listen to
     *
     * @param layout
     */
    public void switchGeneOntologyLayout(HierarchyLayout layout) {
        scene.resetSubgraphHighlighting();

        scene.getGoController().setGraphLayout(layout);
        scene.getGoController().init();

        if (!showUnconnectedComponentsMenuItem.isSelected()) {
            scene.hideUnconnectedComponents();
        }
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        GOController goController = scene.getGoController();

        if (goController != null) {
            GOGraphContainer goGraphContainer = (GOGraphContainer) goController.getRoot();

            if (itemEvent.getItem() instanceof JCheckBoxMenuItem) {

                JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) itemEvent.getItem();
                if (checkBoxMenuItem.getName().equals(SHOW_UNCONNECTED_COMPONENTS)) {
                    if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                        scene.showUnconnectedComponents();
                    }

                    if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
                        scene.hideUnconnectedComponents();
                    }
                }

                if (checkBoxMenuItem.getName().equals(SHOW_SUBGRAPH_EDGES)) {
                    if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                        if (goGraphContainer != null) {
                            goGraphContainer.showSubgraphEdges();
                        }
                    }

                    if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
                        if (goGraphContainer != null) {
                            goGraphContainer.hideSubgraphEdges();
                        }
                    }
                }

            }
        }

    }

}
