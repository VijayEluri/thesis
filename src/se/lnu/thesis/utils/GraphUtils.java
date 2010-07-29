package se.lnu.thesis.utils;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.03.2010
 * Time: 20:56:26
 * <p/>
 * <p/>
 * Graph utility class.
 */
public class GraphUtils<V, E> {

    public static final Logger LOGGER = Logger.getLogger(GraphUtils.class);

    private static GraphUtils instance;

    public static GraphUtils getInstance() {
        if (instance == null) {
            instance = new GraphUtils();
        }
        return instance;
    }


    private GraphUtils() {
    }

    public List<V> invertDfsNodes(Graph<V, E> graph, V node) {

        Stack stack = new Stack();
        Set visited = new HashSet();
        List result = new LinkedList();

        stack.push(node);

        while (!stack.isEmpty()) {
            V next = (V) stack.pop();

            if (visited.add(next)) {

                result.add(next);

                for (V neighbor : graph.getPredecessors(next)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }

            }
        }

        return result;
    }

    public List<V> dfsNodes(Graph<V, E> graph, V node) {

        Stack stack = new Stack();
        Set visited = new HashSet();
        List result = new LinkedList();

        stack.push(node);


        while (!stack.isEmpty()) {
            V next = (V) stack.pop();

            if (visited.add(next)) {

                result.add(next);

                for (V neighbor : graph.getSuccessors(next)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }

            }
        }

        return result;
    }

    public V findRoot(Graph<V, E> graph) {
        for (V v : graph.getVertices()) {
            if (graph.inDegree(v) == 0) {
                return v;
            }
        }

        return null;
    }

    public int computeLevels(Graph<V, E> graph, Map<V, Integer> levels) {

        Integer graphHeight = 0;
        Integer level;

        for (V node : graph.getVertices()) {
            List<V> list = getInstance().invertDfsNodes(graph, node);

            level = list.size();

            if (levels.get(node) != null) {
                if (graphHeight > levels.get(node)) {
                    levels.put(node, level);
                }
            } else {
                levels.put(node, level);
            }

            if (level > graphHeight) {
                graphHeight = level;
            }
        }

        return graphHeight;
    }

    public int computeLevelsV2(Graph<V, E> graph, Map<V, Integer> levels) {

        int graphHeight = 0;
        Integer nodeLevel;

        for (V node : graph.getVertices()) {
            nodeLevel = getNodeHeight(graph, node, 0);

            if (levels.get(node) != null) {
                if (graphHeight > levels.get(node)) {
                    levels.put(node, nodeLevel);
                }
            } else {
                levels.put(node, nodeLevel);
            }

            if (nodeLevel > graphHeight) {
                graphHeight = nodeLevel;
            }
        }

        return graphHeight;
    }

    public int getNodeHeight(Graph<V, E> graph, V node, int start) {
        if (graph.getPredecessorCount(node) > 0) {
            start = getNodeHeight(graph, graph.getPredecessors(node).iterator().next(), ++start);
        }

        return start;
    }

    public boolean isLeaf(Graph<V, E> graph, V node) {
        return graph.outDegree(node) == 0;
    }

    public boolean isRoot(Graph<V, E> graph, V node) {
        return graph.inDegree(node) == 0;
    }

    public Set<V> getNodeLeafs(Graph<V, E> graph, V node) {
        Stack stack = new Stack();
        Set visited = new HashSet();
        Set result = new HashSet();

        stack.push(node);

        while (!stack.isEmpty()) {
            V next = (V) stack.pop();

            if (visited.add(next)) {

                if (isLeaf(graph, next)) {
                    result.add(next);
                } else {
                    for (Object neighbor : graph.getSuccessors(next)) {
                        if (!visited.contains(neighbor)) {
                            stack.push(neighbor);
                        }
                    }
                }


            }
        }

        return result;
    }

    /**
     * Extract subgraph from @graph starting root node @node
     *
     * @param graph    Graph from which to extract subgraph
     * @param subGraph Extracted subgraph
     * @param node     Root node for subgraph
     * @return Leafs from subgraph
     */
    public Set getSubgraphAndItsLeafs(Graph<V, E> graph, Graph<V, E> subGraph, V node) {
        Stack stack = new Stack();
        Set visited = new HashSet();
        Set subGraphLeafs = new HashSet();

        stack.push(node);

        while (!stack.isEmpty()) {
            V parent = (V) stack.pop();

            if (visited.add(parent)) {

                subGraph.addVertex(parent);

                if (isLeaf(graph, parent)) {
                    subGraphLeafs.add(parent);
                } else {
                    for (V child : graph.getSuccessors(parent)) {
                        if (!visited.contains(child)) {
                            stack.push(child);
                            subGraph.addVertex(child);
                            subGraph.addEdge((E) (parent + "->" + child), parent, child);
                        }
                    }
                }

            }
        }

        return subGraphLeafs;
    }

    /**
     * Get longest path for current tree graph
     *
     * @param graph Directed acyclic graph
     * @return List of elements from root to longest leaf
     */
    public List<V> longestPath(Graph<V, E> graph) {
        V longestNode = null;
        int nodeHeight = 0;

        int height;

        for (V node : graph.getVertices()) {
            height = getNodeHeight(graph, node, 0);
            if (height > nodeHeight) {
                nodeHeight = height;
                longestNode = node;
            }
        }

        List<V> path = invertDfsNodes(graph, longestNode);

        Collections.reverse(path);

        return path;

    }

    public void printGraphInfo(Graph<V, E> graph) {
        int nodes = 0;
        int roots = 0;
        int leafs = 0;

        for (V node : graph.getVertices()) {
            if (graph.outDegree(node) == 0) {
                leafs++;
            } else {
                if (graph.inDegree(node) == 0) {
                    roots++;
                } else {
                    nodes++;
                }
            }
        }

        LOGGER.info("***********************************");
        LOGGER.info("   Gene Ontology graph information:");
        LOGGER.info("       Edge count: " + graph.getEdgeCount());
        LOGGER.info("       Vertex count: " + graph.getVertexCount());
        LOGGER.info("           Roots: " + roots);
        LOGGER.info("           Nodes: " + nodes);
        LOGGER.info("           Leafs: " + leafs);
        LOGGER.info("***********************************");
    }

}
