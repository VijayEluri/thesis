package se.lnu.thesis.paint.state;

import org.apache.log4j.Logger;
import se.lnu.thesis.layout.AbstractLayout;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.element.Level;

import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 17:55:49
 */
public class ZoomGOState extends GraphState {

    public static final Logger LOGGER = Logger.getLogger(ZoomGOState.class);

    private Level current;

    private AbstractLayout layout;

    public ZoomGOState(GraphController controller, Level level) {
        setGraphController(controller);
        this.current = level;
    }

    @Override
    protected void drawCurrentState(GLAutoDrawable drawable) {
        current.drawContent(drawable);
    }

    @Override
    public void leftMouseButtonClicked(Point point) {
        LOGGER.info("Zoom out to default view");

        getGraphController().setState(new NormalGOState(getGraphController()));

        current.setFocused(false);
    }
}
