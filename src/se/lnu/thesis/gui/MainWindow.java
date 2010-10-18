package se.lnu.thesis.gui;

import se.lnu.thesis.Scene;

import javax.media.opengl.GLJPanel;
import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    public static final int DEFAULT_WINDOW_HEGIHT = 600;
    public static final int DEFAULT_WINDOW_WIDTH = 1200;

//    SelectionDialog selectionDialog;

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
        GLJPanel result = new GLJPanel();

        NewGOListener listener = new NewGOListener(Scene.getInstance().getGoVisualizer());
        result.addGLEventListener(listener);
        result.addMouseListener(listener);
        result.setVisible(true);

        return result;
    }

    private Component clusterPanel() {

        GLJPanel result = new GLJPanel();

        ClusterListener listener = new ClusterListener(Scene.getInstance().getClusterVisualizer());
        result.addGLEventListener(listener);
        result.addMouseListener(listener);
        result.setVisible(true);

        return result;
    }

}
