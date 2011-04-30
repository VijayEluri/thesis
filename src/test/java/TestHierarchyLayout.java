import com.google.common.collect.HashMultimap;
import edu.uci.ics.jung.graph.Graph;
import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.ElementType;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.HierarchyLayout;
import se.lnu.thesis.layout.HierarchyLayout2;
import se.lnu.thesis.utils.GraphUtils;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 08.04.11
 * Time: 22:59
 */
public class TestHierarchyLayout {

    @Test
    public void checkHierarchyLayout2() {
        Graph graph = new IOFacade().loadMyGraphFromGml(getClass().getClassLoader().getResource("RealGOGraph.gml").getPath());

        Assert.assertNotNull(graph);
        Assert.assertEquals(TestRealData.GO_NODE_COUNT, graph.getVertexCount());
        Assert.assertEquals(TestRealData.GO_EDGE_COUNT, graph.getEdgeCount());

        GOGraphContainer graphContainer = GOGraphContainer.init();

        HierarchyLayout2 layout = new HierarchyLayout2();
        layout.setGraph(graph);
        layout.setRoot(graphContainer);

        Assert.assertFalse(graphContainer.isLayoutComputed());

        layout.compute();

        Assert.assertTrue(graphContainer.isLayoutComputed());

        int vertexElements = 0;
        int edgeElements = 0;

        for (Element element : graphContainer) {
            if (element.getType() == ElementType.EDGE) {
                edgeElements++;
            } else {
                Level level = (Level) element;

                for (Element e : level) {
                    if (e.getType() == ElementType.VERTEX) {
                        vertexElements++;
                    }
                }
            }
        }

        Assert.assertEquals(edgeElements, graph.getEdgeCount());
        Assert.assertEquals(vertexElements, graph.getVertexCount());

    }

    @Test
    public void checkHierarchyLayoutAmountOfElemetsComputed() {
        Graph graph = new IOFacade().loadMyGraphFromGml(getClass().getClassLoader().getResource("RealGOGraph.gml").getPath());

        Assert.assertNotNull(graph);
        Assert.assertEquals(TestRealData.GO_NODE_COUNT, graph.getVertexCount());
        Assert.assertEquals(TestRealData.GO_EDGE_COUNT, graph.getEdgeCount());

        GOGraphContainer graphContainer = GOGraphContainer.init();

        HierarchyLayout layout = new HierarchyLayout();
        layout.setGraph(graph);
        layout.setRoot(graphContainer);

        Assert.assertEquals(0, graphContainer.getElementsCount());

        Assert.assertFalse(graphContainer.isLayoutComputed());
        layout.compute();
        Assert.assertTrue(graphContainer.isLayoutComputed());

        int vertexElements = 0;
        int edgeElements = 0;

        for (Element element : graphContainer) {
            if (element.getType() == ElementType.EDGE) {
                edgeElements++;
            } else {
                Level level = (Level) element;

                for (Element e : level) {
                    if (e.getType() == ElementType.VERTEX) {
                        vertexElements++;
                    }
                }
            }
        }

        Assert.assertEquals(edgeElements, graph.getEdgeCount());
        Assert.assertEquals(vertexElements, graph.getVertexCount());
    }

    @Test
    public void checkHierarchyLayoutEachLevel() {
        Graph graph = new IOFacade().loadMyGraphFromGml(getClass().getClassLoader().getResource("RealGOGraph.gml").getPath());

        Assert.assertNotNull(graph);
        Assert.assertEquals(TestRealData.GO_NODE_COUNT, graph.getVertexCount());
        Assert.assertEquals(TestRealData.GO_EDGE_COUNT, graph.getEdgeCount());

        GOGraphContainer graphContainer = GOGraphContainer.init();

        HierarchyLayout layout = new HierarchyLayout();
        layout.setGraph(graph);
        layout.setRoot(graphContainer);

        layout.compute();


        HashMultimap levelsMap = HashMultimap.create();
        GraphUtils.computeLevels(graph, levelsMap);

        for (Element element : graphContainer) {
            if (element instanceof Level) {
                Level level = (Level) element;

                Assert.assertTrue(levelsMap.containsKey(level.getObject()));

                Set vertices = levelsMap.get(level.getObject());

                Assert.assertEquals(level.getElementsCount(), vertices.size());
                Assert.assertTrue(level.getObjects().containsAll(vertices));
                Assert.assertEquals(level.getPreview().getElementsCount(), vertices.size());
            }
        }

    }

}
