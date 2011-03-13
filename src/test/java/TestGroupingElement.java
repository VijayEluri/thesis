import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.Layout;
import se.lnu.thesis.layout.RadialLayout;

import java.awt.geom.Point2D;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 13.03.11
 * Time: 21:19
 */
public class TestGroupingElement {

    MyGraph graph = null;

    @Before
    public void initializeGraph() {
        String file = getClass().getClassLoader().getResource("test_tree_15_14.gml").getPath();

        IOFacade ioFacade = new IOFacade();
        graph = ioFacade.loadFromYedGml(file);
    }

    @After
    public void cleanUpGraph() {
        graph = null;
    }

    @Test
    public void testCorrectHighLighting() {
        Assert.assertNotNull(graph);

        GroupingElement root = GroupingElement.init(0, new Point2D.Double(0,0), null, graph.getVertices());

        Assert.assertEquals(0, root.getObject());

        Assert.assertEquals(graph.getVertices().size(), root.getObjects().size());

        Assert.assertEquals(15, root.getObjects().size());
        Assert.assertEquals(0, root.getElementsCount());

        Assert.assertFalse(root.isLayoutComputed());

        Layout layout = new RadialLayout(graph, root, 100.0);
        layout.compute();

        Assert.assertTrue(root.isLayoutComputed());
        Assert.assertEquals(15, root.getObjects().size());
        Assert.assertEquals(15, root.getElementsCount());

        Assert.assertEquals(0, root.getHighlightedVerticesCount());

        HashSet highLightObjects = new HashSet();
        highLightObjects.add(0);
        highLightObjects.add(2);
        highLightObjects.add(5);
        highLightObjects.add(12);
        // no such vertices in the graph
        highLightObjects.add(101);
        highLightObjects.add(102);

        root.setHighlighted(highLightObjects);

        Assert.assertEquals(4, root.getHighlightedVerticesCount());

        root.resetHighlighting();

        Assert.assertEquals(0, root.getHighlightedVerticesCount());

    }

}
