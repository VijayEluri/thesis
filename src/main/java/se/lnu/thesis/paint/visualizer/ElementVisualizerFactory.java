package se.lnu.thesis.paint.visualizer;

import com.google.common.collect.ImmutableMap;
import se.lnu.thesis.element.GOBundledEdge;
import se.lnu.thesis.paint.visualizer.edge.BundledEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.edge.LineEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.edge.PolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.graph_level.LevelPreviewVisualizer;
import se.lnu.thesis.paint.visualizer.graph_level.LevelVisualizer;
import se.lnu.thesis.paint.visualizer.vertex.*;
import se.lnu.thesis.properties.ColorSchema;
import se.lnu.thesis.properties.PropertiesHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.07.2010
 * Time: 23:51:08
 */
public class ElementVisualizerFactory {

    private static final double MAX_GROUP_VERTEX_SIZE = 0.0207;
    private static final double MIN_GROUP_VERTEX_SIZE = 0.0057;

    enum Visualizers {

        CIRCLE_VERTEX_VISUALIZER,
        SMALL_CIRCLE_VISUALIZER,
        RECT_VERTEX_VISUALIZER,
        POINT_VERTEX_VISUALIZER,

        POINT_GO_LEAF_VERTEX_VISUALIZER,
        POINT_GO_NODE_VERTEX_VISUALIZER,

        CIRCLE_GO_LEAF_VERTEX_VISUALIZER,
        CIRCLE_GO_NODE_VERTEX_VISUALIZER,

        GO_BUNDLED_EDGE_VISUALIZER,

        LINE_EDGE_VISUALIZER,
        POLAR_DENDROGRAM_EDGE_VISUALIZER,
        THIN_LINE_EDGE_VISUALIZER,

        LEVEL_VISUALIZER,
        LEVEL_PREVIEW_VISUALIZER
    }

    private static final ElementVisualizerFactory instance = new ElementVisualizerFactory();

    public static ElementVisualizerFactory getInstance() {
        return instance;
    }

    private ColorSchema colorSchema = PropertiesHolder.getInstance().getColorSchema();

    private ImmutableMap<Object, ElementVisualizer> visualizers = new ImmutableMap.Builder<Object, ElementVisualizer>()
            .put(Visualizers.CIRCLE_VERTEX_VISUALIZER, new CircleVertexVisualizer(colorSchema.getClusterLeaves()))

            .put(Visualizers.RECT_VERTEX_VISUALIZER, new RectVertexVisualizer(colorSchema.getClusterNodes()))

            .put(Visualizers.POINT_VERTEX_VISUALIZER, new PointVertexVisualizer(colorSchema.getClusterEdges()))

            .put(Visualizers.POINT_GO_LEAF_VERTEX_VISUALIZER, new GOPointVertexVisualizer(colorSchema.getGoLeaves()))
            .put(Visualizers.POINT_GO_NODE_VERTEX_VISUALIZER, new GOPointVertexVisualizer(colorSchema.getGoNodes()))

            .put(Visualizers.CIRCLE_GO_LEAF_VERTEX_VISUALIZER, new CircleVertexVisualizer(colorSchema.getGoLeaves()))
            .put(Visualizers.CIRCLE_GO_NODE_VERTEX_VISUALIZER, new CircleVertexVisualizer(colorSchema.getGoNodes()))

            .put(Visualizers.GO_BUNDLED_EDGE_VISUALIZER, new BundledEdgeVisualizer(colorSchema.getClusterEdges(), 0.5f)) // TODO rename cluster edges to edges

            .put(Visualizers.LINE_EDGE_VISUALIZER, new LineEdgeVisualizer(colorSchema.getClusterEdges()))
            .put(Visualizers.POLAR_DENDROGRAM_EDGE_VISUALIZER, new PolarDendrogramEdgeVisualizer(colorSchema.getClusterEdges()))
            .put(Visualizers.THIN_LINE_EDGE_VISUALIZER, new LineEdgeVisualizer(colorSchema.getClusterEdges(), 0.5f))

            .put(Visualizers.LEVEL_VISUALIZER, new LevelVisualizer())
            .put(Visualizers.LEVEL_PREVIEW_VISUALIZER, new LevelPreviewVisualizer())

            .build();

    private Map<Integer, GroupingElementSmartHighlightVisualizer> rectVisualizers = new HashMap<Integer, GroupingElementSmartHighlightVisualizer>();

    private ElementVisualizerFactory() {
    }

    public CircleVertexVisualizer getCircleVisualizer() {
        return (CircleVertexVisualizer) visualizers.get(Visualizers.CIRCLE_VERTEX_VISUALIZER);
    }

    public GOPointVertexVisualizer getGOLeafPointVisualizer() {
        return (GOPointVertexVisualizer) visualizers.get(Visualizers.POINT_GO_LEAF_VERTEX_VISUALIZER);
    }

    public GOPointVertexVisualizer getGONodePointVisualizer() {
        return (GOPointVertexVisualizer) visualizers.get(Visualizers.POINT_GO_NODE_VERTEX_VISUALIZER);
    }

    public CircleVertexVisualizer getGOLeafCircleVisualizer() {
        return (CircleVertexVisualizer) visualizers.get(Visualizers.CIRCLE_GO_LEAF_VERTEX_VISUALIZER);
    }

    public CircleVertexVisualizer getGONodeCircleVisualizer() {
        return (CircleVertexVisualizer) visualizers.get(Visualizers.CIRCLE_GO_NODE_VERTEX_VISUALIZER);
    }

    public PointVertexVisualizer getPointVisualizer() {
        return (PointVertexVisualizer) visualizers.get(Visualizers.POINT_VERTEX_VISUALIZER);
    }

    public LineEdgeVisualizer getLineEdgeVisializer() {
        return (LineEdgeVisualizer) visualizers.get(Visualizers.LINE_EDGE_VISUALIZER);
    }

    public PolarDendrogramEdgeVisualizer getPolarDendrogramEdgeVisializer() {
        return (PolarDendrogramEdgeVisualizer) visualizers.get(Visualizers.POLAR_DENDROGRAM_EDGE_VISUALIZER);
    }

    public LineEdgeVisualizer getThinLineEdgeVisializer() {
        return (LineEdgeVisualizer) visualizers.get(Visualizers.THIN_LINE_EDGE_VISUALIZER);
    }

    public RectVertexVisualizer getRectVertexVisualizer() {
        return (RectVertexVisualizer) visualizers.get(Visualizers.RECT_VERTEX_VISUALIZER);
    }

    public RectVertexVisualizer getRectVisualizer(int minGroupSize, int maxGroupSize, Integer thisGroupSize) {

        if (rectVisualizers.containsKey(thisGroupSize)) {
            return rectVisualizers.get(thisGroupSize);
        } else {
            double size = (MAX_GROUP_VERTEX_SIZE - MIN_GROUP_VERTEX_SIZE) / (maxGroupSize - minGroupSize) * (thisGroupSize - minGroupSize) + MIN_GROUP_VERTEX_SIZE;

            rectVisualizers.put(thisGroupSize, new GroupingElementSmartHighlightVisualizer(colorSchema.getClusterNodes(), size));

            return rectVisualizers.get(thisGroupSize);
        }
    }

    public LevelVisualizer getLevelVisualizer() {
        return (LevelVisualizer) visualizers.get(Visualizers.LEVEL_VISUALIZER);
    }

    public LevelVisualizer getLevelPreviewVisualizer() {
        return (LevelVisualizer) visualizers.get(Visualizers.LEVEL_PREVIEW_VISUALIZER);
    }

    public BundledEdgeVisualizer getBundledEdgeVisualizer() {
        return (BundledEdgeVisualizer) visualizers.get(Visualizers.GO_BUNDLED_EDGE_VISUALIZER);
    }
}
