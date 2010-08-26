package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.visualizer.GraphVisualizer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.swing.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.05.2010
 * Time: 0:48:58
 * <p/>
 * JOGL window
 */
public abstract class JoglWindow extends JFrame implements KeyListener, GLEventListener, Observer, MouseWheelListener, MouseListener {

    private static final int DEFAULT_WINDOW_HEGIHT = 800;
    private static final int DEFAULT_WINDOW_WIDTH = 800;

    public static final Logger LOGGER = Logger.getLogger(JoglWindow.class);

    protected GraphVisualizer visualizer;

    private GLJPanel drawablePanel;


    public JoglWindow() {
        setSize(DEFAULT_WINDOW_HEGIHT, DEFAULT_WINDOW_WIDTH);
        initDrawablePanel();
    }

    public void initDrawablePanel() {
        drawablePanel = new GLJPanel();

        drawablePanel.addGLEventListener(this);
        drawablePanel.addMouseWheelListener(this);
        drawablePanel.addKeyListener(this);
        drawablePanel.addMouseListener(this);

        drawablePanel.setVisible(true);

        add(drawablePanel);
    }

    public void display(GLAutoDrawable drawable) {
        if (getVisualizer() != null) {
            getVisualizer().draw(drawable);
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        LOGGER.info("Key code:" + keyCode);

/*
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
*/

        repaint();
    }

    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        int i = mouseWheelEvent.getWheelRotation();

        LOGGER.debug("Mouse wheel event. " + i);

        /*       if (i > 0) {
                    visualizer.zoomOut();
                } else {
                    visualizer.zoomIn();
                }
        */
        repaint();
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int heigh) {
        GL gl = drawable.getGL();

        int length;
        int shift = 0;

        if (heigh < width) {
            length = heigh;
            shift = (width - heigh) / 2;
        } else {
            length = width;
        }

        gl.glViewport(shift, 0, length, length);

        repaint();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChange) {
    }

    public void setVisualizer(GraphVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public void keyTyped(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        visualizer.setCursor(mouseEvent.getPoint());

        repaint();
    }

    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        visualizer.setCursor(mouseEvent.getPoint());

        repaint();
    }

    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
        //To change body of created methods use File | Settings | File Templates.
    }


    public abstract void notifyObserver(Subject subject, Object params);

    public GraphVisualizer getVisualizer() {
        return visualizer;
    }
}
