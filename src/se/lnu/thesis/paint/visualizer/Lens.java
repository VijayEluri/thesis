package se.lnu.thesis.paint.visualizer;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.GroupElement;
import se.lnu.thesis.layout.AbstractLayout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.utils.GraphUtils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.07.2010
 */
public class Lens implements Drawable {

    public static final Logger LOGGER = Logger.getLogger(Lens.class);

    private static final double LENS_RADIUS = 0.4;
    private static final double LAYOUT_RADIUS = 0.3;

    protected Color circleColor = Color.BLACK;

    private AbstractLayout layout = new PolarDendrogramLayout(LAYOUT_RADIUS);

    private GroupElement root = null;

    private GLU glu = new GLU();

    private Graph clusterGraph;


    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glColor3d(circleColor.getRed(), circleColor.getGreen(), circleColor.getBlue());
        GLUquadric glUquadric = glu.gluNewQuadric();

        glu.gluDisk(glUquadric, 0, LENS_RADIUS, 50, 50);

        root.drawElements(drawable);
    }

    public void setRoot(GroupElement root) {
        this.root = root;

        if (!root.isLayoutComputed()) {
            Graph groupGraph = new MyGraph();
            GraphUtils.extractSubgraph(clusterGraph, groupGraph, root.getObject());

            layout.setGraph(groupGraph);
            layout.setRoot(root);

            LOGGER.info("Group layout is not computed. Computing lens..");
            layout.compute();
            LOGGER.info("Done.");
        }
    }

    public void setGraph(Graph graph) {
        clusterGraph = graph;
    }

    public Color getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(Color circleColor) {
        this.circleColor = circleColor;
    }
}
