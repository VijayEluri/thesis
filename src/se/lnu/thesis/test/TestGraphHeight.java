package se.lnu.thesis.test;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import edu.uci.ics.jung.graph.Graph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.JungYedHandler;
import se.lnu.thesis.utils.GraphMaker;
import se.lnu.thesis.utils.GraphUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 31.07.2010
 * Time: 1:41:45
 */
public class TestGraphHeight {

    @Test
    public void nodeHeight() {
        Graph graph = GraphMaker.createTestBinaryTree();

        assertTrue(graph.containsVertex(11));
        assertTrue(graph.containsVertex(5));
        assertTrue(graph.containsVertex(8));
        assertTrue(graph.containsVertex(7));
        assertTrue(graph.containsVertex(4));
        assertTrue(graph.containsVertex(1));

        assertEquals(5, GraphUtils.getDistance(graph, 11, 5));
        assertEquals(1, GraphUtils.getDistance(graph, 8, 7));

        assertEquals(2, GraphUtils.getDistance(graph, 11, 8));

        assertEquals(2, GraphUtils.getDistance(graph, 4, 1));
    }

    @Test
    public void nodeHeightRealData() {

        Graph graph = (Graph) new GraphMLParser(new JungYedHandler()).load(new File("RealClusterGraph.graphml")).get(0);

        assertTrue(graph.containsVertex("n11732")); // root
        assertTrue(graph.containsVertex("n11733"));
        assertTrue(graph.containsVertex("n1599"));
        assertTrue(graph.containsVertex("n7181"));
        assertTrue(graph.containsVertex("n13869"));
        assertTrue(graph.containsVertex("n4094"));
        assertTrue(graph.containsVertex("n4846"));

        assertEquals(1631, GraphUtils.getNodeHeight(graph, "n11732", 0));

        assertEquals(0, GraphUtils.getDistance(graph, "n11732", "n11732"));
        assertEquals(1, GraphUtils.getDistance(graph, "n11733", "n11732"));
        assertEquals(2, GraphUtils.getDistance(graph, "n1599", "n11732"));
        assertEquals(2, GraphUtils.getDistance(graph, "n7181", "n11732"));
        assertEquals(1, GraphUtils.getDistance(graph, "n13869", "n11732"));
        assertEquals(2, GraphUtils.getDistance(graph, "n4094", "n11732"));
        assertEquals(2, GraphUtils.getDistance(graph, "n4846", "n11732"));
    }


    @Test
    public void levels() {
        Graph graph = GraphMaker.createTestBinaryTree();

        assertEquals(11, graph.getVertexCount());
        assertEquals(10, graph.getEdgeCount());

        assertEquals(0, GraphUtils.getNodeHeight(graph, 1, 0)); // root vertex

        assertEquals(2, GraphUtils.getNodeHeight(graph, 5, 0));
        assertEquals(3, GraphUtils.getNodeHeight(graph, 5, 1));

        assertEquals(5, GraphUtils.getNodeHeight(graph, 8, 0));

        assertEquals(7, GraphUtils.getNodeHeight(graph, 11, 0));
        assertEquals(8, GraphUtils.getNodeHeight(graph, 11, 1));
    }

    @Test
    public void computeGraphHeight() {
        Graph graph = GraphMaker.createTestBinaryTree();

        assertTrue(graph.containsVertex(8));

        Map<Object, Integer> levels = new HashMap();
        int height = GraphUtils.computeLevelsV2(graph, levels);

        assertEquals(8, height);
    }

    @Test
    public void computeLevels() {
        Graph graph = GraphMaker.createTestBinaryTree();

        assertTrue(graph.containsVertex(8));

        Multimap levels = TreeMultimap.create();
        int height = GraphUtils.computeLevels(graph, levels);

        assertEquals(8, height);
        assertEquals(8, levels.keySet().size());

        // level 0 -> node 1
        assertTrue(levels.get(0).contains(1));

        // level 1 -> nodes 3 and 2
        assertTrue(levels.get(1).contains(3));
        assertTrue(levels.get(1).contains(2));

        // level 6 -> nodes 9 and 10
        assertTrue(levels.get(6).contains(9));
        assertTrue(levels.get(6).contains(10));

        // level 7 -> nodes 9 and 11
        assertTrue(levels.get(7).contains(11));

    }


}
