package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.paint.GraphVisualizer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.05.2010
 * Time: 0:48:58
 * <p/>
 * JOGL window
 */
public class JoglListener implements GLEventListener, MouseListener, MouseMotionListener {


    public static final Logger LOGGER = Logger.getLogger(JoglListener.class);

    protected GraphVisualizer visualizer;

    public JoglListener() {

    }

    public JoglListener(GraphVisualizer visualizer) {
        setVisualizer(visualizer);
    }

    public void init(GLAutoDrawable drawable) {

    }

    public void display(GLAutoDrawable drawable) {
        if (getVisualizer() != null) {
            getVisualizer().draw(drawable);
        }
    }


    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

/*
        int length;
        int shift = 0;

        if (height < width) {
            length = height;
            shift = (width - height) / 2;
        } else {
            length = width;
        }

        gl.glViewport(shift, 0, length, length);

*/
        gl.glViewport(0, 0, width, height);

        Scene.getInstance().getMainWindow().repaint();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChange) {
    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
/*
        visualizer.setCursor(mouseEvent.getPoint());

        Scene.getInstance().getMainWindow().repaint();
*/
    }

    public void mousePressed(java.awt.event.MouseEvent mouseEvent) {
        visualizer.click(mouseEvent.getPoint());

        Scene.getInstance().getMainWindow().repaint();
    }

    public void mouseReleased(java.awt.event.MouseEvent mouseEvent) {
    }

    public void mouseEntered(java.awt.event.MouseEvent mouseEvent) {
    }

    public void mouseExited(java.awt.event.MouseEvent mouseEvent) {
    }

    public void setVisualizer(GraphVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public GraphVisualizer getVisualizer() {
        return visualizer;
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        LOGGER.debug("Mouse moves [" + mouseEvent.getX() + "," + mouseEvent.getY() + "]");
        visualizer.move(mouseEvent.getPoint());

        Scene.getInstance().getMainWindow().repaint();
    }
}
