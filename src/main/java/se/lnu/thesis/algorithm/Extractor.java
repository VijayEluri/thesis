package se.lnu.thesis.algorithm;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.uci.ics.jung.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.utils.GraphTraversalUtils;
import se.lnu.thesis.utils.GraphUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 23:04:50
 * <p/>
 * Main extraction rutine
 * <p/>
 * 1. Node - compute all leafs
 * 2. Compute all nodes connected to each leaf from bottom to up
 * 3. Merge. Find shared parent.
 * 4. Create graph from this structure.
 */
@Component
public class Extractor {

    public static final Logger LOGGER = LoggerFactory.getLogger(Extractor.class);

    public static final int CACHE_MAXIMUM_SIZE = 10;
    public static final int CACHE_EXPIRE_TIME = 1;

    protected MyGraph goSubGraph;
    protected MyGraph clusterSubGraph;

    protected Object selectedNode;

    protected Cache<Object, MyGraph> goCache;
    protected Cache<Object, MyGraph> clusterCache;

    /**
     * All extracted subgraphs stored in the cache.
     * One <code>Map</code> for Gene Ontology subgraph and one for Cluster subgraph.
     * As cache instance used Google smart map (<code>MapMaker</code> which stores <code>Extractor.CACHE_MAXIMUM_SIZE</code> for
     * <code>Extractor.CACHE_EXPIRE_TIME</code> in minutes after last READ(!) access.
     * <p/>
     * <code>new MapMaker().maximumSize(CACHE_MAXIMUM_SIZE).expireAfterAccess(CACHE_EXPIRE_TIME, TimeUnit.MINUTES).makeMap();</code>
     */
    @PostConstruct
    public void initCache() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().
                maximumSize(CACHE_MAXIMUM_SIZE).
                initialCapacity(CACHE_MAXIMUM_SIZE).
                expireAfterAccess(CACHE_EXPIRE_TIME, TimeUnit.MINUTES);

        if (goCache == null) {
            goCache = cacheBuilder.build();
        }

        if (clusterCache == null) {
            clusterCache = cacheBuilder.build();
        }
    }

    /**
     * Clear latest selected vertex and corresponded extracted sub-graphs.
     */
    public void reset() {
        goSubGraph = null;
        clusterSubGraph = null;
        selectedNode = null;
    }

    /**
     * Extract sub-graphs from goGraph and clusterGraph for the selected goVertex in GO graphs.
     *
     * @param goGraph      GO graphs where vertex was selected.
     * @param clusterGraph Cluster graph.
     * @param goVertex     Selected vertex.
     */
    public void extract(MyGraph goGraph, MyGraph clusterGraph, Object goVertex) {
        Preconditions.checkNotNull(goGraph);
        Preconditions.checkNotNull(clusterGraph);
        Preconditions.checkNotNull(goVertex);

        reset();

        LOGGER.info("Compute sub-graphs for GO vertex: " + selectedNode);

        if (!loadFromCache(goVertex)) {
            long startTime = System.currentTimeMillis();

            Set goSubGraphLeafs = extractGOSubGraph(goGraph, goVertex);

            extractClusterSubGraph(goGraph, clusterGraph, goSubGraphLeafs);

            LOGGER.info("GO subgraph [" + getGoSubGraph().getVertexCount() + ", " + getGoSubGraph().getEdgeCount() + "]");
            LOGGER.info("Cluster subgraph [" + getClusterSubGraph().getVertexCount() + ", " + getClusterSubGraph().getEdgeCount() + "]");

            goCache.put(goVertex, goSubGraph);
            clusterCache.put(goVertex, clusterSubGraph);

            long endTime = System.currentTimeMillis();
            LOGGER.info("Sub-graphs extraction tooked " + TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.MILLISECONDS) + "s");
        }

    }


    protected Set extractGOSubGraph(MyGraph goGraph, Object goVertex) {
        goSubGraph = new MyGraph();
        return GraphUtils.extractSubgraph(goGraph, goSubGraph, goVertex);
    }

    protected void extractClusterSubGraph(MyGraph goGraph, MyGraph clusterGraph, Set goSubGraphLeafs) {
        clusterSubGraph = new MyGraph();
        Object clusterSubGraphRoot = null;
        for (Object goLeaf : goSubGraphLeafs) {

            String leafLabel = goGraph.getLabel(goLeaf);

            Object leaf = clusterGraph.getNodeByLabel(leafLabel);

            if (leaf == null) {
                String error = "Error! Can't find leaf in the cluster graph with label '" + leafLabel + "'!!";
                LOGGER.error(error);
                throw new IllegalStateException(error);
            }

            List connectedNodes = GraphTraversalUtils.invertDfs(clusterGraph, leaf);
            for (int i = 0; i <= connectedNodes.size() - 1; i++) {
                Object node1, node2 = null;

                node1 = connectedNodes.get(i);
                clusterSubGraph.addVertex(node1);

                if (i + 1 <= connectedNodes.size() - 1) {
                    node2 = connectedNodes.get(i + 1);

                    clusterSubGraph.addVertex(node2);
                    clusterSubGraph.addEdge(node2 + "->" + node1, node2, node1);
                }

            }

            clusterSubGraphRoot = connectedNodes.get(connectedNodes.size() - 1);
        }

        removeRootChains(clusterSubGraph, clusterSubGraphRoot);
    }


    /**
     * Chech if <code>vertex</code> was computed before and is stored in the cache.
     *
     * @param vertex GO vertex to compute sub-graphs for.
     * @return <code>True</code> if vertex was computed before and corresponded sub-graphs are loaded from the cache.
     *         <code>False</code> otherwise.
     */
    public boolean loadFromCache(Object vertex) {
        Preconditions.checkNotNull(goCache);
        Preconditions.checkNotNull(clusterCache);
        Preconditions.checkNotNull(vertex);

        if (goCache.getIfPresent(vertex) != null && clusterCache.getIfPresent(vertex) != null) {
            LOGGER.info("Vertex '" + vertex + "' already computed. Loading from the cache..");

            goSubGraph = goCache.getIfPresent(vertex);
            clusterSubGraph = clusterCache.getIfPresent(vertex);
            selectedNode = vertex;

            return true;
        } else {
            LOGGER.info("The are no data in the the cache.");

            return false;
        }
    }

    public void removeRootChains(Graph graph, Object root) {
        if (graph.outDegree(root) == 0) {
            return;
        }

        if (graph.getSuccessorCount(root) == 1) {
            Object next = graph.getSuccessors(root).iterator().next();

            graph.removeVertex(root);
            removeRootChains(graph, next);
        }
    }

    public Graph getGoSubGraph() {
        return goSubGraph;
    }

    public Graph getClusterSubGraph() {
        return clusterSubGraph;
    }

    public Object getSelectedNode() {
        return selectedNode;
    }

}
