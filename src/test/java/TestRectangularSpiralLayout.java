

import org.slf4j.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.DimensionalContainer;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.ElementType;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.RectangularSpiralLayout;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.05.2010
 * Time: 15:42:17
 */
public class TestRectangularSpiralLayout {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestRectangularSpiralLayout.class);

    @Test
    public void positionOverlappings() {
        IOFacade ioFacade = new IOFacade();
        MyGraph graph = ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealClusterGraph.gml").getFile());
        assertNotNull(graph);

        Container container = DimensionalContainer.createContainer("root");
        assertEquals(0, container.getSize());

        RectangularSpiralLayout layout = new RectangularSpiralLayout(graph, container);
        layout.compute();

        assertTrue(container.getSize() > 0);


        Collection<Element> groupElementsOverlapping = new LinkedList();
        Collection<Element> nodeElementsOverlapping = new LinkedList();

        for (Element element1 : container) {
            for (Element element2 : container) {

                if (element1.getObject() != element2.getObject()) {

                    Point2D p1 = element1.getPosition();
                    Point2D p2 = element2.getPosition();

                    if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {

                        if (element1.getType() == ElementType.CONTAINER && element2.getType() == ElementType.CONTAINER) {
                            groupElementsOverlapping.add(element1);
                            groupElementsOverlapping.add(element2);
                        }

                        if (element1.getType() == ElementType.VERTEX && element2.getType() == ElementType.VERTEX) {
                            nodeElementsOverlapping.add(element1);
                            nodeElementsOverlapping.add(element2);
                        }

                    }


                }
            }
        }

        assertEquals(0, groupElementsOverlapping.size());
        assertEquals(0, nodeElementsOverlapping.size());
    }

}
