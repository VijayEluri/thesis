package se.lnu.thesis.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.IOFacade;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.08.2010
 * Time: 16:09:13
 * To change this template use File | Settings | File Templates.
 */
public class TestYedGmlReader {


    @Test
    public void readTree_15_14() throws IOException {
        IOFacade ioFacade = new IOFacade();

        MyGraph graph = ioFacade.loadFromYedGml("test_tree_15_14.gml");

        assertNotNull(graph);
        assertEquals(15, graph.getVertexCount());
        assertEquals(14, graph.getEdgeCount());

        assertNotNull(graph.findEdge(0, 1));
        assertNotNull(graph.findEdge(0, 2));
        assertNotNull(graph.findEdge(1, 3));
        assertNotNull(graph.findEdge(3, 7));

        assertEquals(15, graph.getLabelCount());
    }

}