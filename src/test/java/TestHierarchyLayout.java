import edu.uci.ics.jung.graph.Graph;
import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.element.*;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.HierarchyLayout2;

import javax.lang.model.util.Elements;
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
        Graph graph = new IOFacade().loadFromGml(getClass().getClassLoader().getResource("RealGOGraph.gml").getPath());

        Assert.assertNotNull(graph);
        Assert.assertEquals(10042, graph.getVertexCount());
        Assert.assertEquals(24155, graph.getEdgeCount());

        GOGraphContainer graphContainer = GOGraphContainer.init();

        HierarchyLayout2 layout = new HierarchyLayout2();
        layout.setGraph(graph);
        layout.setRoot(graphContainer);

        Assert.assertFalse(graphContainer.isLayoutComputed());

        layout.compute();

        Assert.assertTrue(graphContainer.isLayoutComputed());

        int vertexElements = 0;
        int edgeElements = 0;

        Iterator<Element> iterator1 = graphContainer.getElements();
        while (iterator1.hasNext()) {

            Element element = iterator1.next();
            if (element.getType() == ElementType.EDGE) {

                edgeElements++;

            } else {
                Level level = (Level) element;

                Iterator<Element> iterator2 = level.getElements();
                while (iterator2.hasNext()) {
                    Element e = iterator2.next();

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
