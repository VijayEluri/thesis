package se.lnu.thesis.utils;

import edu.uci.ics.jung.graph.Graph;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 06.08.2010
 * Time: 14:17:32
 */
public class GraphTraversalUtils {

    public static <V, E> List<V> invertDfs(Graph<V, E> graph, V node) {

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

    public static <V, E> List<V> dfs(Graph<V, E> graph, V node) {

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
}
