package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.05.2010
 * Time: 0:48:58
 * <p/>
 * JOGL window
 */
public abstract class JoglWindow extends JFrame implements KeyListener, GLEventListener, Observer {

    private static final int DEFAULT_WINDOW_HEGIHT = 800;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

    public static final Logger LOGGER = Logger.getLogger(JoglWindow.class);

    protected GraphWithSubgraphVisualizer visualizer;

    private GLJPanel drawablePanel;


    public JoglWindow() {
        setSize(DEFAULT_WINDOW_HEGIHT, DEFAULT_WINDOW_WIDTH);
        initDrawablePanel();
    }

    public void initDrawablePanel() {
        drawablePanel = new GLJPanel();

        drawablePanel.addGLEventListener(this);
        drawablePanel.setVisible(true);

        add(drawablePanel);
    }

    public void display(GLAutoDrawable drawable) {
        if (getVisualizer() != null) {
            getVisualizer().draw(drawable);
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        LOGGER.info("Key code:" + keyCode);

        if (keyCode == KeyEvent.VK_ALT) {
            visualizer.zoomOut();
        }
        if (keyCode == KeyEvent.VK_SHIFT) {
            visualizer.zoomIn();
        }

        if (keyCode == KeyEvent.VK_UP) {
            visualizer.down();
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            visualizer.up();
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            visualizer.left();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            visualizer.right();
        }

        repaint();
    }

    public GraphWithSubgraphVisualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(GraphWithSubgraphVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public void keyPressed(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int heigh) {
        repaint();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChange) {
    }

    public abstract void notifyObserver(Subject subject, Object params);
}
