package se.lnu.thesis.paint.lens;

import com.sun.opengl.util.GLUT;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.properties.PropertiesHolder;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.07.2010
 */
public class RadialLens extends Lens {

    public static final double LAYOUT_RADIUS = 0.3;

    public static final int LENS_SEGMENTS = 20;

    private double layoutRadius = LAYOUT_RADIUS;


    public RadialLens() {
        final double lensRadius = LAYOUT_RADIUS + SPACE_BORDER;
        setLensDimension(new Point2D.Double(lensRadius, lensRadius));

        setLayoutRadius(LAYOUT_RADIUS);
        setLayout(new PolarDendrogramLayout(getLayoutRadius()));
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glPushMatrix();
        gl.glTranslated(position.getX(), position.getY(), 0.0);

        gl.glColor4f(getLensColor().getRed(),
                getLensColor().getGreen(),
                getLensColor().getBlue(),
                getLensColor().getAlfa());

        gl.glPushName(ID);
        DrawingUtils.circle(gl, getLensDimension().getX(), LENS_SEGMENTS);
        gl.glPopName();

        getRoot().drawContent(drawable);

        drawTooltip(drawable);

        gl.glPopMatrix();
    }

    /**
     * Set lens near the selected vertex.
     *
     * @param p Position of the selected vertex
     */
    public void setLensNearSelectedNodePosition(Point2D p) {
        position = new Point2D.Double();

        // is it possible to set lens on the right from the vertex
        if (p.getX() + distanceToBorders.getX() + (lensDimension.getX() * 2) <= 1.0) {
            position.setLocation(p.getX() + (distanceToBorders.getX() + lensDimension.getX()), 0);
        } else {
            position.setLocation(p.getX() - (distanceToBorders.getX() + lensDimension.getX()), 0);
        }

        // is it possible to set lens on the bottom from the vertex
        if (p.getY() - distanceToBorders.getY() - (lensDimension.getY() * 2) >= -1.0) {
            position.setLocation(position.getX(), p.getY() - (distanceToBorders.getY() + lensDimension.getY()));
        } else {
            position.setLocation(position.getX(), p.getY() + (distanceToBorders.getY() + lensDimension.getY()));
        }
    }


    /**
     * Check if intersection with right border of the view port accured.
     *
     * @param moveX X value of moving vector.
     * @return Return computed X coordinate.
     */
    @Override
    protected double checkRightBorder(double moveX) {
        double x;

        if (position.getX() + moveX + lensDimension.getX() > 1.0) {
            x = 1.0 - lensDimension.getX();
        } else {
            x = position.getX() + moveX;
        }

        return x;
    }

    /**
     * Check if intersection with left border of the view port accured.
     *
     * @param moveX X value of moving vector.
     * @return Return computed X coordinate.
     */
    @Override
    protected double checkLeftBorder(double moveX) {
        double x;
        if (position.getX() + moveX - lensDimension.getX() < -1.0) {
            x = -1.0 + lensDimension.getX();
        } else {
            x = position.getX() + moveX;
        }
        return x;
    }

    /**
     * Check if intersection with top border of the view port accured.
     *
     * @param moveY X value of moving vector.
     * @return Return computed Y coordinate.
     */
    @Override
    protected double checkTopBorder(double moveY) {
        double y;
        if (position.getY() + moveY + lensDimension.getY() > 1.0) {
            y = 1.0 - lensDimension.getY();
        } else {
            y = position.getY() + moveY;
        }
        return y;
    }

    /**
     * Check if intersection with left border of the view port accured.
     *
     * @param moveY Y value of moving vector.
     * @return Return computed Y coordinate.
     */
    @Override
    protected double checkBottomBorder(double moveY) {
        double y;
        if (position.getY() - moveY - lensDimension.getY() < -1.0) {
            y = -1.0 + lensDimension.getY();
        } else {
            y = position.getY() + moveY;
        }
        return y;
    }

    public double getLayoutRadius() {
        return layoutRadius;
    }

    public void setLayoutRadius(double layoutRadius) {
        this.layoutRadius = layoutRadius;
    }

}
