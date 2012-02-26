package se.lnu.thesis.gui.component;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import se.lnu.thesis.event.ClusterStatusBarTextEvent;
import se.lnu.thesis.event.GOStatusBarTextEvent;
import se.lnu.thesis.event.InfoStatusBarTextEvent;
import se.lnu.thesis.event.StatusBarTextEvent;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.12
 * <p/>
 * GUI class represents status bar of the window.
 * Status bar consists from three parts:
 * _________________________________________________________________
 * |   go status bar   |   cluster status bar |   info status bar   |
 * -----------------------------------------------------------------
 * <p/>
 * Each of the three areas could show different information.
 */
@Component
public class StatusBar extends JPanel implements ApplicationListener {

    private JLabel goStatusBar;
    private JLabel clusterStatusBar;
    private JLabel infoStatusBar;

    @PostConstruct
    public void init() {
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setPreferredSize(new Dimension(this.getWidth(), 16));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        goStatusBar = createStatusBarPart();
        add(goStatusBar);

        clusterStatusBar = createStatusBarPart();
        add(clusterStatusBar);

        infoStatusBar = createStatusBarPart();
        add(infoStatusBar);

    }

    /**
     * Create part of the status bar.
     *
     * @return
     */
    public JLabel createStatusBarPart() {
        JLabel result = new JLabel(" ");

        result.setHorizontalAlignment(SwingConstants.LEFT);

        return result;
    }

    /**
     * @param text View @text in the GO status bar part.
     */
    public void setGOStatusBarText(String text) {
        setStatusBarText(goStatusBar, text);
    }

    /**
     * @param text View @text in the cluster status bar part.
     */
    public void setClusterStatusBarText(String text) {
        setStatusBarText(clusterStatusBar, text);
    }

    /**
     * @param text View @text in the info status bar part.
     */
    public void setInfoStatusBarText(String text) {
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
    protected void setStatusBarText(JLabel statusBar, String text) {
        Preconditions.checkNotNull(statusBar);
        Preconditions.checkNotNull(text);
        statusBar.setText(Strings.padEnd(Strings.padStart(text, 5, ' '), 5, ' '));
    }

    /**
     * Clear all texts from status bar.
     */
    public void clear() {
        setGOStatusBarText("");
        setClusterStatusBarText("");
        setInfoStatusBarText("");
    }

    /**
     * Analyze events from application context pipe.
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof GOStatusBarTextEvent) {
            setGOStatusBarText(((StatusBarTextEvent) event).getText());
        }
        if (event instanceof ClusterStatusBarTextEvent) {
            setClusterStatusBarText(((StatusBarTextEvent) event).getText());
        }
        if (event instanceof InfoStatusBarTextEvent) {
            setInfoStatusBarText(((StatusBarTextEvent) event).getText());
        }
    }

}
