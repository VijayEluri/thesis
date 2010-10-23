package se.lnu.thesis.paint.element;

import com.google.common.collect.ImmutableSet;
import se.lnu.thesis.utils.IdUtils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:12:17
 */
public class LevelElement extends DimensionalContainer {

    public static LevelElement init(Object o, Collection<Object> objects) {
        LevelElement result = new LevelElement();

        result.setObject(o);

        result.setId(IdUtils.next());

        result.preview = DimensionalContainer.init(o);

        result.objects = ImmutableSet.copyOf(objects);

        return result;
    }

    private DimensionalContainer preview;

    protected LevelElement() {
        super();
    }


    @Override
    public void draw(GLAutoDrawable drawable) {
        if (isDrawed()) {
            drawHorizontalBorders(drawable, preview);

            preview.drawContent(drawable);
        }
    }

    @Override
    public void drawContent(GLAutoDrawable drawable) {
        super.draw(drawable);
    }

    protected void drawHorizontalBorders(GLAutoDrawable drawable, DimensionalContainer container) {
        int length = 2; // 2% of the whole horizontal length

        Point2D p = container.getPosition();
        Point2D d = container.getDimension();

        double x_length = container.getDimension().getX() * (length / 100.0);

        GL gl = drawable.getGL();

        gl.glLineWidth(1.5f);
        gl.glColor3d(1.0, 1.0, 0.0); // border color
        gl.glBegin(GL.GL_LINES);

        // left up
        gl.glVertex2d(p.getX(), p.getY());
        gl.glVertex2d(p.getX() + x_length, p.getY());

        // right up
        gl.glVertex2d((p.getX() + d.getX()) - x_length, p.getY());
        gl.glVertex2d(p.getX() + d.getX(), p.getY());

        // left down
        gl.glVertex2d(p.getX(), p.getY() - d.getY());
        gl.glVertex2d(p.getX() + x_length, p.getY() - d.getY());

        // right down
        gl.glVertex2d((p.getX() + d.getX()) - x_length, p.getY() - d.getY());
        gl.glVertex2d(p.getX() + d.getX(), p.getY() - d.getY());

        gl.glEnd();
    }

    public DimensionalContainer getPreview() {
        return preview;
    }

    @Override
    public void setHighlighted(Collection objects) {
        preview.setHighlighted(objects);
        super.setHighlighted(objects);
    }

    @Override
    public void resetHighlighting() {
        preview.resetHighlighting();
        super.resetHighlighting();
    }
}
