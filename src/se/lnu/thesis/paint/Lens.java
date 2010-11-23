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
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.geom.Point2D;

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

    public static final Point2D DEFAULT_DISTANCE = new Point2D.Double(0.0, 0.0);

    protected Color circleColor = DEFAULT_CIRCLE_COLOR;
    private double circleAlfa = DEFAULT_CIRCLE_ALFA;

    private double lensRadius = LENS_RADIUS;
    private double layoutRadius = LAYOUT_RADIUS;

    private AbstractLayout layout = new PolarDendrogramLayout(LAYOUT_RADIUS);

    private GroupingElement root = null;

    private Graph clusterGraph;

    private Point2D center;

    private Point2D distance = DEFAULT_DISTANCE;

    private Point start;
    private Point end;


    public Lens() {

    }

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glPushMatrix();
        gl.glTranslated(center.getX(), center.getY(), 0.0);

        DrawingUtils.colord(gl, circleColor, circleAlfa);

        gl.glPushName(ID);
        DrawingUtils.circle(gl, LENS_RADIUS, LENS_SEGMENTS);
        gl.glPopName();

        root.drawContent(drawable);

        gl.glPopMatrix();
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

        setLensCenterPosition(root.getPosition());
    }

    protected void setLensCenterPosition(Point2D p) {
        center = new Point2D.Double();

        // is it posible to set lens on the right from the vertex
        if (p.getX() + distance.getX() + (lensRadius * 2) <= 1.0) {
            center.setLocation(p.getX() + (distance.getX() + lensRadius), 0);
        } else {
            center.setLocation(p.getX() - (distance.getX() + lensRadius), 0);
        }

        // is it posible to set lens on the bottom from the vertex
        if (p.getY() - distance.getY() - (lensRadius * 2) >= -1.0) {
            center.setLocation(center.getX(), p.getY() - (distance.getY() + lensRadius));
        } else {
            center.setLocation(center.getX(), p.getY() + (distance.getY() + lensRadius));
        }
    }

    public void move(GL gl, GLU glu) {
        if (start != null && end != null) {
            double[] start = DrawingUtils.window2world(gl, glu, this.start);
            double[] end = DrawingUtils.window2world(gl, glu, this.end);

            double moveX = end[0] - start[0];
            double moveY = end[1] - start[1];

            center.setLocation(center.getX() + moveX, center.getY() + moveY);
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

    public double getLensRadius() {
        return lensRadius;
    }

    public void setLensRadius(double lensRadius) {
        this.lensRadius = lensRadius;
    }

    public double getLayoutRadius() {
        return layoutRadius;
    }

    public void setLayoutRadius(double layoutRadius) {
        this.layoutRadius = layoutRadius;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
