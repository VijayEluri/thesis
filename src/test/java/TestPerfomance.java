

import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.ClusterGraphContainer;
import se.lnu.thesis.element.Dimensional;
import se.lnu.thesis.element.GraphContainer;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.JungTreeYedHandler;
import se.lnu.thesis.io.graphml.JungYedHandler;
import se.lnu.thesis.io.graphml.MyGraphYedHandler;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 16:58:02
 */
public class TestPerfomance {

    public static final Logger LOGGER = Logger.getLogger(TestPerfomance.class);

    @Test
    @Category(PerfomanceTest.class)
    public void iterate() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

        Graph graph = (Graph) parser.load(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath()).get(0);

        final int vertexCount = 14623;
        assertEquals(vertexCount, graph.getVertexCount());

        final int edges = 14622;
        assertEquals(edges, graph.getEdgeCount());


        long start = System.currentTimeMillis();

        Set visited = new HashSet();
        Stack stack = new Stack();

        Object root = GraphUtils.getRoot(graph);

        stack.push(root);
        visited.add(root);

        while (!stack.isEmpty()) {
            Object parent = stack.pop();

            for (Object child : graph.getSuccessors(parent)) {
                if (!visited.contains(child)) {
                    visited.add(child);
                    stack.push(child);
                }
            }

        }

        long end = System.currentTimeMillis();

        assertEquals(vertexCount, visited.size());

        LOGGER.info("Done in " + (end - start) + "s");
    }

    @Test
    @Category(PerfomanceTest.class)
    public void measureJungTreeYedHandlerLoadingInsideTry() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

        Graph graph = null;
        long start = System.currentTimeMillis();
        try {
            graph = (Graph) parser.load(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath()).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        LOGGER.info("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "ms");

        assertEquals(14623, graph.getVertexCount());
        assertEquals(14622, graph.getEdgeCount());
    }

    @Test
    @Category(PerfomanceTest.class)
    public void measureJungTreeYedHandlerClusterLoading() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

        Graph graph = null;
        long start = System.currentTimeMillis();
        graph = (Graph) parser.load(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath()).get(0);
        long end = System.currentTimeMillis();

        LOGGER.info("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "ms");

        assertEquals(14623, graph.getVertexCount());
        assertEquals(14622, graph.getEdgeCount());
    }

    @Test
    @Category(PerfomanceTest.class)
    public void measureMyGraphYedHandlerClusterLoading() {
        GraphMLParser parser = new GraphMLParser(new MyGraphYedHandler());

        long start = System.currentTimeMillis();
        MyGraph graph = (MyGraph) parser.load(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath()).get(0);
        long end = System.currentTimeMillis();

        LOGGER.info("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "ms");

        assertEquals(14623, graph.getVertexCount());
        assertEquals(14622, graph.getEdgeCount());

        assertEquals("AFFX-HXB2_5_at", graph.getLabel("n8"));
        assertEquals("n8", graph.getNodeByLabel("AFFX-HXB2_5_at"));
    }


    @Test
    @Category(PerfomanceTest.class)
    public void measureJungYedHandlerClusterLoading() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load("RealClusterGraph.graphml").get(0);
        long end = System.currentTimeMillis();

        assertEquals(14623, graph.getVertexCount());
        assertEquals(14622, graph.getEdgeCount());

        LOGGER.info("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "ms");
    }

   /* @Test      TODO implement this test for some graph
    public void measurePolarDendrogramLayoutComputationForCluster() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());
        Graph graph = (Graph) parser.load("RealClusterGraph.graphml").get(0);

        GraphContainer root = ClusterGraphContainer.init();

        PolarDendrogramLayout layout = new PolarDendrogramLayout();
        layout.setRadius(0.9);
        layout.setGraph(graph);
        layout.setRoot(root);

        System.gc();

        long start = System.currentTimeMillis();
        layout.compute();
        long end = System.currentTimeMillis();

        LOGGER.info("Initialize layout using Graph done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "ms");

    }*/


    @Test
    @Category(PerfomanceTest.class)
    public void measureJungYedHandlerGOLoading() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load("RealGOGraph.graphml").get(0);
        long end = System.currentTimeMillis();

        LOGGER.info("Loading graph from file done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
    }


    @Test
    public void printGeneOntologyGraph() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load(getClass().getClassLoader().getResource("RealGOGraph.graphml").getPath()).get(0);
        long end = System.currentTimeMillis();

        LOGGER.info("Loading graph from file done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        int leafs = 0;
        int nodes = 0;
        int roots = 0;

        for (Object nodeId : graph.getVertices()) {
            if (graph.outDegree(nodeId) == 0) {
                leafs++;
            } else {
                if (graph.inDegree(nodeId) == 0) {
                    roots++;
                } else {
                    nodes++;
                }
            }
        }

        Assert.assertEquals(leafs + roots + nodes, graph.getVertexCount());

        LOGGER.info("Vertex count:" + graph.getVertexCount());
        LOGGER.info("Edge count:" + graph.getEdgeCount());
        LOGGER.info("Leafs:" + leafs);
        LOGGER.info("Nodes:" + nodes);
        LOGGER.info("Roots:" + roots);


        LOGGER.info("Computing layout done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
    }

    public void measureJungKKLayoutForGO() {
        IOFacade facade = new IOFacade();
        Graph graph = facade.loadFromGml("RealGOGraph.graphml");

        KKLayout layout = new KKLayout(graph);
        layout.setSize(new Dimension(800, 600));

        System.gc();

        long start = System.currentTimeMillis();
        while (!layout.done()) {
            layout.step();
        }
        long end = System.currentTimeMillis();

        LOGGER.info("Computing layout done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
    }


}
