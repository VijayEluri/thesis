package se.lnu.thesis.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
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

    public static final Logger LOGGER = Logger.getLogger(GraphUtils.class);


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

    public static <V, E> int computeLevels(Graph<V, E> graph, Multimap<Integer, V> levels) {

        int maxHeight = 0;
        Integer level;

        for (V node : graph.getVertices()) {
            level = GraphUtils.getNodeHeight(graph, node);
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

        for (V v: levels.values()) {
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

    public static <V, E> Set<V> getLeafs(Graph<V, E> graph, V node) {
        Set result = new HashSet();

        getLeafs(graph, node, result);

        return result;
    }

    public static <V, E> int getLeafs(Graph<V, E> graph, V node, Collection<V> leafs) {
        if (leafs == null) {
            throw new IllegalArgumentException("Argument 'leafs' cannt be null! Initialize it before method lensMovingCursorStartPosition.");
        }

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
     * @param node     Root node for subgraph
     * @return Leafs from subgraph
     */
    public static <V, E> Set<V> extractSubgraph(Graph<V, E> graph, Graph<V, E> subGraph, V node) {
        Stack stack = new Stack();
        Set visited = new HashSet();
        Set<V> subGraphLeafs = new HashSet<V>();

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

    /**
     *
     * Check if vertex does not have neither predecessors nor successors
     *
     * @param graph Graph where vertex is
     * @param vertex Corresponded vertex object id to check
     * @return True if unconnected, False otherwise
     */
    public static <V,E> boolean isUnconnectedComponent(Graph<V,E> graph, V vertex) {
        return isRoot(graph, vertex) && isLeaf(graph, vertex);
    }
}
