package se.lnu.thesis.paint.visualizer;

import com.google.common.collect.ImmutableMap;
import se.lnu.thesis.paint.visualizer.edge.LineEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.edge.PolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.vertex.*;

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

    private static final Color DEFAULT_ELEMENT_COLOR = new Color(100, 100, 100, 100); // deafult color is grey

    private static final int CIRCLE_VERTEX_VISUALIZER = 1;
    private static final int SMALL_CIRCLE_VISUALIZER = 2;
    private static final int RECT_VERTEX_VISUALIZER = 3;
    private static final int POINT_VERTEX_VISUALIZER = 4;

    private static final int POINT_GO_LEAF_VERTEX_VISUALIZER = 5;
    private static final int POINT_GO_NODE_VERTEX_VISUALIZER = 6;

    private static final int LINE_EDGE_VISUALIZER = 101;
    private static final int POLAR_DENDROGRAM_EDGE_VISUALIZER = 102;

    private static final int LEVEL_VISUALIZER = 200;

    private static final ElementVisualizerFactory instance = new ElementVisualizerFactory();


    public static ElementVisualizerFactory getInstance() {
        return instance;
    }


    private ImmutableMap<Object, ElementVisualizer> visualizers = new ImmutableMap.Builder<Object, ElementVisualizer>()
            .put(CIRCLE_VERTEX_VISUALIZER, new CircleVertexVisualizer(DEFAULT_ELEMENT_COLOR))
            .put(SMALL_CIRCLE_VISUALIZER, new CircleVertexVisualizer(DEFAULT_ELEMENT_COLOR, 0.005))

            .put(RECT_VERTEX_VISUALIZER, new RectVertexVisualizer(DEFAULT_ELEMENT_COLOR))

            .put(POINT_VERTEX_VISUALIZER, new PointVertexVisualizer(DEFAULT_ELEMENT_COLOR))
            .put(POINT_GO_LEAF_VERTEX_VISUALIZER, new GOPointVertexVisualizer(Color.RED))
            .put(POINT_GO_NODE_VERTEX_VISUALIZER, new GOPointVertexVisualizer(Color.GREEN))

            .put(LINE_EDGE_VISUALIZER, new LineEdgeVisualizer(DEFAULT_ELEMENT_COLOR))
            .put(POLAR_DENDROGRAM_EDGE_VISUALIZER, new PolarDendrogramEdgeVisualizer(DEFAULT_ELEMENT_COLOR))

            .put(LEVEL_VISUALIZER, new LevelVisualizer())

            .build();

    private Map<Object, AbstractElementVisualizer> rectVisualizers = new HashMap<Object, AbstractElementVisualizer>();

    private ElementVisualizerFactory() {
    }


    public CircleVertexVisualizer getCircleVisualizer() {
        return (CircleVertexVisualizer) visualizers.get(CIRCLE_VERTEX_VISUALIZER);
    }

    public GOPointVertexVisualizer getGOLeafPointVisualizer() {
        return (GOPointVertexVisualizer) visualizers.get(POINT_GO_LEAF_VERTEX_VISUALIZER);
    }

    public GOPointVertexVisualizer getGONodePointVisualizer() {
        return (GOPointVertexVisualizer) visualizers.get(POINT_GO_NODE_VERTEX_VISUALIZER);
    }

    public PointVertexVisualizer getPointVisualizer() {
        return (PointVertexVisualizer) visualizers.get(POINT_VERTEX_VISUALIZER);
    }

    public LineEdgeVisualizer getLineEdgeVisializer() {
        return (LineEdgeVisualizer) visualizers.get(LINE_EDGE_VISUALIZER);
    }

    public PolarDendrogramEdgeVisualizer getPolarDendrogramEdgeVisializer() {
        return (PolarDendrogramEdgeVisualizer) visualizers.get(POLAR_DENDROGRAM_EDGE_VISUALIZER);
    }

    public CircleVertexVisualizer getSmallCircleVisualizer() {
        return (CircleVertexVisualizer) visualizers.get(SMALL_CIRCLE_VISUALIZER);
    }

    public RectVertexVisualizer getRectVertexVisualizer() {
        return (RectVertexVisualizer) visualizers.get(RECT_VERTEX_VISUALIZER);
    }

    public RectVertexVisualizer getRectVisualizer(int maxGroupSize, int thisGroupSize) {

        if (rectVisualizers.containsKey(thisGroupSize)) {
            return (RectVertexVisualizer) rectVisualizers.get(thisGroupSize);
        } else {
            double size = (MAX_GROUP_VERTEX_SIZE / maxGroupSize) * thisGroupSize;
            rectVisualizers.put(thisGroupSize, new RectVertexVisualizer(DEFAULT_ELEMENT_COLOR, size));

            return (RectVertexVisualizer) rectVisualizers.get(thisGroupSize);
        }
    }

    public LevelVisualizer getLevelVisualizer() {
        return (LevelVisualizer) visualizers.get(LEVEL_VISUALIZER);
    }
}
