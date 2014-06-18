import edu.uci.ics.jung.graph.Graph;
import org.junit.Test;
import static org.junit.Assert.*;
import se.lnu.thesis.element.DimensionalContainer;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.ElementType;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.HVTreeLayout;
import se.lnu.thesis.utils.GraphMaker;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 26.03.11
 * Time: 22:38
 *
 *
 *
 */
public class TestHVTreeLayout {

    @Test
    public void checkCorrectComputationForSmallBinaryTree() {

        Graph tree = GraphMaker.createSmallBinaryTree();
        Object root = GraphUtils.getRoot(tree);

        DimensionalContainer container = DimensionalContainer.createContainer(root);


        HVTreeLayout layout = new HVTreeLayout(tree, container);
        layout.setStart(new Point2D.Double(0, 0));
        layout.setDistance(new Point2D.Double(10, -10));
        layout.compute();

        assertEquals(9, container.getElementsCount());

        int vertexCount = 0;
        for (Element element: container) {
            if (element.getType() == ElementType.VERTEX) {
                vertexCount++;
            }
        }

        assertEquals(5, vertexCount);

        assertEquals(0, container.getElementByObject(1).getPosition().getX(), 0);
        assertEquals(0, container.getElementByObject(1).getPosition().getY(), 0);

        assertEquals(0, container.getElementByObject(2).getPosition().getX(), 0);
        assertEquals(30, container.getElementByObject(2).getPosition().getY(), 0);

        assertEquals(10, container.getElementByObject(3).getPosition().getX(), 0);
        assertEquals(0, container.getElementByObject(3).getPosition().getY(), 0);

        assertEquals(10, container.getElementByObject(4).getPosition().getX(), 0);
        assertEquals(10, container.getElementByObject(4).getPosition().getY(), 0);

        assertEquals(20, container.getElementByObject(5).getPosition().getX(), 0);
        assertEquals(0, container.getElementByObject(5).getPosition().getY(), 0);
    }

    @Test
    public void checkComputationPerformanceForCluster() {
        IOFacade ioFacade = new IOFacade();


        Graph cluster = ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealClusterGraph.gml").getPath());
        Object root = GraphUtils.getRoot(cluster);
        DimensionalContainer container = DimensionalContainer.createContainer(root);


        HVTreeLayout layout = new HVTreeLayout(cluster, container);
        layout.setStart(new Point2D.Double(0, 0));
        layout.setDistance(new Point2D.Double(10, -10));

        long start = System.currentTimeMillis();
        layout.compute();
        long end = System.currentTimeMillis();

        System.out.println("");
        System.out.println("    HVTreeLayout for RealClusterGraph.gml computed in " + (end-start) + " ms");
        System.out.println("");
    }

}
