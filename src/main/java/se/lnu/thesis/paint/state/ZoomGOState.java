package se.lnu.thesis.paint.state;

import com.jogamp.opengl.util.gl2.GLUT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.paint.controller.GOController;
import se.lnu.thesis.paint.controller.GraphController;
import se.lnu.thesis.properties.PropertiesHolder;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;
import java.awt.geom.Point2D;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 17:55:49
 * <p/>
 * <p/>
 * Gene Ontology zooming levels state
 */
public class ZoomGOState extends FocusableState {

    public static final Logger LOGGER = LoggerFactory.getLogger(ZoomGOState.class);

    private List<Level> levels;

    public ZoomGOState(GraphController controller, Level level) {
        setGraphController(controller);

        level.setFocused(false);

        levels = new LinkedList<Level>();
        int topLevelIndex = ((GOGraphContainer) controller.getRoot()).getZoomedLevels(level, levels);

        controller.getScene().getMainWindow().setScrollBarValue(topLevelIndex);
        controller.getScene().getMainWindow().showScrollBar();
    }

    @Override
    protected void drawCurrentState(GLAutoDrawable drawable) {
        if (getCursor() != null) {
            focusing(drawable);
        }

        drawTopLevel(drawable);

        drawMidLevel(drawable);

        drawBottomLevel(drawable);
    }

    private void drawTopLevel(GLAutoDrawable drawable) {
        Level level = levels.get(0);
        shiftAndDrawLevel(drawable, level, level.getDimension().getY());
    }

    private void drawMidLevel(GLAutoDrawable drawable) {
        Level level = levels.get(1);
        shiftAndDrawLevel(drawable, level, 0);
    }

    private void drawBottomLevel(GLAutoDrawable drawable) {
        Level level = levels.get(2);
        shiftAndDrawLevel(drawable, level, -level.getDimension().getY());
    }

    private void shiftAndDrawLevel(GLAutoDrawable drawable, Level level, double shiftY) {
        setGl(drawable);

        gl2().glPushMatrix();
        gl2().glTranslated(0.0, shiftY, 0.0);
        level.drawContent(drawable);

        if (getCurrent() != null) {
            if (level.has(getCurrent().getObject())) {
                drawTooltip(drawable, getCurrent());
            }
        }
        gl2().glPopMatrix();
    }

    public void drawTooltip(GLAutoDrawable drawable, Element element) {
        setGl(drawable);

        if (element.isFocused()) {
            MyColor color = PropertiesHolder.getInstance().getColorSchema().getVerticesTooltips();
            gl2().glColor3f(color.getRed(), color.getGreen(), color.getBlue());

            Point2D p = element.getPosition();
            gl2().glRasterPos2d(p.getX(), p.getY());
            glut().glutBitmapString(GLUT.BITMAP_8_BY_13, element.getTooltip());
        }
    }

    protected void focusing(GLAutoDrawable drawable) {
        setGl(drawable);

        IntBuffer selectBuffer = ByteBuffer.allocate(BUFSIZE * Integer.BYTES).asIntBuffer();

        int viewport[] = new int[4];

        gl2().glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);

        gl2().glSelectBuffer(BUFSIZE, selectBuffer);
        gl2().glRenderMode(GL2.GL_SELECT);

        gl2().glInitNames();

        gl2().glMatrixMode(GL2.GL_PROJECTION);
        gl2().glPushMatrix();
        gl2().glLoadIdentity();

        glu().gluPickMatrix(getCursor().getX(), (viewport[3] - getCursor().getY()), CURSOR_X_SIZE, CURSOR_Y_SIZE, viewport, 0);


        drawTopLevel(drawable);
        drawMidLevel(drawable);
        drawBottomLevel(drawable);


        gl2().glMatrixMode(GL2.GL_PROJECTION);
        gl2().glPopMatrix();
        gl2().glFlush();


        int hits = gl2().glRenderMode(GL2.GL_RENDER);

        LOGGER.debug("There are " + hits + " ids found.");

        if (hits > 0) { // founded something?
            int id = selectBuffer.get(hits * 4 - 1); // get last id in the stack

            LOGGER.debug("Looking for id '" + id + "'");

            unfocus();
            for (Level level : levels) {
                focus(id, level);
            }
        } else {
            unfocus();
        }

    }

    @Override
    public void leftMouseButtonClicked(Point cursor) {
        GOController goController = (GOController) getGraphController();

        getGraphController().getScene().getMainWindow().setCursor(Cursor.WAIT_CURSOR);

        if (getCurrent() == null) { // no  focused element than skip selection
            goController.unselect();

            getGraphController().getScene().getExtractor().reset();
        } else {                    // there is focused element then select it
            goController.select(getCurrent());

            getGraphController().getScene().getExtractor().extract(getGraphController().getScene().getGoGraph(), getGraphController().getScene().getClusterGraph(), getCurrent().getObject());
        }

        getGraphController().getScene().getGeneListDialog().notifyObservers(); // TODO class knows too mach - decouple this kind behavior

        getGraphController().getScene().getMainWindow().setCursor(Cursor.DEFAULT_CURSOR);
    }

    @Override
    public void rightMouseButtonClicked(Point cursor) {
        LOGGER.info("Zoom out to default view");

        getGraphController().getScene().getMainWindow().hideScrollBar();
        getGraphController().setState(new NormalGOState(getGraphController()));
    }

    public void scrollBarValueChanged(int index) {
        ((GOGraphContainer) getGraphController().getRoot()).getZoomedLevels(index, levels);
    }
}
