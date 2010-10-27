package se.lnu.thesis.test;

import edu.uci.ics.jung.graph.Graph;
import static org.junit.Assert.*;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.io.gml.GmlReader;

import java.io.File;
import java.io.FileInputStream;
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

        Graph graph = reader.read(new FileInputStream(new File("FixedAsterix.gml")));

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

        MyGraph graph = reader.read(new FileInputStream(new File("Asterix.gml")));

        assertEquals(100, graph.getVertexCount());

        for (int i = 0; i < 100; i++) {
            assertTrue(graph.containsVertex(i));
        }

        assertEquals(99, graph.getEdgeCount());

        assertNotNull(graph.findEdge(60, 65));
        assertNotNull(graph.findEdge(62, 71));
        assertNotNull(graph.findEdge(68, 75));
        assertNotNull(graph.findEdge(72, 79));

        assertEquals(100, graph.getLabelCount());

        for (Object o : graph.getLabels()) {
            System.out.println(o);
        }
    }

    @Test
    public void readRealGO() throws IOException {
        GmlReader reader = new GmlReader();

        MyGraph graph = reader.read(new FileInputStream(new File("RealGOGraph.gml")));

        assertNotNull(graph);
        assertEquals(10042, graph.getVertexCount());
        assertEquals(24155, graph.getEdgeCount());

    }

    @Test
    public void testRealGeneOntology() throws IOException {
        IOFacade ioFacade = new IOFacade();

        MyGraph gmlGraph = ioFacade.loadFromGml("RealGOGraph.gml");
        assertNotNull(gmlGraph);
        assertEquals(10042, gmlGraph.getVertexCount());
        assertEquals(24155, gmlGraph.getEdgeCount());


        MyGraph graphmlGraph = ioFacade.loadFromYedGraphml("RealGOGraph.graphml");
        assertNotNull(graphmlGraph);
        assertEquals(10042, graphmlGraph.getVertexCount());
        assertEquals(24155, graphmlGraph.getEdgeCount());


        assertEquals(graphmlGraph.getVertexCount(), gmlGraph.getVertexCount());
        assertEquals(graphmlGraph.getEdgeCount(), gmlGraph.getEdgeCount());

        assertEquals(graphmlGraph.getLabelCount(), gmlGraph.getLabelCount());

        assertTrue(gmlGraph.getLabels().containsAll(graphmlGraph.getLabels()));
    }

    @Test
    public void testRealCluster() throws IOException {
        IOFacade ioFacade = new IOFacade();

        MyGraph gmlGraph = ioFacade.loadFromGml("RealClusterGraph.gml");
        assertNotNull(gmlGraph);
        assertEquals(14623, gmlGraph.getVertexCount());
        assertEquals(14622, gmlGraph.getEdgeCount());


        MyGraph graphmlGraph = ioFacade.loadFromYedGraphml("RealClusterGraph.graphml");
        assertNotNull(graphmlGraph);
        assertEquals(14623, graphmlGraph.getVertexCount());
        assertEquals(14622, graphmlGraph.getEdgeCount());


        assertEquals(graphmlGraph.getVertexCount(), gmlGraph.getVertexCount());
        assertEquals(graphmlGraph.getEdgeCount(), gmlGraph.getEdgeCount());

        assertEquals(graphmlGraph.getLabels(), gmlGraph.getLabels());

        assertTrue(gmlGraph.getLabels().containsAll(graphmlGraph.getLabels()));
    }

}