import edu.uci.ics.jung.graph.Graph;
import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.element.*;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.HierarchyLayout2;

import java.util.Iterator;

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

}
