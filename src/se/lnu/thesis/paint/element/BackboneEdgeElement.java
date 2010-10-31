package se.lnu.thesis.paint.element;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 31.10.2010
 * Time: 15:40:49
 */
public class BackboneEdgeElement extends EdgeElement {

    private static final int BACKBONE_EDGE_DRAWING_ORDER = -1;

    public static BackboneEdgeElement init(Object o, Graph graph, Container root) {
        BackboneEdgeElement result = new BackboneEdgeElement();

        result.setObject(o);

        Object source = graph.getSource(o);
        result.setFrom(source);

        Object dest = graph.getDest(o);
        result.setTo(dest);

        Element element = root.getElementByObject(source);
        if (element != null) {
            result.setStartPosition(element.getPosition());
        } else {
            LOGGER.error("Cant find vertex for object '" + source + "'");
        }

        element = root.getElementByObject(dest);
        if (element != null) {
            result.setEndPosition(element.getPosition());
        } else {
            LOGGER.error("Cant find vertex for object '" + source + "'");
        }

        result.setVisualizer(ElementVisualizerFactory.getInstance().getLineEdgeVisializer());

        return result;
    }

    @Override
    public int getDrawingOrder() {
        return BACKBONE_EDGE_DRAWING_ORDER;
    }
}
