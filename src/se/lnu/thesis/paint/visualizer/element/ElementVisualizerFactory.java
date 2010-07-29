package se.lnu.thesis.paint.visualizer.element;

import se.lnu.thesis.paint.visualizer.element.edge.LineEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.element.vertex.CircleVertexVisualizer;
import se.lnu.thesis.paint.visualizer.element.vertex.PointVertexVisualizer;
import se.lnu.thesis.paint.visualizer.element.vertex.RectVertexVisualizer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.07.2010
 * Time: 23:51:08
 */
public class ElementVisualizerFactory {

    private static final double MAX_GROUP_VERTEX_SIZE = 0.028;

    private static final int CIRCLE_VERTEX_VISUALIZER = -1;
    private static final int RECT_VERTEX_VISUALIZER = -2;
    private static final int POINT_VERTEX_VISUALIZER = -3;
    private static final int LINE_EDGE_VISUALIZER = -4;

    private static final Color DEFAULT_VERTEX_COLOR = new Color(100, 100, 100, 100);

    private static final ElementVisualizerFactory instance = new ElementVisualizerFactory();

    public static ElementVisualizerFactory getInstance() {
        return instance;
    }

    private Map<Object, AbstractGraphElementVisualizer> visualizers;

    private ElementVisualizerFactory() {
        init();
    }

    private void init() {
        visualizers = new HashMap<Object, AbstractGraphElementVisualizer>();

        visualizers.put(CIRCLE_VERTEX_VISUALIZER, new CircleVertexVisualizer(DEFAULT_VERTEX_COLOR));
        visualizers.put(RECT_VERTEX_VISUALIZER, new RectVertexVisualizer(DEFAULT_VERTEX_COLOR));
        visualizers.put(POINT_VERTEX_VISUALIZER, new PointVertexVisualizer(DEFAULT_VERTEX_COLOR));
        visualizers.put(LINE_EDGE_VISUALIZER, new LineEdgeVisualizer(DEFAULT_VERTEX_COLOR));
    }

    protected AbstractGraphElementVisualizer getVisualizer(int id) {
        return visualizers.get(id);
    }

    public AbstractGraphElementVisualizer getCircleVisualizer() {
        return getVisualizer(CIRCLE_VERTEX_VISUALIZER);
    }

    public AbstractGraphElementVisualizer getRectVisualizer() {
        return getVisualizer(RECT_VERTEX_VISUALIZER);
    }

    public AbstractGraphElementVisualizer getPointVisualizer() {
        return getVisualizer(POINT_VERTEX_VISUALIZER);
    }

    public GraphElementVisualizer getLineEdgeVisializer() {
        return getVisualizer(LINE_EDGE_VISUALIZER);
    }

    public GraphElementVisualizer getRectVisualizer(int maxGroupSize, int thisGroupSize) {

        if (visualizers.containsKey(thisGroupSize)) {
            return getVisualizer(thisGroupSize);
        } else {
            double size = (MAX_GROUP_VERTEX_SIZE / maxGroupSize) * thisGroupSize;
            visualizers.put(thisGroupSize, new RectVertexVisualizer(DEFAULT_VERTEX_COLOR, size));

            return getVisualizer(thisGroupSize);
        }
    }
}
