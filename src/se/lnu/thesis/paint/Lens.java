package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.layout.AbstractLayout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.element.GroupingElement;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.GraphUtils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.07.2010
 */
public class Lens implements Drawable {

    public static final Logger LOGGER = Logger.getLogger(Lens.class);

    public static final int ID = -100;

    public static final int LENS_SEGMENTS = 20;
    public static final double LENS_RADIUS = 0.35;

    public static final double LAYOUT_RADIUS = 0.3;

    public static final Color DEFAULT_CIRCLE_COLOR = Color.BLACK;
    public static final double DEFAULT_CIRCLE_ALFA = 0.5;

    protected Color circleColor = DEFAULT_CIRCLE_COLOR;
    private double circleAlfa = DEFAULT_CIRCLE_ALFA;

    private AbstractLayout layout = new PolarDendrogramLayout(LAYOUT_RADIUS);

    private GroupingElement root = null;

    private Graph clusterGraph;


    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        DrawingUtils.colord(gl, circleColor, circleAlfa);

        gl.glPushName(ID);
        DrawingUtils.circle(gl, LENS_RADIUS, LENS_SEGMENTS);

        root.drawContent(drawable);

        gl.glPopName();
    }

    public void setRoot(GroupingElement root) {
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

    public double getCircleAlfa() {
        return circleAlfa;
    }

    public void setCircleAlfa(double circleAlfa) {
        this.circleAlfa = circleAlfa;
    }
}
