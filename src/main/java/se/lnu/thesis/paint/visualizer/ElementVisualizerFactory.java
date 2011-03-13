package se.lnu.thesis.paint.visualizer;

import com.google.common.collect.ImmutableMap;
import se.lnu.thesis.paint.visualizer.edge.LineEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.edge.PolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.visualizer.vertex.*;
import se.lnu.thesis.utils.MyColor;
import se.lnu.thesis.properties.ColorSchema;
import se.lnu.thesis.properties.PropertiesHolder;

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

    private static final double MAX_GROUP_VERTEX_SIZE = 0.0207;
    private static final double MIN_GROUP_VERTEX_SIZE = 0.0057;

//    private static final MyColor DEFAULT_ELEMENT_COLOR = new MyColor(100, 100, 100); // deafult color is grey

    private static final int CIRCLE_VERTEX_VISUALIZER = 1;
    private static final int SMALL_CIRCLE_VISUALIZER = 2;
    private static final int RECT_VERTEX_VISUALIZER = 3;
    private static final int POINT_VERTEX_VISUALIZER = 4;

    private static final int POINT_GO_LEAF_VERTEX_VISUALIZER = 5;
    private static final int POINT_GO_NODE_VERTEX_VISUALIZER = 6;

    private static final int CIRCLE_GO_LEAF_VERTEX_VISUALIZER = 7;
    private static final int CIRCLE_GO_NODE_VERTEX_VISUALIZER = 8;

    private static final int LINE_EDGE_VISUALIZER = 101;
    private static final int POLAR_DENDROGRAM_EDGE_VISUALIZER = 102;

    private static final int LEVEL_VISUALIZER = 200;
    private static final int LEVEL_PREVIEW_VISUALIZER = 201;

    private static final ElementVisualizerFactory instance = new ElementVisualizerFactory();

    public static ElementVisualizerFactory getInstance() {
        return instance;
    }

    private ColorSchema colorSchema = PropertiesHolder.getInstance().getColorSchema();

    private ImmutableMap<Object, ElementVisualizer> visualizers = new ImmutableMap.Builder<Object, ElementVisualizer>()
            .put(CIRCLE_VERTEX_VISUALIZER, new CircleVertexVisualizer(colorSchema.getClusterLeaves()))
            /*.put(SMALL_CIRCLE_VISUALIZER, new CircleVertexVisualizer(DEFAULT_ELEMENT_COLOR, 0.005))*/

            .put(RECT_VERTEX_VISUALIZER, new RectVertexVisualizer(colorSchema.getClusterNodes()))

            .put(POINT_VERTEX_VISUALIZER, new PointVertexVisualizer(colorSchema.getClusterEdges()))

            .put(POINT_GO_LEAF_VERTEX_VISUALIZER, new GOPointVertexVisualizer(colorSchema.getGoLeaves()))
            .put(POINT_GO_NODE_VERTEX_VISUALIZER, new GOPointVertexVisualizer(colorSchema.getGoNodes()))

            .put(CIRCLE_GO_LEAF_VERTEX_VISUALIZER, new CircleVertexVisualizer(colorSchema.getGoLeaves()))
            .put(CIRCLE_GO_NODE_VERTEX_VISUALIZER, new CircleVertexVisualizer(colorSchema.getGoNodes()))

            .put(LINE_EDGE_VISUALIZER, new LineEdgeVisualizer(colorSchema.getClusterEdges()))
            .put(POLAR_DENDROGRAM_EDGE_VISUALIZER, new PolarDendrogramEdgeVisualizer(colorSchema.getClusterEdges()))

            .put(LEVEL_VISUALIZER, new LevelVisualizer())
            .put(LEVEL_PREVIEW_VISUALIZER, new LevelPreviewVisualizer())

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

    public CircleVertexVisualizer getGOLeafCircleVisualizer() {
        return (CircleVertexVisualizer) visualizers.get(CIRCLE_GO_LEAF_VERTEX_VISUALIZER);
    }

    public CircleVertexVisualizer getGONodeCircleVisualizer() {
        return (CircleVertexVisualizer) visualizers.get(CIRCLE_GO_NODE_VERTEX_VISUALIZER);
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

/*
    public CircleVertexVisualizer getSmallCircleVisualizer() {
        return (CircleVertexVisualizer) visualizers.get(SMALL_CIRCLE_VISUALIZER);
    }
*/

    public RectVertexVisualizer getRectVertexVisualizer() {
        return (RectVertexVisualizer) visualizers.get(RECT_VERTEX_VISUALIZER);
    }

    public RectVertexVisualizer getRectVisualizer(int minGroupSize, int maxGroupSize, int thisGroupSize) {

        if (rectVisualizers.containsKey(thisGroupSize)) {
            return (RectVertexVisualizer) rectVisualizers.get(thisGroupSize);
        } else {
            double size = (MAX_GROUP_VERTEX_SIZE - MIN_GROUP_VERTEX_SIZE) / (maxGroupSize - minGroupSize) * (thisGroupSize - minGroupSize) + MIN_GROUP_VERTEX_SIZE;

//            rectVisualizers.put(thisGroupSize, new RectVertexVisualizer(colorSchema.getClusterNodes(), size));
            rectVisualizers.put(thisGroupSize, new GroupingElementSmartHighlightVisualizer(colorSchema.getClusterNodes(), size));

            return (RectVertexVisualizer) rectVisualizers.get(thisGroupSize);
        }
    }

    public LevelVisualizer getLevelVisualizer() {
        return (LevelVisualizer) visualizers.get(LEVEL_VISUALIZER);
    }

    public LevelVisualizer getLevelPreviewVisualizer() {
        return (LevelVisualizer) visualizers.get(LEVEL_PREVIEW_VISUALIZER);
    }
}
