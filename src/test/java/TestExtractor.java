
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.MyGraphYedHandler;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 09.04.2010
 * Time: 15:57:30
 */
public class TestExtractor {

    /**
     * ___1
     * ___|
     * ___2
     * ___|
     * ___3
     * ___|
     * ___4
     * ___|
     * ___5
     * ___|
     * ___6
     * __/_\
     * _7___8
     * _____|
     * _____9
     * _____|
     * ____10
     *
     * @return DirectedSparceGraph
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

        graph.addEdge("1->2", 1, 2);
        graph.addEdge("2->3", 2, 3);
        graph.addEdge("3->4", 3, 4);
        graph.addEdge("4->5", 4, 5);
        graph.addEdge("5->6", 5, 6);
        graph.addEdge("6->7", 6, 7);
        graph.addEdge("6->8", 6, 8);
        graph.addEdge("8->9", 8, 9);
        graph.addEdge("9->10", 9, 10);

        return graph;
    }

    @Test
    public void optimize() {

        Graph graph = createGraph();

        Assert.assertEquals(10, graph.getVertexCount());
        Assert.assertEquals(9, graph.getEdgeCount());
        Assert.assertTrue(graph.containsVertex(1));
        Assert.assertTrue(graph.containsVertex(3));
        Assert.assertTrue(graph.containsVertex(6));

        Extractor extractor = new Extractor();
        extractor.removeRootChains(graph, 1);

        Assert.assertEquals(5, graph.getVertexCount());
        Assert.assertEquals(4, graph.getEdgeCount());
        Assert.assertFalse(graph.containsVertex(1));
        Assert.assertFalse(graph.containsVertex(3));
        Assert.assertTrue(graph.containsVertex(6));
    }

    @Test
    public void optimizeFromMiddle() {

        Graph graph = createGraph();

        Assert.assertEquals(10, graph.getVertexCount());
        Assert.assertEquals(9, graph.getEdgeCount());

        Assert.assertTrue(graph.containsVertex(1));
        Assert.assertTrue(graph.containsVertex(3));
        Assert.assertTrue(graph.containsVertex(6));

        Extractor extractor = new Extractor();
        extractor.removeRootChains(graph, 4);

        Assert.assertEquals(8, graph.getVertexCount());
        Assert.assertEquals(6, graph.getEdgeCount());

        Assert.assertTrue(graph.containsVertex(1));
        Assert.assertTrue(graph.containsVertex(3));
        Assert.assertTrue(graph.containsVertex(6));
        Assert.assertFalse(graph.containsVertex(4));
        Assert.assertFalse(graph.containsVertex(5));
    }

    @Test
    public void extract() {

        MyGraph goGraph = (MyGraph) new GraphMLParser(new MyGraphYedHandler()).load(getClass().getClassLoader().getResource("RealGOGraph.graphml").getPath()).get(0);
        Assert.assertEquals(10042, goGraph.getVertexCount());
        Assert.assertEquals(24155, goGraph.getEdgeCount());

        MyGraph clusterGraph = (MyGraph) new GraphMLParser(new MyGraphYedHandler()).load(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath()).get(0);
        Assert.assertEquals(14623, clusterGraph.getVertexCount());
        Assert.assertEquals(14622, clusterGraph.getEdgeCount());

        Object node = goGraph.getNodeByLabel("tetrapyrrole metabolic process");
        Assert.assertNotNull(node != null);
        Assert.assertEquals("n7315", node);


        Extractor extractor = new Extractor();

        long start = System.currentTimeMillis();
        extractor.extractSubGraphs(goGraph, clusterGraph, "n7315");
        long end = System.currentTimeMillis();

        Graph subGraph = extractor.getClusterSubGraph();

        System.out.println("Extracting subgraph on real data. Done in " + (end - start) + "ms");

        Assert.assertEquals(1699, subGraph.getVertexCount());
/*
        Assert.assertEquals(6, subGraph.getEdgeCount());


        Assert.assertTrue(clusterGraph.containsVertex(3));
        Assert.assertTrue(clusterGraph.containsVertex(6));
        Assert.assertFalse(clusterGraph.containsVertex(4));
        Assert.assertFalse(clusterGraph.containsVertex(5));
*/
    }

    @Test
    public void extractSmall_10() {

        MyGraph goGraph = (MyGraph) new GraphMLParser(new MyGraphYedHandler()).load(getClass().getClassLoader().getResource("smallGO.graphml").getPath()).get(0);
        Assert.assertEquals(10, goGraph.getVertexCount());
        Assert.assertEquals(12, goGraph.getEdgeCount());

        MyGraph clusterGraph = (MyGraph) new GraphMLParser(new MyGraphYedHandler()).load(getClass().getClassLoader().getResource("smallCluster.graphml").getPath()).get(0);
        Assert.assertEquals(9, clusterGraph.getVertexCount());
        Assert.assertEquals(8, clusterGraph.getEdgeCount());

        Extractor extractor = new Extractor();

        extractor.extractSubGraphs(goGraph, clusterGraph, goGraph.getNodeByLabel("10"));
        Graph subGraph = extractor.getClusterSubGraph();

        Assert.assertEquals(3, subGraph.getVertexCount());
        Assert.assertTrue(subGraph.containsVertex("n0"));
        Assert.assertTrue(subGraph.containsVertex("n1"));
        Assert.assertTrue(subGraph.containsVertex("n2"));
    }

    @Test
    public void extractSmall_1() {

        MyGraph goGraph = (MyGraph) new GraphMLParser(new MyGraphYedHandler()).load(getClass().getClassLoader().getResource("smallGO.graphml").getPath()).get(0);
        Assert.assertEquals(10, goGraph.getVertexCount());
        Assert.assertEquals(12, goGraph.getEdgeCount());

        MyGraph clusterGraph = (MyGraph) new GraphMLParser(new MyGraphYedHandler()).load(getClass().getClassLoader().getResource("smallCluster.graphml").getPath()).get(0);
        Assert.assertEquals(9, clusterGraph.getVertexCount());
        Assert.assertEquals(8, clusterGraph.getEdgeCount());

        Extractor extractor = new Extractor();

        extractor.extractSubGraphs(goGraph, clusterGraph, goGraph.getNodeByLabel("1"));
        Graph subGraph = extractor.getClusterSubGraph();

        Assert.assertEquals(9, subGraph.getVertexCount());
    }

}
