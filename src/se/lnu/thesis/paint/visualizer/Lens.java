package se.lnu.thesis.paint.visualizer;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.AbstractGraphElement;
import se.lnu.thesis.element.GroupElement;
import se.lnu.thesis.layout.AbstractLayout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.utils.GraphUtils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.07.2010
 */
public class Lens implements Drawable {

    public static final Logger LOGGER = Logger.getLogger(Lens.class);

    private static final double LENS_RADIUS = 0.4;
    private static final double LAYOUT_RADIUS = 0.3;

    private AbstractLayout layout = new PolarDendrogramLayout(LAYOUT_RADIUS);

    private GroupElement groupElement = null;

    private GLU glu = new GLU();

    private Graph clusterGraph;


    public void setGraph(Graph graph) {
        clusterGraph = graph;
    }

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glColor3d(1, 0, 0);
        GLUquadric glUquadric = glu.gluNewQuadric();

        glu.gluDisk(glUquadric, 0, LENS_RADIUS, 50, 50);

        for (AbstractGraphElement element : groupElement.getElements()) {
            element.draw(drawable);
        }

    }

    public void setGroupElement(GroupElement groupElement) {
        this.groupElement = groupElement;

        if (!groupElement.isInnerLayoutComputed()) {
            Graph subGraph = new MyGraph();
            GraphUtils.extractSubgraph(clusterGraph, subGraph, groupElement.getObject());

            layout.setGraph(subGraph);
            layout.setRoot(groupElement);

            LOGGER.info("Computing lens..");
            layout.compute();
            LOGGER.info("Done.");
        }
    }
}
