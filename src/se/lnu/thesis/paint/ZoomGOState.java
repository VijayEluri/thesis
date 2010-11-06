package se.lnu.thesis.paint;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.element.Element;

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

    private Element current;

    public ZoomGOState(GraphController controller, Element current) {
        setGraphController(controller);
        this.current = current;
    }

    protected void drawCurrentState(GLAutoDrawable drawable) {

    }

    @Override
    public void leftMouseButtonClicked(Point point) {
        LOGGER.info("Zoom out to default view");
        getGraphController().setState(new NormalGOState(getGraphController()));
    }
}
