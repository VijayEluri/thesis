

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import static org.junit.Assert.*;
import org.junit.Test;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.JungYedHandler;
import se.lnu.thesis.utils.GraphMaker;
import se.lnu.thesis.utils.GraphTraversalUtils;
import se.lnu.thesis.utils.GraphUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TestGraphUtils {


    @Test
    public void nodeLeafs() {
        Graph graph = GraphMaker.createTestBinaryTree();

        assertEquals(11, graph.getVertexCount());
        assertEquals(10, graph.getEdgeCount());

        assertTrue(graph.containsVertex(1));

        Set leafs = GraphUtils.getLeafs(graph, 1);

        assertEquals(4, leafs.size());

        assertFalse("There are shouldn't be 3!!", leafs.contains(3));

        assertTrue(leafs.contains(2));
        assertTrue(leafs.contains(4));
        assertTrue(leafs.contains(9));
        assertTrue(leafs.contains(11));
    }

    @Test
    public void subgraph() {
        Graph graph = GraphMaker.createTestBinaryTree();

        assertTrue(graph.containsVertex(6));

        Graph subgraph = new DirectedSparseGraph();
        Set leafs = GraphUtils.extractSubgraph(graph, subgraph, 6);

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
        Graph graph = GraphMaker.createTestBinaryTree();

        assertTrue(graph.containsVertex(8));

        Graph subgraph = new DirectedSparseGraph();
        Set leafs = GraphUtils.extractSubgraph(graph, subgraph, 8);

        assertEquals(2, leafs.size());
        assertTrue(leafs.contains(9));
        assertTrue(leafs.contains(11));


        assertEquals(4, subgraph.getVertexCount());
        assertTrue(subgraph.containsVertex(8));
        assertTrue(subgraph.containsVertex(9));
        assertTrue(subgraph.containsVertex(10));
        assertTrue(subgraph.containsVertex(11));
    }

    @Test
    public void heightPerfomance() {
        Graph graph = new DirectedSparseGraph();

        final int size = 1000;

        graph.addVertex(0); // <-- root
        for (int i = 1; i < size; i++) {
            graph.addVertex(i);
            graph.addEdge((i - 1) + "->" + i, i - 1, i);
        }

        long start, end;

        start = System.currentTimeMillis();
        int height = GraphUtils.getNodeHeight(graph, size - 1, 0);
        end = System.currentTimeMillis();

        System.out.println(end - start + "ms");


        assertEquals(size - 1, height);
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
        int dfsGraphHeight = GraphUtils.computeLevels(graph, dfsMap);
        end = System.currentTimeMillis();

        System.out.println((end - start) / 1000 + "s");


        start = System.currentTimeMillis();
        int height = GraphUtils.computeLevelsV2(graph, levelMap);
        end = System.currentTimeMillis();

        System.out.println((end - start) / 1000 + "s");


        assertEquals(dfsGraphHeight, height + 1);
        //assertEquals(dfsMap.entrySet(), height);
    }

    @Test
    public void testPerfomanceOnRealData() {
        long start, end;

        start = System.currentTimeMillis();
        Graph graph = (Graph) new GraphMLParser(new JungYedHandler()).load(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath()).get(0);
        end = System.currentTimeMillis();

        System.out.println("Graph loaded in " + (end - start) / 1000 + "s");


        Map<Object, Integer> dfsMap = new HashMap<Object, Integer>();
        Map<Object, Integer> levelMap = new HashMap<Object, Integer>();

        start = System.currentTimeMillis();
        int dfsGraphHeight = GraphUtils.computeLevels(graph, dfsMap);
        end = System.currentTimeMillis();

        System.out.println("Old version did in " + (end - start) / 1000 + "s");


        start = System.currentTimeMillis();
        int height = GraphUtils.computeLevelsV2(graph, levelMap);
        end = System.currentTimeMillis();

        System.out.println("New version did in " + (end - start) / 1000 + "s");


        assertEquals(dfsGraphHeight, height + 1);
        //assertEquals(dfsMap.entrySet(), height);
    }


    @Test
    public void longestPath() {
        Graph graph = GraphMaker.createTestBinaryTree();

        List path = GraphUtils.getLongestPath(graph);

        assertEquals(8, path.size());

        assertEquals(1, path.get(0));
        assertEquals(11, path.get(path.size() - 1));

        assertTrue(path.contains(1));
        assertTrue(path.contains(3));
        assertTrue(path.contains(5));
        assertTrue(path.contains(6));
        assertTrue(path.contains(7));
        assertTrue(path.contains(8));
        assertTrue(path.contains(10));
        assertTrue(path.contains(11));

    }

    @Test
    public void longestPathOnRealData() {
        IOFacade ioFacade = new IOFacade();

        Graph graph = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath());

        List path = GraphUtils.getLongestPath(graph);

        assertEquals(GraphUtils.getRoot(graph), path.get(0));

        for (Object node : path) {

            for (Object successor : graph.getSuccessors(node)) {
                if (!path.contains(successor)) {

                    System.out.print(successor + " => [ ");
                    List list = GraphTraversalUtils.dfs(graph, successor);
                    for (Object o : list) {
                        System.out.print(o);
                        if (graph.outDegree(o) == 0) {
                            System.out.print("*");
                        }
                        System.out.print(" , ");
                    }
                    System.out.print("]");
                    System.out.println();

                }


            }

        }

    }

    @Test
    public void noUnconnectedComponents() {
        Graph<Integer, String> graph = GraphMaker.createTestBinaryTree();

        for (Integer vertex: graph.getVertices()) {
            assertFalse(GraphUtils.isUnconnectedComponent(graph, vertex));
        }
    }

    @Test
    public void checkUnconnectedComponents() {
        Graph<Integer, String> graph = GraphMaker.createGraphWithUnconnectedComponents();

        assertEquals(9, graph.getVertexCount());
        assertEquals(4, graph.getEdgeCount());

        int unconnectedComponentsCount = 0;
        for (Integer vertex: graph.getVertices()) {
            if (GraphUtils.isUnconnectedComponent(graph, vertex)) {
                unconnectedComponentsCount++;
            }
        }

        assertEquals(4, unconnectedComponentsCount);

    }

}
