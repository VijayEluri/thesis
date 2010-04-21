package se.lnu.thesis.utils;

import edu.uci.ics.jung.graph.Graph;

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

    private static GraphUtils instance = new GraphUtils();

    public static GraphUtils getInstance() {
        if (instance == null) {
            instance = new GraphUtils();
        }

        return instance;
    }

    private Stack stack;
    private Set visited;

    private GraphUtils() {
    }

    protected void init() {
        if (stack == null) {
            stack = new Stack();
        } else {
            stack.clear();
        }

        if (visited == null) {
            visited = new HashSet();
        } else {
            visited.clear();
        }

    }

    public List<V> invertDfsNodes(Graph<V, E> graph, V node) {

        init();
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

        init();
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

    public boolean isLeaf(Graph graph, Object node) {
        return graph.outDegree(node) == 0;
    }

    public boolean isRoot(Graph graph, Object node) {
        return graph.inDegree(node) == 0;
    }

    public Set getNodeLeafs(Graph graph, Object root) {
        init();

        Set result = new HashSet();

        stack.push(root);

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

    public Set getSubgraphAndLeafs(Graph graph, Graph subGraph, Object root) {
        init();

        Set result = new HashSet();

        stack.push(root);

        while (!stack.isEmpty()) {
            V parent = (V) stack.pop();

            if (visited.add(parent)) {

                subGraph.addVertex(parent);

                if (isLeaf(graph, parent)) {
                    result.add(parent);
                } else {
                    for (Object child : graph.getSuccessors(parent)) {
                        if (!visited.contains(child)) {
                            stack.push(child);
                            subGraph.addVertex(child);
                            subGraph.addEdge(parent + "->" + child, parent, child);
                        }
                    }
                }

            }
        }

        return result;
    }


}
