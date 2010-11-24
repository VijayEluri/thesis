package se.lnu.thesis.paint.state;

import org.apache.log4j.Logger;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.paint.GraphController;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 16:10:16
 */
public class NormalGOState extends FocusableState {

    public static final Logger LOGGER = Logger.getLogger(NormalGOState.class);

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

            LOGGER.debug("Focused level " + getCurrent());
        }
    }

    @Override
    public void mouseExited() {
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
