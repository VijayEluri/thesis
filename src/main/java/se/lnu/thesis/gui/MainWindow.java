package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import se.lnu.thesis.event.RepaintWindowEvent;
import se.lnu.thesis.gui.component.StatusBar;
import se.lnu.thesis.paint.controller.ClusterController;
import se.lnu.thesis.paint.controller.GOController;
import se.lnu.thesis.paint.controller.GraphController;

import javax.annotation.PostConstruct;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

@org.springframework.stereotype.Component
public class MainWindow extends JFrame implements ApplicationListener {

    public static final Logger LOGGER = Logger.getLogger(MainWindow.class);

    JScrollBar scrollBar;

    @Autowired
    private GOController goController;

    @Autowired
    private ClusterController clusterController;

    @Autowired
    private MainMenu mainMenu;

    @Autowired
    private StatusBar statusBar;

    public MainWindow() {

    }

    @PostConstruct
    public void initUIElements() {
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.setJMenuBar(mainMenu);

        this.add(centralPanel(), BorderLayout.CENTER);
        this.add(statusBar, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    protected JComponent centralPanel() {
        JPanel result = new JPanel();

        result.setLayout(new GridLayout(1, 2));

        result.add(goPanel());
        result.add(createGLJPanel(clusterController));

        return result;
    }

    protected JComponent goPanel() {
        JPanel result = new JPanel(new BorderLayout());

        result.add(createGLJPanel(goController), BorderLayout.CENTER);

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

    protected GLJPanel createGLJPanel(GraphController graphController) {
        JoglPanelAdapter panelAdapter = new JoglPanelAdapter(graphController, this);

        GLJPanel result = new GLJPanel();
        result.addGLEventListener(panelAdapter);
        result.addMouseListener(panelAdapter);
        result.addMouseMotionListener(panelAdapter);

        return result;
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
     * Analyze events from application context pipe.
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof RepaintWindowEvent) {
            this.repaint();
        }
    }
}
