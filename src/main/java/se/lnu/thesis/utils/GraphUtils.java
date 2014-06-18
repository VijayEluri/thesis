package se.lnu.thesis.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import edu.uci.ics.jung.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.core.MyGraph;

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
public class GraphUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphUtils.class);


    public static <V, E> V getRoot(Graph<V, E> graph) {
        for (V vertex : graph.getVertices()) {
            if (isRoot(graph, vertex)) {
                return vertex;
            }
        }

        return null;
    }

    public static <V, E> Set<V> getRoots(Graph<V, E> graph) {
        Set<V> result = new HashSet<V>();

        for (V vertex : graph.getVertices()) {
            if (isRoot(graph, vertex)) {
                result.add(vertex);
            }
        }

        return result;
    }

    /**
     * This method computes level for every vertex in @graph and store into map vertex -> level
     *
     * @param graph  Specified graph to process
     * @param levels Result map (vertex object -> level value)
     * @return Graph height (maximum level value + 1).
     */
    public static <V, E> int computeLevels(Graph<V, E> graph, Map<V, Integer> levels) {

        Integer maxHeight = 0;
        Integer level;

        for (V node : graph.getVertices()) {
            List<V> list = GraphTraversalUtils.invertDfs(graph, node);

            level = list.size();

            if (levels.get(node) != null) {
                if (maxHeight > levels.get(node)) {
                    levels.put(node, level);
                }
            } else {
                levels.put(node, level);
            }

            if (level > maxHeight) {
                maxHeight = level;
            }
        }

        return maxHeight + 1;
    }

    /**
     * It is improved version of the method @GraphUtils.computeLevels
     * This method computes level for every vertex in @graph and store into map vertex -> level
     *
     * @param graph  Specified graph to process
     * @param levels Result map (vertex object -> level value)
     * @return Graph height (maximum level value + 1).
     */
    public static <V, E> int computeLevelsV2(Graph<V, E> graph, Map<V, Integer> levels) {

        int maxHeight = 0;
        Integer nodeLevel;

        for (V node : graph.getVertices()) {
            nodeLevel = GraphUtils.getNodeHeight(graph, node, 0);

            if (levels.get(node) != null) {
                if (maxHeight > levels.get(node)) {
                    levels.put(node, nodeLevel);
                }
            } else {
                levels.put(node, nodeLevel);
            }

            if (nodeLevel > maxHeight) {
                maxHeight = nodeLevel;
            }
        }

        return maxHeight + 1;
    }

    /**
     * Method computes all levels for specific graph and extracts them to map level number -> vertices
     *
     * @param graph  Graph to extract levels for.
     * @param levels Result map.
     * @return Graph height (maximum level value + 1).
     */
    public static <V, E> int computeLevels(Graph<V, E> graph, Multimap<Integer, V> levels) {

        int maxHeight = 0;
        Integer level;

        for (V node : graph.getVertices()) {
            //level = GraphUtils.getNodeHeight(graph, node);
            level = GraphUtils.getMaxNodeHeight(graph, node);
            levels.put(level, node);

            if (level > maxHeight) {
                maxHeight = level;
            }
        }

        return maxHeight + 1;
    }

    public static <V, E> void moveAllLeafBottomLevel(Graph<V, E> graph, Multimap<Integer, V> levels) {

        Set<V> leafs = new HashSet<V>();

        Integer leafsLevelIndex = levels.keySet().size() - 1;

        for (V v : levels.values()) {
            if (GraphUtils.isLeaf(graph, v)) {
                leafs.add(v);
            }
        }

        levels.values().removeAll(leafs);
        levels.putAll(leafsLevelIndex, leafs);

    }


    public static <V, E> int getNodeHeight(Graph<V, E> graph, V node) {
        return getNodeHeight(graph, node, 0);
    }

    public static <V, E> int getNodeHeight(Graph<V, E> graph, V node, int start) {
        if (graph.getPredecessorCount(node) > 0) {
            start = getNodeHeight(graph, graph.getPredecessors(node).iterator().next(), ++start);
        }

        return start;
    }

    public static <V, E> int getMaxNodeHeight(Graph<V, E> graph, V node) {
        return getMaxNodeHeight(graph, node, 0);
    }

    public static <V, E> int getMaxNodeHeight(Graph<V, E> graph, V node, int start) {
        if (graph.getPredecessorCount(node) == 0) {
            return start;
        } else {
            int max = start;

            for (V parent : graph.getPredecessors(node)) {
                int nodeHeight = getMaxNodeHeight(graph, parent, start + 1);
                max = Math.max(nodeHeight, max);
            }

            return max;
        }
    }

    public static <V, E> int getDistance(Graph<V, E> graph, V node1, V node2) {
        return getDistance(graph, node1, node2, 0);
    }

    public static <V, E> int getDistance(Graph<V, E> graph, V node1, V node2, int start) {
        if (graph.getPredecessorCount(node1) > 0 && !node2.equals(node1)) {
            start = getDistance(graph, graph.getPredecessors(node1).iterator().next(), node2, ++start);
        }

        return start;
    }

    public static <V, E> boolean isLeaf(Graph<V, E> graph, V node) {
        return graph.outDegree(node) == 0;
    }

    public static <V, E> boolean isRoot(Graph<V, E> graph, V node) {
        return graph.inDegree(node) == 0;
    }

    /**
     * Check if vertex does not have neither predecessors nor successors
     *
     * @param graph  Graph where vertex is
     * @param vertex Corresponded vertex object id to check
     * @return True if unconnected, False otherwise
     */
    public static <V, E> boolean isUnconnectedComponent(Graph<V, E> graph, V vertex) {
        return isRoot(graph, vertex) && isLeaf(graph, vertex);
    }

    /**
     * Check if current vertex is node: has outgoing edges.
     *
     * @param graph Graph to check vertex in
     * @param o     Vertex id object
     * @return True if it's node, False otherwise
     */
    public static boolean isNode(Graph graph, Object o) {
        return graph.outDegree(o) > 0;
    }

    public static <V, E> Set<V> getLeafs(Graph<V, E> graph, V node) {
        Set result = new HashSet();

        getLeafs(graph, node, result);

        return result;
    }

    public static <V, E> int getLeafs(Graph<V, E> graph, V node, Collection<V> leafs) {
        Preconditions.checkNotNull(leafs);

        Stack stack = new Stack();
        Set visited = new HashSet();

        stack.push(node);

        while (!stack.isEmpty()) {
            V next = (V) stack.pop();

            if (visited.add(next)) {

                if (isLeaf(graph, next)) {
                    leafs.add(next);
                } else {
                    for (Object neighbor : graph.getSuccessors(next)) {
                        if (!visited.contains(neighbor)) {
                            stack.push(neighbor);
                        }
                    }
                }


            }
        }

        return leafs.size();
    }

    /**
     * Extract subgraph from @graph starting root node @node
     *
     * @param graph    Graph from which to extract subgraph
     * @param subGraph Extracted subgraph
     * @param root     Root node for subgraph
     * @return Leafs from subgraph
     */
    public static <V, E> Set<V> extractSubgraph(Graph<V, E> graph, Graph<V, E> subGraph, V root) {
        Stack stack = new Stack();
        Set visited = new HashSet();
        Set<V> subGraphLeafs = new HashSet<V>();

        stack.push(root);

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
     * Extract subgraph from @graph to @subGraph starting root node @node and copy vertex label.
     *
     * @param graph    Graph from which to extract subgraph
     * @param subGraph Extracted subgraph
     * @param root     Root node for subgraph
     * @return Leafs from subgraph
     */
    public static <V, E> Set<V> extractSubgraph(MyGraph<V, E> graph, MyGraph<V, E> subGraph, V root) {
        Stack stack = new Stack();
        Set visited = new HashSet();
        Set<V> subGraphLeafs = new HashSet<V>();

        stack.push(root);

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

        for (V o : subGraph.getVertices()) {
            String label = graph.getLabel(o);
            if (label != null) {
                subGraph.addLabel(o, label);
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
    public static <V, E> List<V> getLongestPath(Graph<V, E> graph) {
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

        List<V> path = GraphTraversalUtils.invertDfs(graph, longestNode);

        Collections.reverse(path);

        return path;

    }

}
