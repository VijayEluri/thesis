import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.DimensionalContainer;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.RadialLayout;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 13.03.11
 * Time: 22:10
 */
public class TestRadialLayout {

    @Test
    public void testCorrectComputation() {
        String file = getClass().getClassLoader().getResource("test_tree_15_14.gml").getPath();

        IOFacade ioFacade = new IOFacade();
        MyGraph graph = ioFacade.loadMyGraphFromYedGml(file);

        Assert.assertNotNull(graph);

        DimensionalContainer root = DimensionalContainer.createContainer(0, graph.getVertices());

        Assert.assertEquals(0, root.getObject());

        Assert.assertEquals(graph.getVertices().size(), root.getObjects().size());

        Assert.assertEquals(15, root.getObjects().size());
        Assert.assertEquals(0, root.getElementsCount());

        Assert.assertFalse(root.isLayoutComputed());

        RadialLayout layout = new RadialLayout();
        layout.setRadius(100.0);
        layout.setGraph(graph);
        layout.setRoot(root);

        layout.compute();

        Assert.assertTrue(root.isLayoutComputed());

        Assert.assertEquals(15, root.getObjects().size());
        Assert.assertEquals(15, root.getElementsCount());

        Element element = root.getElementByObject(0);
        Point2D p = element.getPosition();
        Assert.assertEquals(100.0, p.getX(), 0.0);
        Assert.assertEquals(0.0, p.getY(), 0.0);

        // TODO compute manually other element positions and add comparing here
    }

}
