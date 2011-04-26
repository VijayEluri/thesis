package se.lnu.thesis.algorithm;

import com.google.common.collect.MapMaker;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.utils.GraphTraversalUtils;
import se.lnu.thesis.utils.GraphUtils;

import java.util.List;
import java.util.Map;
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
public class Extractor {

    public static final Logger LOGGER = Logger.getLogger(Extractor.class);

    public static final int CACHE_MAXIMUM_SIZE = 10;
    public static final int CACHE_EXPIRE_TIME = 1;

    protected MyGraph goSubGraph;
    protected MyGraph clusterSubGraph;

    protected Object selectedNode;

    protected Map<Object, MyGraph> goCache;
    protected Map<Object, MyGraph> clusterCache;

    public Extractor() {

    }

    public void extractSubGraphs(MyGraph goGraph, MyGraph clusterGraph, Object goVertex) {
        initCache();

        if (goGraph == null) {
            LOGGER.error("Gene Ontology graph is null");
            return;
        }

        if (clusterGraph == null) {
            LOGGER.error("Cluster graph is null!!");
            return;
        }

        if (goVertex == null) {
            LOGGER.error("Gene Ontology vertex is null!!");
            return;
        }

        setSelectedNode(goVertex);

        if (goCache.containsKey(goVertex) && clusterCache.containsKey(goVertex)) {
            LOGGER.info("Already computed. Loading from cache..");

            goSubGraph = goCache.get(goVertex);
            clusterSubGraph = clusterCache.get(goVertex);
        } else {
            long startTime = System.currentTimeMillis();

            goSubGraph = new MyGraph();
            Set goSubGraphLeafs = GraphUtils.extractSubgraph(goGraph, goSubGraph, goVertex);

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

            LOGGER.info("GO subgraph [" + getGoSubGraph().getVertexCount() + ", " + getGoSubGraph().getEdgeCount() + "]");
            LOGGER.info("Cluster subgraph [" + getClusterSubGraph().getVertexCount() + ", " + getClusterSubGraph().getEdgeCount() + "]");

            goCache.put(goVertex, goSubGraph);
            clusterCache.put(goVertex, clusterSubGraph);

            long endTime = System.currentTimeMillis();
            LOGGER.info("Subgraphs extraction tooked " + TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.MILLISECONDS) + "s");
        }

    }

    /**
     * All extracted subgraphs stored in the cache.
     * One <code>Map</code> for Gene Ontology subgraph and one for Cluster subgraph.
     * As cache instance used Google smart map (<code>MapMaker</code> which stores <code>Extractor.CACHE_MAXIMUM_SIZE</code> for
     * <code>Extractor.CACHE_EXPIRE_TIME</code> in minutes after last READ(!) access.
     * <p/>
     * <code>new MapMaker().maximumSize(CACHE_MAXIMUM_SIZE).expireAfterAccess(CACHE_EXPIRE_TIME, TimeUnit.MINUTES).makeMap();</code>
     */
    public void initCache() {
        if (goCache == null || clusterCache == null) {
            MapMaker cacheMaker = new MapMaker().maximumSize(CACHE_MAXIMUM_SIZE).expireAfterAccess(CACHE_EXPIRE_TIME, TimeUnit.MINUTES);

            if (goCache == null) {
                goCache = cacheMaker.makeMap();
            }

            if (clusterCache == null) {
                clusterCache = cacheMaker.makeMap();
            }
        }

        goSubGraph = null;
        clusterSubGraph = null;
        selectedNode = null;
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

    public void setSelectedNode(Object selectedNode) {
        LOGGER.info("Selected GO vertex: " + selectedNode);

        this.selectedNode = selectedNode;
    }
}
