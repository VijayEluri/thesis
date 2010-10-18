package se.lnu.thesis.gui;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.layout.AbstractLayout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.element.GroupingElement;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.*;
import java.awt.*;


public class LensDialog extends JFrame implements GLEventListener {

    public static final Logger LOGGER = Logger.getLogger(LensDialog.class);

    public static final double LENS_RADIUS = 0.9;
    public static final double LAYOUT_RADIUS = 0.8;

    protected Color background = Color.WHITE;

    protected Color circleColor = Color.BLACK;

    private AbstractLayout layout = new PolarDendrogramLayout(LAYOUT_RADIUS);

    private GroupingElement root = null;

    private GLU glu = new GLU();


    public LensDialog() {
        GLJPanel panel = new GLJPanel();
        panel.addGLEventListener(this);

        this.add(panel);

        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }


    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1.0f); // background color

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_DEPTH_TEST);


        if (root != null) {
            gl.glColor3d(Utils.colorAsDouble(circleColor.getRed()),
                    Utils.colorAsDouble(circleColor.getGreen()),
                    Utils.colorAsDouble(circleColor.getBlue()));

            GLUquadric glUquadric = glu.gluNewQuadric();
            glu.gluDisk(glUquadric, 0, LENS_RADIUS, 50, 50);

            root.draw(drawable);
        }

        drawable.swapBuffers();

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        int length;
        int shift = 0;

        if (height < width) {
            length = height;
            shift = (width - height) / 2;
        } else {
            length = width;
        }

        gl.glViewport(shift, 0, length, length);

        this.repaint();
    }

    public void setRoot(GroupingElement root) {
        this.root = root;

        if (!root.isLayoutComputed() && Scene.getInstance().getClusterGraph() != null) {
            Graph groupGraph = new MyGraph();
            GraphUtils.extractSubgraph(Scene.getInstance().getClusterGraph(), groupGraph, root.getObject());

            layout.setGraph(groupGraph);
            layout.setRoot(root);

            LOGGER.info("Group layout is not computed. Computing lens..");
            layout.compute();
            LOGGER.info("Done.");
        }

        this.setVisible(true);

        this.repaint();
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean b, boolean b1) {
    }

}
