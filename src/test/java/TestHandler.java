

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Tree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.JungTreeYedHandler;
import se.lnu.thesis.io.graphml.JungYedHandler;
import se.lnu.thesis.io.graphml.MyGraphYedHandler;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 08.04.2010
 * Time: 13:17:57
 */
public class TestHandler {

    @Test
    public void jungTreeYedHandler() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());
        Tree tree = (Tree) parser.load(getClass().getClassLoader().getResource("cluster.graphml").getPath()).get(0);

        assertEquals(23, tree.getVertexCount());
        assertEquals(11, tree.getHeight());

        assertTrue(tree.containsVertex("n10"));
        assertEquals(5, tree.getDepth("n10"));

        assertTrue(tree.containsVertex("n0"));
        assertEquals(0, tree.getDepth("n0"));
    }

    @Test
    public void myGraphYedHandler() {
        GraphMLParser parser = new GraphMLParser(new MyGraphYedHandler());

        List graphs = parser.load(getClass().getClassLoader().getResource("cluster.graphml").getPath());
        assertEquals(1, graphs.size());

        MyGraph graph = (MyGraph) graphs.get(0);

        assertEquals(23, graph.getVertexCount());
        assertEquals(23, graph.getLabelCount());

        assertTrue(graph.containsVertex("n10"));
        assertTrue(graph.containsLabel("11"));

        assertEquals("11", graph.getLabel("n10"));
        assertEquals("n10", graph.getNodeByLabel("11"));

        assertTrue(graph.containsVertex("n0"));

        assertEquals("n0", graph.getNodeByLabel("1"));
    }

    @Test
    public void myGraphYedHandlerRealdata() {
        MyGraphYedHandler yedHandler = new MyGraphYedHandler();
        GraphMLParser parser = new GraphMLParser(yedHandler);

        List graphs = parser.load(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath());
        assertEquals(1, graphs.size());

        MyGraph graph = (MyGraph) graphs.get(0);

        assertEquals(14623, graph.getVertexCount());

        assertTrue(graph.containsVertex("n10"));

        assertEquals("AFFX-LysX-3_at", graph.getLabel("n10"));
        assertEquals("n10", graph.getNodeByLabel("AFFX-LysX-3_at"));
    }

    @Test
    public void jungYedHandler() {
        JungYedHandler yedHandler = new JungYedHandler();

        GraphMLParser parser = new GraphMLParser(yedHandler);

        List graphs = parser.load(getClass().getClassLoader().getResource("cluster.graphml").getPath());
        assertEquals(1, graphs.size());

        Graph graph = (Graph) graphs.get(0);

        assertEquals(23, graph.getVertexCount());
        assertEquals(graph.getVertexCount(), yedHandler.getIdLabel().size());

        assertTrue(graph.containsVertex("n10"));
        assertTrue(graph.containsVertex("n0"));
    }

}
