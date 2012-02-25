package se.lnu.thesis.gui;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import se.lnu.thesis.event.SetStatusBarText;
import se.lnu.thesis.paint.controller.ClusterController;
import se.lnu.thesis.paint.controller.GOController;
import se.lnu.thesis.paint.controller.GraphController;

import javax.annotation.PostConstruct;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

@org.springframework.stereotype.Component
public class MainWindow extends JFrame implements ApplicationListener {

    public static final Logger LOGGER = Logger.getLogger(MainWindow.class);

    JLabel goStatusBar;
    JLabel clusterStatusBar;
    JLabel infoStatusBar;

    JScrollBar scrollBar;

    @Autowired
    private GOController goController;

    @Autowired
    private ClusterController clusterController;

    @Autowired
    private MainMenu mainMenu;

    public MainWindow() {

    }

    @PostConstruct
    public void initUIElements() {
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.setJMenuBar(mainMenu);

        this.add(centralPanel(), BorderLayout.CENTER);
        this.add(statusBar(), BorderLayout.SOUTH);

        this.setVisible(true);
    }

    protected JComponent statusBar() {
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

        infoStatusBar = new JLabel(" ");
        infoStatusBar.setHorizontalAlignment(SwingConstants.LEFT);
        result.add(infoStatusBar);

        return result;
    }

    protected JComponent centralPanel() {
        JPanel result = new JPanel();

        result.setLayout(new GridLayout(1, 2));

        result.add(goPanel());
        result.add(clusterGLJPanel());

        return result;
    }

    protected JComponent goPanel() {
        JPanel result = new JPanel(new BorderLayout());

        result.add(goGLJPanel(), BorderLayout.CENTER);

        result.add(goScrollBar(), BorderLayout.EAST);

        return result;
    }

    protected JScrollBar goScrollBar() {
        scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 3, 0, Integer.MAX_VALUE);
        scrollBar.setUnitIncrement(1);
        scrollBar.setBlockIncrement(2);
        scrollBar.setVisible(false);

        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                int i = adjustmentEvent.getValue();

                LOGGER.debug("Current scroll bar value is " + i);

                goController.scrollBarValueChanged(i);
            }
        });

        return scrollBar;
    }

    protected GLJPanel goGLJPanel() {
        return createGLJPanel(goController);
    }

    protected GLJPanel clusterGLJPanel() {
        return createGLJPanel(clusterController);
    }

    protected GLJPanel createGLJPanel(GraphController graphController) {
        ClusterPanelAdapter panelAdapter = new ClusterPanelAdapter(graphController, this);

        GLJPanel gljPanel = new GLJPanel();
        gljPanel.addGLEventListener(panelAdapter);
        gljPanel.addMouseListener(panelAdapter);
        gljPanel.addMouseMotionListener(panelAdapter);

        return gljPanel;
    }

    public void setGOStatusBarText(String text) {
        setStatusBarText(goStatusBar, text);
    }

    public void setClusterStatusBarText(String text) {
        setStatusBarText(clusterStatusBar, text);
    }

    public void setInfoBarText(String text) {
        setStatusBarText(infoStatusBar, text);
    }

    /**
     * Pad around text with spaces and set to status bar:
     * <code>
     * "     " + @text + "     "
     * </code>
     *
     * @param statusBar Status bar object to view text on
     * @param text      Text message to view
     */
    public void setStatusBarText(JLabel statusBar, String text) {
        text = Strings.padStart(text, 5, ' ');
        text = Strings.padEnd(text, 5, ' ');

        statusBar.setText(text);
    }

    public void setScrollBarValue(int index) {
        scrollBar.setValue(index);
    }

    public void setScrollBarMax(int max) {
        scrollBar.setMaximum(max);
    }

    public void showScrollBar() {
        scrollBar.setVisible(true);
    }

    public void hideScrollBar() {
        scrollBar.setVisible(false);
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * TODO add javadoc
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof SetStatusBarText) {
            setInfoBarText(((SetStatusBarText) event).getText());
        }
    }

    /**
     * FOR TESTING PURPOSE ONLY
     */
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);

        window.setInfoBarText("THIS IS STATUS BAR MESSAGE");
    }
}
