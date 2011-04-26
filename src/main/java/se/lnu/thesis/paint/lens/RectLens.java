package se.lnu.thesis.paint.lens;

import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.layout.HVTreeLayout;
import se.lnu.thesis.utils.DrawingUtils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.07.2010
 */
public class RectLens extends Lens {

    public RectLens() {
        HVTreeLayout hvTreeLayout = new HVTreeLayout();
        setLayout(hvTreeLayout);
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glPushMatrix();
        gl.glTranslated(position.getX(), position.getY(), 0.0);

        gl.glColor4f(
                getLensColor().getRed(),
                getLensColor().getGreen(),
                getLensColor().getBlue(),
                getLensColor().getAlfa());

        gl.glPushName(ID);
        DrawingUtils.rect(gl, -SPACE_BORDER, SPACE_BORDER, getLensDimension().getX(), -getLensDimension().getY());
        gl.glPopName();

        getRoot().drawContent(drawable);

        drawTooltip(drawable);

        gl.glPopMatrix();
    }

    @Override
    public void setRoot(GroupingElement root) {
        this.root = root;

        if (!root.isLayoutComputed()) {
            computeGroupingElementLayout(root);
        }

        Point2D.Double d = new Point2D.Double(root.getDimension().getX() + 2 * SPACE_BORDER, root.getDimension().getY() + 2 * SPACE_BORDER);
        setLensDimension(d);

        setLensNearSelectedNodePosition(root.getPosition());
    }

    /**
     * Set lens near the selected vertex.
     *
     * @param p Position of the selected vertex
     */
    @Override
    public void setLensNearSelectedNodePosition(Point2D p) {
        position = new Point2D.Double();

        // is it possible to set lens on the right from the vertex
        if (p.getX() + distanceToBorders.getX() + lensDimension.getX() <= 1.0) {
            position.setLocation(p.getX() + SPACE_BORDER, 0);
        } else {
            position.setLocation(p.getX() - (distanceToBorders.getX() + lensDimension.getX()), 0);
        }

        // is it possible to set lens on the bottom from the vertex
        if (p.getY() + distanceToBorders.getY() + lensDimension.getY() >= -1.0) {
            position.setLocation(position.getX(), p.getY() - SPACE_BORDER);
        } else {
            position.setLocation(position.getX(), p.getY() + (distanceToBorders.getY() - lensDimension.getY()));
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
        if (position.getX() + moveX + lensDimension.getX() - SPACE_BORDER >= 1.0) {
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
        if (position.getX() + moveX - SPACE_BORDER <= -1.0) {
            x = -1.0 + SPACE_BORDER;
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
        if (position.getY() + moveY + SPACE_BORDER >= 1.0) {
            y = 1.0 - SPACE_BORDER;
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
        if (position.getY() - moveY - lensDimension.getY() <= -1.0) {
            y = -1.0 + lensDimension.getY() - SPACE_BORDER;
        } else {
            y = position.getY() + moveY;
        }
        return y;
    }
}
