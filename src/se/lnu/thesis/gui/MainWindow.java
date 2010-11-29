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

    JLabel statusBar;
    JScrollBar scrollBar;

    public MainWindow() {
        initUIElements();
    }

    private void initUIElements() {
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
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

        result.add(goDrawingPanel(), BorderLayout.CENTER);

        result.add(goScrollBar(), BorderLayout.EAST);

        return result;
    }

    private JScrollBar goScrollBar() {
        scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 3, 0, Integer.MAX_VALUE);
        scrollBar.setUnitIncrement(1);
        scrollBar.setBlockIncrement(2);
        scrollBar.setVisible(false);

        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                int i = adjustmentEvent.getValue();

                LOGGER.debug("Current scrollbar value is " + i);

                Scene.getInstance().getGoController().scrollBarValueChanged(i);
            }
        });

        return scrollBar;
    }

    private GLJPanel goDrawingPanel() {
        JoglPanelAdapter panelAdapter = new GOPanelAdapter(Scene.getInstance().getGoController());
        panelAdapter.setFrame(this);

        GLJPanel gljPanel = new GLJPanel();
        gljPanel.addGLEventListener(panelAdapter);
        gljPanel.addMouseListener(panelAdapter);
        gljPanel.addMouseMotionListener(panelAdapter);
        return gljPanel;
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
