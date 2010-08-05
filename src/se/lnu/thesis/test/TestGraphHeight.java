package se.lnu.thesis.test;

import edu.uci.ics.jung.graph.Graph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.JungYedHandler;
import se.lnu.thesis.utils.GraphUtils;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 31.07.2010
 * Time: 1:41:45
 */
public class TestGraphHeight extends TestGraph {

    @Test
    public void nodeHeight() {
        Graph graph = createGraph();

        GraphUtils utils = GraphUtils.getInstance();

        assertTrue(graph.containsVertex(11));

        assertEquals(5, utils.getNodeHeight(graph, 11, 5, 0));
        assertEquals(1, utils.getNodeHeight(graph, 8, 7, 0));

        assertEquals(2, utils.getNodeHeight(graph, 11, 8, 0));

        assertEquals(2, utils.getNodeHeight(graph, 4, 1, 0));
    }

    @Test
    public void nodeHeightRealData() {

        Graph graph = (Graph) new GraphMLParser(new JungYedHandler()).load(new File("RealClusterGraph.graphml")).get(0);

        GraphUtils utils = GraphUtils.getInstance();

        assertTrue(graph.containsVertex("n11732")); // root
        assertTrue(graph.containsVertex("n11733"));
        assertTrue(graph.containsVertex("n1599"));
        assertTrue(graph.containsVertex("n7181"));
        assertTrue(graph.containsVertex("n13869"));
        assertTrue(graph.containsVertex("n4094"));
        assertTrue(graph.containsVertex("n4846"));

        assertEquals(1631, utils.getNodeHeight(graph, "n11732", 0));

        assertEquals(0, utils.getNodeHeight(graph, "n11732", "n11732", 0));
        assertEquals(1, utils.getNodeHeight(graph, "n11733", "n11732", 0));
        assertEquals(2, utils.getNodeHeight(graph, "n1599", "n11732", 0));
        assertEquals(2, utils.getNodeHeight(graph, "n7181", "n11732", 0));
        assertEquals(1, utils.getNodeHeight(graph, "n13869", "n11732", 0));
        assertEquals(2, utils.getNodeHeight(graph, "n4094", "n11732", 0));
        assertEquals(2, utils.getNodeHeight(graph, "n4846", "n11732", 0));
    }


    @Test
    public void graphHeight() {
        Graph graph = createGraph();

        assertEquals(11, graph.getVertexCount());
        assertEquals(10, graph.getEdgeCount());

        GraphUtils utils = GraphUtils.getInstance();

        assertEquals(0, utils.getNodeHeight(graph, 1, 0)); // root vertex
        assertEquals(2, utils.getNodeHeight(graph, 5, 0));
        assertEquals(3, utils.getNodeHeight(graph, 5, 1));
        assertEquals(5, utils.getNodeHeight(graph, 8, 0));
        assertEquals(7, utils.getNodeHeight(graph, 11, 0));
        assertEquals(8, utils.getNodeHeight(graph, 11, 1));
    }


}
