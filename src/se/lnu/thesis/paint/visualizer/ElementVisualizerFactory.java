package se.lnu.thesis.paint.visualizer;

import se.lnu.thesis.paint.visualizer.edge.LineEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.edge.PolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.vertex.CircleVertexVisualizer;
import se.lnu.thesis.paint.visualizer.vertex.PointVertexVisualizer;
import se.lnu.thesis.paint.visualizer.vertex.RectVertexVisualizer;
import se.lnu.thesis.paint.visualizer.vertex.TriangleVertexVisualizer;

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

    private static final double MAX_GROUP_VERTEX_SIZE = 0.02;

    private static final int CIRCLE_VERTEX_VISUALIZER = -1;
    private static final int RECT_VERTEX_VISUALIZER = -2;
    private static final int POINT_VERTEX_VISUALIZER = -3;
    private static final int SMALL_CIRCLE_VISUALIZER = -6;
    private static final int TRIANGLE_VISUALIZER = -7;

    private static final int LINE_EDGE_VISUALIZER = -101;
    private static final int POLAR_DENDROGRAM_EDGE_VISUALIZER = -102;

    private static final Color DEFAULT_ELEMENT_COLOR = new Color(100, 100, 100, 100);

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


        visualizers.put(SMALL_CIRCLE_VISUALIZER, new CircleVertexVisualizer(DEFAULT_ELEMENT_COLOR, 0.005));
        visualizers.put(TRIANGLE_VISUALIZER, new TriangleVertexVisualizer(DEFAULT_ELEMENT_COLOR));
        visualizers.put(CIRCLE_VERTEX_VISUALIZER, new CircleVertexVisualizer(DEFAULT_ELEMENT_COLOR));
        visualizers.put(RECT_VERTEX_VISUALIZER, new RectVertexVisualizer(DEFAULT_ELEMENT_COLOR));
        visualizers.put(POINT_VERTEX_VISUALIZER, new PointVertexVisualizer(DEFAULT_ELEMENT_COLOR));

        visualizers.put(LINE_EDGE_VISUALIZER, new LineEdgeVisualizer(DEFAULT_ELEMENT_COLOR));
        visualizers.put(POLAR_DENDROGRAM_EDGE_VISUALIZER, new PolarDendrogramEdgeVisualizer(DEFAULT_ELEMENT_COLOR));
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

    public GraphElementVisualizer getPolarDendrogramEdgeVisializer() {
        return getVisualizer(POLAR_DENDROGRAM_EDGE_VISUALIZER);
    }

    public GraphElementVisualizer getRectVisualizer(int maxGroupSize, int thisGroupSize) {

        if (visualizers.containsKey(thisGroupSize)) {
            return getVisualizer(thisGroupSize);
        } else {
            double size = (MAX_GROUP_VERTEX_SIZE / maxGroupSize) * thisGroupSize;
            visualizers.put(thisGroupSize, new RectVertexVisualizer(DEFAULT_ELEMENT_COLOR, size));

            return getVisualizer(thisGroupSize);
        }
    }

    public AbstractGraphElementVisualizer getSmallCircleVisualizer() {
        return getVisualizer(SMALL_CIRCLE_VISUALIZER);
    }

    public AbstractGraphElementVisualizer getTriangleVisualizer() {
        return getVisualizer(TRIANGLE_VISUALIZER);
    }
}
