package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.io.IOFacade;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class MainMenu extends JMenuBar implements ActionListener {

    public static final Logger LOGGER = Logger.getLogger(MainMenu.class);

    public static final String FILE = "File";
    public static final String OPEN_GO_GRAPH = "Open GO graph";
    public static final String OPEN_CLUSTER_GRAPH = "Open cluster graph";

    private JFileChooser fileChooser;
    private IOFacade ioFacade;

    public MainMenu() {
        initFileChooser();
        createFileMenu();
    }

    private void initFileChooser() {
        ioFacade = new IOFacade();

        fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".gml");  // TODO fix it
            }

            @Override
            public String getDescription() {
                return "gml";
            }
        });

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
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Scene.getInstance().setGoGraph(ioFacade.loadFromYedGraphml(fileChooser.getSelectedFile()));
            }

        }

        if (event == OPEN_CLUSTER_GRAPH) {
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Scene.getInstance().setClusterGraph(ioFacade.loadFromYedGraphml(fileChooser.getSelectedFile()));
            }
        }

        Scene.getInstance().getMainWindow().repaint();
    }
}
