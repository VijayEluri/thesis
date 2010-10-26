package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.core.MyGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu extends JMenuBar implements ActionListener {

    public static final Logger LOGGER = Logger.getLogger(MainMenu.class);

    public static final String FILE = "File";
    public static final String OPEN_GO_GRAPH = "Open GO graph";
    public static final String OPEN_CLUSTER_GRAPH = "Open cluster graph";

    private GraphChooser graphChooser;

    public MainMenu() {
        initFileChooser();
        createFileMenu();
    }

    private void initFileChooser() {
        graphChooser = new GraphChooser();
    }

    private void createFileMenu() {
        constructMenu(FILE, new String[]{OPEN_GO_GRAPH, OPEN_CLUSTER_GRAPH}, this);
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

        Scene.getInstance().getMainWindow().repaint();
    }
}
