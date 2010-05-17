package se.lnu.thesis.algorithm;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.utils.GraphUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private Graph goSubGraph;
    private Graph clusterSubGraph;

    private Object selectedNode;

    // TODO make smarter cache!
    private Map<Object, Graph> goCache;
    private Map<Object, Graph> clusterCache;

    public Extractor() {
        goCache = new HashMap<Object, Graph>();
        clusterCache = new HashMap<Object, Graph>();
    }

    public void extractSubGraphs(MyGraph goGraph, MyGraph clusterGraph, Object goNode) {

        setSelectedNode(goNode);

        if (goCache.containsKey(goNode) && clusterCache.containsKey(goNode)) {
            LOGGER.info("Allready computed. Loading from cache..");

            goSubGraph = goCache.get(goNode);
            clusterSubGraph = clusterCache.get(goNode);
        } else {
            goSubGraph = new DirectedSparseGraph();
            Set goSubGraphLeafs = GraphUtils.getInstance().getSubgraphAndLeafs(goGraph, goSubGraph, goNode);

            clusterSubGraph = new DirectedSparseGraph();
            Object clusterSubGraphRoot = null;
            for (Object goLeaf : goSubGraphLeafs) {

                String label = goGraph.getLabel(goLeaf);

                Set leafs = clusterGraph.getLeafsByLabel(label);

                if (leafs.size() > 1) {
                    String error = "Error! Found couple leafs with label '" + label + "'!!";
                    LOGGER.error(error);
                    throw new IllegalStateException(error);
                }

                List connectedNodes = GraphUtils.getInstance().invertDfsNodes(clusterGraph, leafs.iterator().next());
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

            goCache.put(goNode, goSubGraph);
            clusterCache.put(goNode, clusterSubGraph);
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

    public void setClusterSubGraph(Graph clusterSubGraph) {
        this.clusterSubGraph = clusterSubGraph;
    }

    public Object getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(Object selectedNode) {
        this.selectedNode = selectedNode;
    }
}
