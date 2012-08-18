import com.google.common.collect.UnmodifiableIterator;
import edu.uci.ics.jung.graph.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.LeafsBottomHierarchyLayout;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 17.04.11
 * Time: 18:44
 */
public class TestContainer {


    protected GOGraphContainer graphContainer = null;
    protected Graph graph = null;

    @Before
    public void prepareGraphAndContainer() {
        graph = new IOFacade().loadMyGraphFromGml(getClass().getClassLoader().getResource("RealGOGraph.gml").getPath());

        Assert.assertNotNull(graph);
        Assert.assertEquals(TestRealData.GO_NODE_COUNT, graph.getVertexCount());
        Assert.assertEquals(TestRealData.GO_EDGE_COUNT, graph.getEdgeCount());

        graphContainer = GOGraphContainer.init();

        LeafsBottomHierarchyLayout layoutLeafsBottom = new LeafsBottomHierarchyLayout();
        layoutLeafsBottom.setGraph(graph);
        layoutLeafsBottom.setRoot(graphContainer);

        Assert.assertFalse(graphContainer.isLayoutComputed());

        layoutLeafsBottom.compute();

        Assert.assertTrue(graphContainer.isLayoutComputed());
    }

    @Test
    public void checkAllElementsIterator() {
        UnmodifiableIterator<Element> iterator = graphContainer.getAllElements();

        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        Assert.assertEquals(TestRealData.GO_NODE_COUNT + TestRealData.GO_EDGE_COUNT + TestRealData.GO_LEVEL_COUNT, count);
    }


    @Test
    public void checkEdgeIterator() {

        UnmodifiableIterator<Element> iterator = graphContainer.getAllEdgeElements();

        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        Assert.assertEquals(TestRealData.GO_EDGE_COUNT, count);
    }


}
