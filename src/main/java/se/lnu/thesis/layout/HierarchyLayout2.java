package se.lnu.thesis.layout;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.*;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 *
 *
 *  This layout does almost same visualisations as HierarchyLayout: draw vertices by levels
 *      But it also move all leaf of the graph to bottom level
 *
 */
public class HierarchyLayout2 extends HierarchyLayout {

    public HierarchyLayout2(MyGraph graph, Container root) {
        super(graph, root);
    }

    public HierarchyLayout2() {

    }

    @Override
    protected void initLevelLayout() {
        LevelLayout layout = new LevelLayout();
        layout.setLeafVisualizer(ElementVisualizerFactory.getInstance().getGOLeafPointVisualizer());

        levelLayout = new LevelBarLayout(graph, layout);
    }

    @Override
    protected void initPreviewLevelLayout() {
        LevelLayout layout = new LevelLayout();
        layout.setVertexVisualizer(ElementVisualizerFactory.getInstance().getGONodePointVisualizer());
        layout.setLeafVisualizer(ElementVisualizerFactory.getInstance().getGOLeafPointVisualizer());

        levelPreviewLayout = new LevelBarLayout(graph, layout);
    }

    @Override
    protected void computeGeneOntologyLevels() {
        super.computeGeneOntologyLevels();

        GraphUtils.moveAllLeafBottomLevel(graph, levels);
    }

    public int getLevelCount() {
        return levelCount;
    }
}
