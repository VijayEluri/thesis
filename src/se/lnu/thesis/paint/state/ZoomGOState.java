package se.lnu.thesis.paint.state;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.GOController;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.element.Level;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 17:55:49
 */
public class ZoomGOState extends FocusableState {

    public static final Logger LOGGER = Logger.getLogger(ZoomGOState.class);

    private Level level;

    public ZoomGOState(GraphController controller, Level level) {
        setGraphController(controller);
        setContainer(level);
        this.level = level;
    }

    @Override
    public void leftMouseButtonClicked(Point cursor) {
        GOController goController = (GOController) getGraphController();

        if (getCurrent() == null) { // no  focused element than skip selection
            goController.unselect();
        } else {                    // there is focused element then select it
            goController.select(getCurrent());
        }
    }

    @Override
    public void rightMouseButtonClicked(Point cursor) {
        LOGGER.info("Zoom out to default view");

        getGraphController().setState(new NormalGOState(getGraphController()));

        unfocus();
        level.setFocused(false);
    }
}
