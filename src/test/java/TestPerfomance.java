import com.google.common.cache.CacheBuilder;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.JungTreeYedHandler;
import se.lnu.thesis.io.graphml.JungYedHandler;
import se.lnu.thesis.io.graphml.MyGraphYedHandler;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

        Graph graph = (Graph) parser.load(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath()).get(0);

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
            graph = (Graph) parser.load(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath()).get(0);
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
        graph = (Graph) parser.load(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath()).get(0);
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
        MyGraph graph = (MyGraph) parser.load(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath()).get(0);
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
        String file = getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath();

        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        List list = parser.load(file);
        long end = System.currentTimeMillis();

        assertNotNull(list);
        Assert.assertFalse(list.isEmpty());

        Graph graph = (Graph) list.get(0);

        assertEquals(14623, graph.getVertexCount());
        assertEquals(14622, graph.getEdgeCount());

        LOGGER.info("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "ms");
    }

    @Test
    @Category(PerfomanceTest.class)
    public void measureJungYedHandlerGOLoading() {
        String file = getClass().getClassLoader().getResource("data/RealGOGraph.graphml").getPath();

        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        List list = parser.load(file);
        long end = System.currentTimeMillis();

        assertNotNull(list);
        Assert.assertFalse(list.isEmpty());

        Graph graph = (Graph) list.get(0);
        assertNotNull(graph);

        LOGGER.info("Loading graph from file done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
    }


    @Test
    public void printGeneOntologyGraph() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load(getClass().getClassLoader().getResource("data/RealGOGraph.graphml").getPath()).get(0);
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

    @Deprecated
    public void measureJungKKLayoutForGO() {
        IOFacade facade = new IOFacade();
        Graph graph = facade.loadMyGraphFromGml("data/RealGOGraph.graphml");

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


    /**
     * Try to compute subgraphs for all Gene Ontology nodes.
     */
    public void computeAllSubgraphs() {
        IOFacade ioFacade = new IOFacade();
        MyGraph go = ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealGOGraph.gml").getPath());
        MyGraph cluster = ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealClusterGraph.gml").getPath());

        System.gc();

        Extractor extractor = new Extractor() {
            @Override
            public void initCache() {
                CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().expireAfterAccess(CACHE_EXPIRE_TIME, TimeUnit.MINUTES);

                if (goCache == null) {
                    goCache = cacheBuilder.build();
                }

                if (clusterCache == null) {
                    clusterCache = cacheBuilder.build();
                }
            }
        };

        int subgraphs = 0;
        int vertices = 0;
        long startTime = System.currentTimeMillis();
        try {
            for (Object o : go.getVertices()) {
                if (GraphUtils.isNode(go, o)) {
                    subgraphs++;
                    extractor.extract(go, cluster, o);
                    vertices += extractor.getGoSubGraph().getVertexCount() + extractor.getClusterSubGraph().getVertexCount();
                }
            }
        } catch (Throwable e) {
            long end = System.currentTimeMillis();

            System.out.println("Subgraph extracted: " + subgraphs);
            System.out.println("Total vertex count: " + vertices);
            System.out.println(TimeUnit.SECONDS.convert(end - startTime, TimeUnit.MILLISECONDS));

            System.out.println(e.getStackTrace());
        }

    }


}
