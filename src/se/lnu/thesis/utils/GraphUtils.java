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

    private List result;


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


        if (result == null) {
            result = new LinkedList();
        } else {
            result.clear();
        }

    }

    public List<V> invertDfsNodes(Graph<V, E> graph, V node) {

        init();

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

    public boolean isLeaf(Graph graph, Object node) {
        return graph.outDegree(node) == 0;
    }

    public boolean isRoot(Graph graph, Object node) {
        return graph.inDegree(node) == 0;
    }
}
