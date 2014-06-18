

import edu.uci.ics.jung.graph.Graph;
import static org.junit.Assert.*;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.io.gml.GmlReader;
import se.lnu.thesis.io.gml.MyGraphGmlReader;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.08.2010
 * Time: 16:09:13
 * To change this template use File | Settings | File Templates.
 */
public class TestGmlReader {


    @Test
    public void readFixedAsterix() throws IOException {
        GmlReader reader = new GmlReader();

        Graph graph = reader.read(getClass().getClassLoader().getResourceAsStream("data/FixedAsterix.gml"));

        assertEquals(100, graph.getVertexCount());

        for (int i = 0; i < 100; i++) {
            assertTrue(graph.containsVertex(i));
        }

        assertEquals(99, graph.getEdgeCount());

        assertNotNull(graph.findEdge(60, 65));
        assertNotNull(graph.findEdge(62, 71));
        assertNotNull(graph.findEdge(68, 75));
        assertNotNull(graph.findEdge(72, 79));
    }

    @Test
    public void readAsterix() throws IOException {
        GmlReader reader = new GmlReader();

        Graph graph = reader.read(getClass().getClassLoader().getResourceAsStream("data/Asterix.gml"));

        assertEquals(100, graph.getVertexCount());

        for (int i = 0; i < 100; i++) {
            assertTrue(graph.containsVertex(i));
        }

        assertEquals(99, graph.getEdgeCount());

        assertNotNull(graph.findEdge(60, 65));
        assertNotNull(graph.findEdge(62, 71));
        assertNotNull(graph.findEdge(68, 75));
        assertNotNull(graph.findEdge(72, 79));
    }

    @Test
    public void readRealGO() throws IOException {
        MyGraphGmlReader reader = new MyGraphGmlReader();

        MyGraph graph = reader.read(getClass().getClassLoader().getResourceAsStream("data/RealGOGraph.gml"));

        assertNotNull(graph);
        assertEquals(TestRealData.GO_NODE_COUNT, graph.getVertexCount());
        assertEquals(TestRealData.GO_EDGE_COUNT, graph.getEdgeCount());

    }

    @Test
    public void testRealGeneOntology() throws IOException {
        IOFacade ioFacade = new IOFacade();

        MyGraph gmlGraph = ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealGOGraph.gml").getFile());
        assertNotNull(gmlGraph);
        assertEquals(TestRealData.GO_NODE_COUNT, gmlGraph.getVertexCount());
        assertEquals(TestRealData.GO_EDGE_COUNT, gmlGraph.getEdgeCount());


        MyGraph graphmlGraph = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("data/RealGOGraph.graphml").getPath());
        assertNotNull(graphmlGraph);
        assertEquals(TestRealData.GO_NODE_COUNT, graphmlGraph.getVertexCount());
        assertEquals(TestRealData.GO_EDGE_COUNT, graphmlGraph.getEdgeCount());


        assertEquals(graphmlGraph.getVertexCount(), gmlGraph.getVertexCount());
        assertEquals(graphmlGraph.getEdgeCount(), gmlGraph.getEdgeCount());

        assertEquals(graphmlGraph.getLabelCount(), gmlGraph.getLabelCount());

        assertTrue(gmlGraph.getLabels().containsAll(graphmlGraph.getLabels()));
    }

    @Test
    public void testRealCluster() throws IOException {
        IOFacade ioFacade = new IOFacade();

        MyGraph gmlGraph = ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealClusterGraph.gml").getFile());
        assertNotNull(gmlGraph);
        assertEquals(TestRealData.CLUSTER_NODE_COUNT, gmlGraph.getVertexCount());
        assertEquals(TestRealData.CLUSTER_EDGE_COUNT, gmlGraph.getEdgeCount());


        MyGraph graphmlGraph = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath());
        assertNotNull(graphmlGraph);
        assertEquals(TestRealData.CLUSTER_NODE_COUNT, graphmlGraph.getVertexCount());
        assertEquals(TestRealData.CLUSTER_EDGE_COUNT, graphmlGraph.getEdgeCount());


        assertEquals(graphmlGraph.getVertexCount(), gmlGraph.getVertexCount());
        assertEquals(graphmlGraph.getEdgeCount(), gmlGraph.getEdgeCount());

        assertEquals(graphmlGraph.getLabelCount(), gmlGraph.getLabelCount());

        assertTrue(gmlGraph.getLabels().containsAll(graphmlGraph.getLabels()));
    }

}