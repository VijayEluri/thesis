package se.lnu.thesis.layout;

import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * <p/>
 * <p/>
 * This layout does almost same visualisations as HierarchyLayout: draw vertices by levels
 * But it also move all leaf of the graph to bottom level
 */
public class LeafsBottomHierarchyLayout extends HierarchyLayout {

    public LeafsBottomHierarchyLayout(MyGraph graph, Container root) {
        super(graph, root);
    }

    public LeafsBottomHierarchyLayout() {

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

    /**
     * Compute amount of levels for the graph and corresponded graph vertices.
     * Then find all leafs and move to the bottom level of the graph.
     */
    @Override
    protected void computeGeneOntologyLevels() {
        super.computeGeneOntologyLevels();

        GraphUtils.moveAllLeafBottomLevel(graph, levels);
    }

    public int getLevelCount() {
        return levelCount;
    }
}
