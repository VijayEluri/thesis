package se.lnu.thesis.test;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import static org.junit.Assert.*;
import org.junit.Test;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.JungYedHandler;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class TestUtils {

    @Test
    public void testMin() {
        assertEquals(5.0, Utils.min(9.0d, 5.0d));
    }

    @Test
    public void testMax() {
        assertEquals(9.0, Utils.max(9.0d, 5.0d));
    }

    @Test
    public void graphHeight() {
        Graph graph = createGraph();

        assertEquals(11, graph.getVertexCount());
        assertEquals(10, graph.getEdgeCount());

        assertEquals(0, GraphUtils.getInstance().getNodeHeight(graph, 1, 0)); // root vertex
        assertEquals(2, GraphUtils.getInstance().getNodeHeight(graph, 5, 0));
        assertEquals(3, GraphUtils.getInstance().getNodeHeight(graph, 5, 1));
        assertEquals(5, GraphUtils.getInstance().getNodeHeight(graph, 8, 0));
        assertEquals(7, GraphUtils.getInstance().getNodeHeight(graph, 11, 0));
        assertEquals(8, GraphUtils.getInstance().getNodeHeight(graph, 11, 1));
    }

    @Test
    public void nodeLeafs() {
        Graph graph = createGraph();

        assertEquals(11, graph.getVertexCount());
        assertEquals(10, graph.getEdgeCount());

        assertTrue(graph.containsVertex(1));

        Set leafs = GraphUtils.getInstance().getNodeLeafs(graph, 1);

        assertEquals(4, leafs.size());

        assertFalse("There are shouldn't be 3!!", leafs.contains(3));

        assertTrue(leafs.contains(2));
        assertTrue(leafs.contains(4));
        assertTrue(leafs.contains(9));
        assertTrue(leafs.contains(11));
    }

    @Test
    public void subgraph() {
        Graph graph = createGraph();

        assertTrue(graph.containsVertex(6));

        Graph subgraph = new DirectedSparseGraph();
        Set leafs = GraphUtils.getInstance().getSubgraphAndLeafs(graph, subgraph, 6);

        assertEquals(2, leafs.size());
        assertTrue(leafs.contains(9));
        assertTrue(leafs.contains(11));


        assertEquals(6, subgraph.getVertexCount());
        assertTrue(subgraph.containsVertex(6));
        assertTrue(subgraph.containsVertex(7));
        assertTrue(subgraph.containsVertex(8));
        assertTrue(subgraph.containsVertex(9));
        assertTrue(subgraph.containsVertex(10));
        assertTrue(subgraph.containsVertex(11));
    }

    @Test
    public void subgraph2() {
        Graph graph = createGraph();

        assertTrue(graph.containsVertex(8));

        Graph subgraph = new DirectedSparseGraph();
        Set leafs = GraphUtils.getInstance().getSubgraphAndLeafs(graph, subgraph, 8);

        assertEquals(2, leafs.size());
        assertTrue(leafs.contains(9));
        assertTrue(leafs.contains(11));


        assertEquals(4, subgraph.getVertexCount());
        assertTrue(subgraph.containsVertex(8));
        assertTrue(subgraph.containsVertex(9));
        assertTrue(subgraph.containsVertex(10));
        assertTrue(subgraph.containsVertex(11));
    }

    /**
     * ______1
     * ____/   \
     * ___3     2
     * _/  \
     * 4    5
     * _____|
     * _____6
     * _____|
     * _____7
     * _____|
     * _____8
     * ____/ \
     * ___9__10
     * ______|
     * ______11
     */
    private Graph createGraph() {
        Graph graph = new DirectedSparseGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);
        graph.addVertex(10);

        graph.addEdge("1->2", 1, 2, EdgeType.DIRECTED);
        graph.addEdge("1->3", 1, 3, EdgeType.DIRECTED);
        graph.addEdge("3->4", 3, 4, EdgeType.DIRECTED);
        graph.addEdge("3->5", 3, 5, EdgeType.DIRECTED);
        graph.addEdge("5->6", 5, 6, EdgeType.DIRECTED);
        graph.addEdge("6->7", 6, 7, EdgeType.DIRECTED);
        graph.addEdge("7->8", 7, 8, EdgeType.DIRECTED);
        graph.addEdge("8->9", 8, 9, EdgeType.DIRECTED);
        graph.addEdge("8->10", 8, 10, EdgeType.DIRECTED);
        graph.addEdge("10->11", 10, 11, EdgeType.DIRECTED);

        return graph;
    }

    @Test
    public void heightPerfomance() {
        Graph graph = new DirectedSparseGraph();

        final int size = 10000;

        graph.addVertex(0); // <-- root
        for (int i = 1; i < size; i++) {
            graph.addVertex(i);
            graph.addEdge((i - 1) + "->" + i, i - 1, i);
        }

        long start, end;


        start = System.currentTimeMillis();
        int dfsRes = GraphUtils.getInstance().invertDfsNodes(graph, size - 1).size();
        end = System.currentTimeMillis();

        System.out.println(end - start + "ms");


        start = System.currentTimeMillis();
        int height = GraphUtils.getInstance().getNodeHeight(graph, size - 1, 1);
        end = System.currentTimeMillis();

        System.out.println(end - start + "ms");


        assertEquals(dfsRes, height);
    }

    @Test
    public void computeLevelsPerfomance() {
        Graph graph = new DirectedSparseGraph();

        final int size = 5000;

        graph.addVertex(0); // <-- root
        for (int i = 1; i < size; i++) {
            graph.addVertex(i);
            graph.addEdge((i - 1) + "->" + i, i - 1, i);
        }

        Map<Object, Integer> dfsMap = new HashMap<Object, Integer>();
        Map<Object, Integer> levelMap = new HashMap<Object, Integer>();

        long start, end;
        start = System.currentTimeMillis();
        int dfsGraphHeight = GraphUtils.getInstance().computeLevels(graph, dfsMap);
        end = System.currentTimeMillis();

        System.out.println((end - start) / 1000 + "s");


        start = System.currentTimeMillis();
        int height = GraphUtils.getInstance().computeLevelsV2(graph, levelMap);
        end = System.currentTimeMillis();

        System.out.println((end - start) / 1000 + "s");


        assertEquals(dfsGraphHeight, height + 1);
        //assertEquals(dfsMap.entrySet(), height);
    }

    @Test
    public void testPerfomanceOnRealData() {
        long start, end;

        start = System.currentTimeMillis();
        Graph graph = (Graph) new GraphMLParser(new JungYedHandler()).load(new File("RealClusterGraph.graphml")).get(0);
        end = System.currentTimeMillis();

        System.out.println("Graph loaded in " + (end - start) / 1000 + "s");


        Map<Object, Integer> dfsMap = new HashMap<Object, Integer>();
        Map<Object, Integer> levelMap = new HashMap<Object, Integer>();

        start = System.currentTimeMillis();
        int dfsGraphHeight = GraphUtils.getInstance().computeLevels(graph, dfsMap);
        end = System.currentTimeMillis();

        System.out.println("Old version did in " + (end - start) / 1000 + "s");


        start = System.currentTimeMillis();
        int height = GraphUtils.getInstance().computeLevelsV2(graph, levelMap);
        end = System.currentTimeMillis();

        System.out.println("New version did in " + (end - start) / 1000 + "s");


        assertEquals(dfsGraphHeight, height + 1);
        //assertEquals(dfsMap.entrySet(), height);
    }


}
