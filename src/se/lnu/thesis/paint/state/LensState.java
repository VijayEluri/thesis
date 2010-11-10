package se.lnu.thesis.paint.state;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.Scene;
import se.lnu.thesis.paint.GraphController;
import se.lnu.thesis.paint.Lens;
import se.lnu.thesis.paint.element.GroupingElement;

import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 18:46:11
 */
public class LensState extends NormalClusterState {

    private GroupingElement selectedElement;

    private Lens lens;

    protected LensState() {

    }

    public LensState(GraphController controller, GroupingElement element) {
        super(controller);

        lens = new Lens();
        lens.setGraph(controller.getGraph());

        select(element);
    }

    @Override
    protected void drawCurrentState(GLAutoDrawable drawable) {
        if (getCursor() != null) {
            focusing(drawable, selectedElement);
        }

        getContainer().draw(drawable);

        lens.draw(drawable);
    }


    protected void select(GroupingElement element) {
        String label = getGraphController().getGraph().getLabel(element.getObject());

        LOGGER.info("Selected vertex " + element.getObject() + " [" + label + "]");

        Scene.getInstance().getMainWindow().setStatusBarText("Selected vertex " + label);

        this.selectedElement = element;
        this.selectedElement.setSelected(true);

        lens.setRoot(selectedElement);

        Graph subGraph = getGraphController().getSubGraph();
        if (subGraph != null) {
            selectedElement.setHighlighted(subGraph.getVertices());
        }
    }

    protected void unselect() {
        if (selectedElement != null) {
            selectedElement.setSelected(false);
        }

        selectedElement = null;
    }

    @Override
    public void leftMouseButtonClicked(Point point) {
        LOGGER.info("Hide lens");
        unfocus();
        unselect();
        getGraphController().setState(new NormalClusterState(getGraphController()));
    }
}
