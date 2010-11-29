package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;

import javax.media.opengl.GLJPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


public class MainWindow extends JFrame {

    public static final Logger LOGGER = Logger.getLogger(MainWindow.class);

    public static final int DEFAULT_WINDOW_HEGIHT = 600;
    public static final int DEFAULT_WINDOW_WIDTH = 1200;

    JLabel statusBar;
    JScrollBar scrollBar;

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
        JPanel result = new JPanel(new BorderLayout());

        JoglPanelAdapter panelAdapter = new GOPanelAdapter(Scene.getInstance().getGoController());
        panelAdapter.setFrame(this);

        GLJPanel gljPanel = new GLJPanel();
        gljPanel.addGLEventListener(panelAdapter);
        gljPanel.addMouseListener(panelAdapter);
        gljPanel.addMouseMotionListener(panelAdapter);

        result.add(gljPanel, BorderLayout.CENTER);

        scrollBar = new JScrollBar(JScrollBar.VERTICAL);
        scrollBar.setMinimum(0);
        scrollBar.setValue(0);
        scrollBar.setVisibleAmount(3);
        scrollBar.setUnitIncrement(1);
        scrollBar.setVisible(false);

        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                int i = adjustmentEvent.getValue();

                LOGGER.debug("Current scrollbar value is " + i);

                Scene.getInstance().getGoController().scrollBarValueChanged(i);
            }
        });

        result.add(scrollBar, BorderLayout.EAST);

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

    public void setScrollBarValue(int index) {
        scrollBar.setValue(index);
    }

    public void setScrollBarMax(int max) {
        scrollBar.setMaximum(max);
    }

    public void showSrollBar() {
        scrollBar.setVisible(true);
    }

    public void hideScrollBar() {
        scrollBar.setVisible(false);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);

        window.setStatusBarText("THIS IS STATUS BAR MESSAGE");
    }
}
