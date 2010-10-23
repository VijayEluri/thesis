package se.lnu.thesis.gui;

import se.lnu.thesis.Scene;
import se.lnu.thesis.core.MyGraph;

import javax.media.opengl.GLJPanel;
import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    public static final int DEFAULT_WINDOW_HEGIHT = 600;
    public static final int DEFAULT_WINDOW_WIDTH = 1200;

//    SelectionDialog selectionDialog;

    MyGraph clusterGraph;

    public MainWindow() {
        initUIElements();
    }

    private void initUIElements() {

        this.setLayout(new GridLayout(1, 2));
        this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEGIHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setJMenuBar(new MainMenu());

        this.add(goPanel());
        this.add(clusterPanel());

        this.setVisible(true);
    }

    private Component goPanel() {

        JoglPanelAdapter panelAdapter = new JoglPanelAdapter(Scene.getInstance().getGoVisualizer());
        panelAdapter.setFrame(this);

        GLJPanel result = new GLJPanel();
        result.addGLEventListener(panelAdapter);
        result.addMouseListener(panelAdapter);
        result.addMouseMotionListener(panelAdapter);
        result.setVisible(true);

        return result;
    }

    private Component clusterPanel() {

        JoglPanelAdapter panelAdapter = new ClusterPanelAdapter();
        panelAdapter.setGraphController(Scene.getInstance().getClusterVisualizer());
        panelAdapter.setFrame(this);

        GLJPanel result = new GLJPanel();
        result.addGLEventListener(panelAdapter);
        result.addMouseListener(panelAdapter);
        result.addMouseMotionListener(panelAdapter);
        result.setVisible(true);

        return result;
    }

    public MyGraph getClusterGraph() {
        return clusterGraph;
    }

    public void setClusterGraph(MyGraph clusterGraph) {
        this.clusterGraph = clusterGraph;
    }
}
