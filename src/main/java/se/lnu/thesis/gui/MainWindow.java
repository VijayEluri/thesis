package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;

import javax.media.opengl.GLJPanel;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


public class MainWindow extends JFrame {

    public static final Logger LOGGER = Logger.getLogger(MainWindow.class);
    public static final String STATUS_MESSAGE_FRAME = "   ";

    JLabel goStatusBar;
    JLabel clusterStatusBar;
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

    private JComponent statusBar() {
        JPanel result = new JPanel();
        result.setBorder(new BevelBorder(BevelBorder.LOWERED));
        result.setPreferredSize(new Dimension(this.getWidth(), 16));
        result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));

        goStatusBar = new JLabel(" ");
        goStatusBar.setHorizontalAlignment(SwingConstants.LEFT);
        result.add(goStatusBar);

        clusterStatusBar = new JLabel(" ");
        clusterStatusBar.setHorizontalAlignment(SwingConstants.LEFT);
        result.add(clusterStatusBar);

        statusBar = new JLabel(" ");
        statusBar.setHorizontalAlignment(SwingConstants.LEFT);
        result.add(statusBar);

        return result;
    }

    private JComponent centralPanel() {
        JPanel result = new JPanel();

        result.setLayout(new GridLayout(1, 2));

        result.add(goPanel());
        result.add(clusterPanel());

        return result;
    }

    private JComponent goPanel() {
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
        GOPanelAdapter panelAdapter = new GOPanelAdapter(Scene.getInstance().getGoController(), this);

        GLJPanel gljPanel = new GLJPanel();
        gljPanel.addGLEventListener(panelAdapter);
        gljPanel.addMouseListener(panelAdapter);
        gljPanel.addMouseMotionListener(panelAdapter);

        return gljPanel;
    }

    private GLJPanel clusterPanel() {

        ClusterPanelAdapter panelAdapter = new ClusterPanelAdapter(Scene.getInstance().getClusterController(), this);

        GLJPanel gljPanel = new GLJPanel();
        gljPanel.addGLEventListener(panelAdapter);
        gljPanel.addMouseListener(panelAdapter);
        gljPanel.addMouseMotionListener(panelAdapter);

        return gljPanel;
    }

    public void setGOStatusBarText(String text) {
        goStatusBar.setText(frameMessage(text));
    }

    public void setClusterStatusBarText(String text) {
        clusterStatusBar.setText(frameMessage(text));
    }

    public void setStatusBarText(String text) {
        statusBar.setText(frameMessage(text));
    }

    /**
     * Frame from left and right message text with spaces:
     * <code>
     * "    " + @text + "    "
     * </code>
     *
     * @param text Source message to frame
     * @return Framed message
     */
    public String frameMessage(String text) {
        return STATUS_MESSAGE_FRAME + text + STATUS_MESSAGE_FRAME;
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
