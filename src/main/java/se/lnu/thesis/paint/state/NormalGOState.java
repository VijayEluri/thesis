package se.lnu.thesis.paint.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.event.InfoStatusBarTextEvent;
import se.lnu.thesis.paint.controller.GraphController;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 16:10:16
 */
public class NormalGOState extends FocusableState {

    public static final Logger LOGGER = LoggerFactory.getLogger(NormalGOState.class);

    protected NormalGOState() {

    }

    public NormalGOState(GraphController controller) {
        setGraphController(controller);
        setContainer(controller.getRoot());
    }

    @Override
    protected void focus(int id, Container container) {
        Element element = container.getElementById(id);

        if (element != null) {
            setCurrent(element);
            getCurrent().setFocused(true);

            getGraphController().publish(new InfoStatusBarTextEvent(this, "Level: " + element.getObject()));

            LOGGER.debug("Focused level " + getCurrent());
        }
    }

    @Override
    public void mouseExited() {
        super.mouseExited();
        unfocus();
    }

    /**
     * Left mouse button been clicked
     *
     * @param point Cursor positon
     */
    public void leftMouseButtonClicked(Point point) {
        if (getCurrent() != null) {
            LOGGER.info("Zooming... Current level is " + getCurrent());

            getGraphController().setState(new ZoomGOState(getGraphController(), (Level) getCurrent()));
            unfocus();
        }
    }


}
