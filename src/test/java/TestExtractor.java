
import edu.uci.ics.jung.graph.Graph;
import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.MyGraphYedHandler;
import se.lnu.thesis.utils.GraphMaker;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 09.04.2010
 * Time: 15:57:30
 */
public class TestExtractor {

    @Test
    public void optimize() {

        Graph graph = GraphMaker.createHighBinaryTree();

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

        Graph graph = GraphMaker.createHighBinaryTree();

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
        Assert.assertEquals(TestRealData.GO_NODE_COUNT, goGraph.getVertexCount());
        Assert.assertEquals(TestRealData.GO_EDGE_COUNT, goGraph.getEdgeCount());

        MyGraph clusterGraph = (MyGraph) new GraphMLParser(new MyGraphYedHandler()).load(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath()).get(0);
        Assert.assertEquals(TestRealData.CLUSTER_NODE_COUNT, clusterGraph.getVertexCount());
        Assert.assertEquals(TestRealData.CLUSTER_EDGE_COUNT, clusterGraph.getEdgeCount());

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
