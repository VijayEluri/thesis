import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.*;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.PolarDendrogramLayout;

import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 13.03.11
 * Time: 21:20
 */
public class TestPolarDendrogramLayout {

    public static final Logger LOGGER = Logger.getLogger(TestPolarDendrogramLayout.class);

    @Test
    public void computeForSmallTree() {
        String file = getClass().getClassLoader().getResource("test_tree_15_14.gml").getPath();

        IOFacade ioFacade = new IOFacade();
        MyGraph graph = ioFacade.loadFromYedGml(file);

        Assert.assertNotNull(graph);

        DimensionalContainer root = DimensionalContainer.init(0, graph.getVertices());

        Assert.assertEquals(0, root.getObject());

        Assert.assertEquals(graph.getVertices().size(), root.getObjects().size());

        Assert.assertEquals(15, root.getObjects().size());
        Assert.assertEquals(0, root.getElementsCount());

        Assert.assertFalse(root.isLayoutComputed());

        PolarDendrogramLayout layout = new PolarDendrogramLayout();
        layout.setRadius(100.0);
        layout.setGraph(graph);
        layout.setRoot(root);

        layout.compute();

        Assert.assertTrue(root.isLayoutComputed());

        Assert.assertEquals(15, root.getObjects().size());
        Assert.assertEquals(29, root.getElementsCount());

        Element element = root.getElementByObject(0);
        Point2D p = element.getPosition();
        Assert.assertEquals(0.0, p.getX(), 0.0);
        Assert.assertEquals(0.0, p.getY(), 0.0);

        // TODO compute manually other element positions and add comparing here

    }

}
