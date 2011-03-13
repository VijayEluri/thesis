package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.layout.Layout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.MyColor;
import se.lnu.thesis.properties.PropertiesHolder;

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

    public static final Point2D DEFAULT_DISTANCE = new Point2D.Double(0.0, 0.0);

    protected MyColor circleColor = PropertiesHolder.getInstance().getColorSchema().getLens();

    private double lensRadius = LENS_RADIUS;
    private double layoutRadius = LAYOUT_RADIUS;

    private Layout layout = new PolarDendrogramLayout(LAYOUT_RADIUS);

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

        gl.glColor4f(getCircleColor().getRed(),
                getCircleColor().getGreen(),
                getCircleColor().getBlue(),
                getCircleColor().getAlfa());

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

        setLensNearSelectedNodePosition(root.getPosition());
    }

    protected void setLensNearSelectedNodePosition(Point2D p) {
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

    protected void keepLensInside(double moveX, double moveY) {
        double x, y;

        if (moveX > 0) { // moving right?

            // check right border
            if (center.getX() + moveX + lensRadius > 1.0) {
                x = 1.0 - lensRadius;
            } else {
                x = center.getX() + moveX;
            }

        } else { // moving left

            // check left border
            if (center.getX() + moveX - lensRadius < -1.0) {
                x = -1.0 + lensRadius;
            } else {
                x = center.getX() + moveX;
            }

        }

        if (moveY > 0) { // moving up

            // check top border
            if (center.getY() + moveY + lensRadius > 1.0) {
                y = 1.0 - lensRadius;
            } else {
                y = center.getY() + moveY;
            }

        } else { // moving down

            // check bottom border
            if (center.getY() - moveY - lensRadius < -1.0) {
                y = -1.0 + lensRadius;
            } else {
                y = center.getY() + moveY;
            }

        }

        center.setLocation(x, y);

    }

    public void move(GL gl, GLU glu) {
        if (start != null && end != null) {
            double[] start = DrawingUtils.window2world(gl, glu, this.start);
            double[] end = DrawingUtils.window2world(gl, glu, this.end);

            double moveX = end[0] - start[0];
            double moveY = end[1] - start[1];

            keepLensInside(moveX, moveY);

            start = null;
        }
    }

    public void setGraph(Graph graph) {
        clusterGraph = graph;
    }

    public MyColor getCircleColor() {
        return circleColor;
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
