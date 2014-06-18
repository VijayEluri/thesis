package se.lnu.thesis.paint.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.element.ElementType;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.paint.controller.GraphController;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 18:04:51
 */
public class NormalClusterState extends FocusableState {

    public static final Logger LOGGER = LoggerFactory.getLogger(NormalClusterState.class);

    protected NormalClusterState() {
    }

    public NormalClusterState(GraphController controller) {
        setGraphController(controller);
        setContainer(controller.getRoot());
    }

    @Override
    public void leftMouseButtonClicked(Point point) {
        if (getCurrent() != null && getCurrent().getType() == ElementType.CONTAINER) {
            LOGGER.info("Showing lens..");

            getGraphController().setState(new LensState(getGraphController(), (GroupingElement) getCurrent()));
            unfocus();
        }
    }


}
