package se.lnu.thesis.gui;

import se.lnu.thesis.Scene;

import javax.media.opengl.GLJPanel;
import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    public static final int DEFAULT_WINDOW_HEGIHT = 600;
    public static final int DEFAULT_WINDOW_WIDTH = 1200;

    JLabel statusBar;

    public MainWindow() {
        initUIElements();
    }

    private void initUIElements() {
        this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEGIHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.setJMenuBar(new MainMenu());

        this.add(centralPanel(), BorderLayout.CENTER);
        this.add(statusBar(), BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private JLabel statusBar() {
        statusBar = new JLabel(" ");

        return statusBar;
    }

    private Component centralPanel() {
        JPanel result = new JPanel();

        result.setLayout(new GridLayout(1, 2));

        result.add(goPanel());
        result.add(clusterPanel());

        return result;
    }

    private Component goPanel() {

        JoglPanelAdapter panelAdapter = new GOPanelAdapter(Scene.getInstance().getGoController());
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
        panelAdapter.setGraphController(Scene.getInstance().getClusterController());
        panelAdapter.setFrame(this);

        GLJPanel result = new GLJPanel();
        result.addGLEventListener(panelAdapter);
        result.addMouseListener(panelAdapter);
        result.addMouseMotionListener(panelAdapter);
        result.setVisible(true);

        return result;
    }

    public void setStatusBarText(String text) {
        statusBar.setText(text);
    }

    public void clearStatusBar() {
        setStatusBarText(" ");
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);

        window.setStatusBarText("THIS IS STATUS BAR MESSAGE");
    }
}
