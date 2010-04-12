package se.lnu.thesis.algorithm;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.utils.GraphUtils;

import java.util.List;
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

    public Graph extractSubGraph(MyGraph goGraph, MyGraph clusterGraph, Object goNode) {
        // TODO !!! add caching!
        Graph subGraph = new DirectedSparseGraph();

        Object subGraphRoot = null;

        for (Object goLeaf : GraphUtils.getInstance().getNodeLeafs(goGraph, goNode)) {

            String label = goGraph.getLabel(goLeaf);

            Set leafs = clusterGraph.getLeafsByLabel(label);

            if (leafs.size() > 1) {
                String error = "Error! Found couple leafs with label '" + label + "'!!";
                System.out.println(error);                    // TODO use logger
                throw new IllegalStateException(error);
            }

            List connectedNodes = GraphUtils.getInstance().invertDfsNodes(clusterGraph, leafs.iterator().next());
            for (int i = 0; i <= connectedNodes.size() - 1; i++) {
                Object node1, node2 = null;

                node1 = connectedNodes.get(i);
                subGraph.addVertex(node1);

                if (i + 1 <= connectedNodes.size() - 1) {
                    node2 = connectedNodes.get(i + 1);

                    subGraph.addVertex(node2);
                    subGraph.addEdge(node2 + "->" + node1, node2, node1);
                }

            }

            subGraphRoot = connectedNodes.get(connectedNodes.size() - 1); // TODO maybe use clusterGraph.getRoot() ?
        }

        removeRootChains(subGraph, subGraphRoot);

        return subGraph;
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

}
