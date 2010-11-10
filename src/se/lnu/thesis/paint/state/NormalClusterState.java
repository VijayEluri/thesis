package se.lnu.thesis.paint.state;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.element.ElementType;
import se.lnu.thesis.paint.element.GroupingElement;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 18:04:51
 */
public class NormalClusterState extends FocusableState {

    public static final Logger LOGGER = Logger.getLogger(NormalClusterState.class);

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
