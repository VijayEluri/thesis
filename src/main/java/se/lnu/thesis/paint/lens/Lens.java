package se.lnu.thesis.paint.lens;


import com.jogamp.opengl.util.gl2.GLUT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.layout.Layout;
import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.properties.PropertiesHolder;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 27.03.11
 * Time: 00:32
 * <p/>
 * Base class for lens to view grouped elements in the cluster grpah.
 */
public abstract class Lens implements Drawable {

    public static final Logger LOGGER = LoggerFactory.getLogger(RadialLens.class);

    public static final int ID = -100;

    public static final Point2D DEFAULT_DISTANCE_TO_VIEWPORT_BORDERS = new Point2D.Double(0.0, 0.0);
    public static final double SPACE_BORDER = 0.05;

    private MyColor lensColor = PropertiesHolder.getInstance().getColorSchema().getLens();

    private Layout layout;

    protected GroupingElement root = null;

    protected MyGraph clusterGraph;

    protected Point2D position;
    protected Point2D lensDimension;
    protected Point2D distanceToBorders = DEFAULT_DISTANCE_TO_VIEWPORT_BORDERS;

    private Point lensMovingCursorStartPosition;
    private Point lensMovingCursorEndPosition;
    protected GLUT glut;

    public abstract void draw(GLAutoDrawable drawable);

    public MyColor getLensColor() {
        return lensColor;
    }

    public Point2D getLensDimension() {
        return lensDimension;
    }

    public void setLensDimension(Point2D lensDimension) {
        this.lensDimension = lensDimension;
    }

    public Point getStart() {
        return lensMovingCursorStartPosition;
    }

    public void setStart(Point start) {
        this.lensMovingCursorStartPosition = start;
    }

    public Point getEnd() {
        return lensMovingCursorEndPosition;
    }

    public void setEnd(Point end) {
        this.lensMovingCursorEndPosition = end;
    }

    public void setRoot(GroupingElement root) {
        this.root = root;

        if (!root.isLayoutComputed()) {
            computeGroupingElementLayout(root);
        }

        setLensNearSelectedNodePosition(root.getPosition());
    }

    protected void computeGroupingElementLayout(GroupingElement root) {
        MyGraph groupGraph = new MyGraph();
        GraphUtils.extractSubgraph(clusterGraph, groupGraph, root.getObject());

        layout.setGraph(groupGraph);
        layout.setRoot(root);

        LOGGER.info("Group layout is not computed. Computing lens..");
        layout.compute();
        LOGGER.info("Done.");
    }

    public GroupingElement getRoot() {
        return this.root;
    }

    /**
     * Set lens near the selected vertex.
     *
     * @param p Position of the selected vertex
     */
    public abstract void setLensNearSelectedNodePosition(Point2D p);

    /**
     * Cursor moving event accured.
     *
     * @param gl  OpenGL drawing context
     * @param glu GLU library. Used to convert coordinates from window coordinate system to OpenGL.
     */
    public void move(GL2 gl, GLU glu) {
        if (lensMovingCursorStartPosition != null && lensMovingCursorEndPosition != null) {
            double[] start = DrawingUtils.window2world(gl, glu, this.lensMovingCursorStartPosition);
            double[] end = DrawingUtils.window2world(gl, glu, this.lensMovingCursorEndPosition);

            double moveX = end[0] - start[0];
            double moveY = end[1] - start[1];

            keepLensInsideWindow(moveX, moveY);

            lensMovingCursorStartPosition = null;
        }
    }

    /**
     * Check that lens should stay inside of the view port
     *
     * @param moveX X value of the moving vector in OpenGL system coordinates converted from cursor coordinates
     * @param moveY Y value of the moving vector in OpenGL system coordinates converted from cursor coordinates
     */
    public void keepLensInsideWindow(double moveX, double moveY) {
        double x, y;

        if (moveX > 0) { // moving right?
            x = checkRightBorder(moveX);

        } else { // moving left
            x = checkLeftBorder(moveX);
        }

        if (moveY > 0) { // moving up
            y = checkTopBorder(moveY);

        } else { // moving down
            y = checkBottomBorder(moveY);
        }

        position.setLocation(x, y);

    }

    public void setGraph(MyGraph graph) {
        clusterGraph = graph;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    /**
     * Check if intersection with left border of the view port accured.
     *
     * @param moveX X value of moving vector.
     * @return Return computed X coordinate.
     */
    protected abstract double checkLeftBorder(double moveX);

    /**
     * Check if intersection with right border of the view port accured.
     *
     * @param moveX X value of moving vector.
     * @return Return computed X coordinate.
     */
    protected abstract double checkRightBorder(double moveX);

    /**
     * Check if intersection with left border of the view port accured.
     *
     * @param moveY Y value of moving vector.
     * @return Return computed Y coordinate.
     */
    protected abstract double checkBottomBorder(double moveY);

    /**
     * Check if intersection with top border of the view port accured.
     *
     * @param moveY X value of moving vector.
     * @return Return computed Y coordinate.
     */
    protected abstract double checkTopBorder(double moveY);

    public GLUT glut() {
        if (glut == null) {
            glut = new GLUT();
        }

        return glut;
    }

    /**
     * Find in the goor container focused element and draw tooltip for it
     *
     * @param drawable OpenGL drawing context
     */
    public void drawTooltip(GLAutoDrawable drawable) {  // TODO move it to separate class
        GL2 gl = (GL2) drawable.getGL();

        for (Element element : getRoot()) {
            if (element.isFocused()) {
                MyColor color = PropertiesHolder.getInstance().getColorSchema().getVerticesTooltips();
                gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());

                Point2D p = element.getPosition();
                gl.glRasterPos2d(p.getX(), p.getY());
                glut().glutBitmapString(GLUT.BITMAP_8_BY_13, element.getTooltip());
            }
        }
    }
}
