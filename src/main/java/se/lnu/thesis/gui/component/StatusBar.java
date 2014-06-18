package se.lnu.thesis.gui.component;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import se.lnu.thesis.event.ClusterStatusBarTextEvent;
import se.lnu.thesis.event.GOStatusBarTextEvent;
import se.lnu.thesis.event.InfoStatusBarTextEvent;
import se.lnu.thesis.event.TextEvent;

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
 * </p>
 * <p>
 * _________________________________________________________________
 * |   go status bar   |   cluster status bar |   info status bar   |
 * -----------------------------------------------------------------
 * <p/>
 * <p>
 * Each of the three areas could show different information.
 * </p>
 */
@Component
public class StatusBar extends JPanel implements ApplicationListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(StatusBar.class);

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
    public void showGOInfo(String text) {
        setStatusBarText(goStatusBar, text);
    }

    /**
     * @param text View @text in the cluster status bar part.
     */
    public void showClusterInfo(String text) {
        setStatusBarText(clusterStatusBar, text);
    }

    /**
     * @param text View @text in the info status bar part.
     */
    public void showInfo(String text) {
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
        showGOInfo("");
        showClusterInfo("");
        showInfo("");
    }

    /**
     * Analyze events from application context pipe.
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof GOStatusBarTextEvent) {
            showGOInfo(TextEvent.class.cast(event).getText());
        } else if (event instanceof ClusterStatusBarTextEvent) {
            showClusterInfo(TextEvent.class.cast(event).getText());
        } else if (event instanceof InfoStatusBarTextEvent) {
            showInfo(TextEvent.class.cast(event).getText());
        } else {
            LOGGER.warn("UNHANDLED EVENT: " + event);
        }
    }

}
