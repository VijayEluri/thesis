package se.lnu.thesis.utils;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.08.2010
 * Time: 15:53:01
 */
public class GraphMaker {

    /**
     *
     *      Create and add to graph directed edge from vertex1 to vertex2.
     *      Equats to:
     *      <code>
     *          graph.addEdge(vertex1 + "->" + vertex2, vertex1, vertex2, EdgeType.DIRECTED);
     *      </code>
     *
     *      If corresponded vertices do not exist in the graph they will be created.
     *
     * @param graph Graph where to add vertex.
     * @param vertex1 Source vertex object.
     * @param vertex2 Target vertex object.
     */
    protected static void createDirectedEdge(Graph<Integer, String> graph, Integer vertex1, Integer vertex2) {
        if (!graph.containsVertex(vertex1)) {
            graph.addVertex(vertex1);
        }

        if (!graph.containsVertex(vertex2)) {
            graph.addVertex(vertex2);
        }

        java.lang.String edge = vertex1 + "->" + vertex2;
        graph.addEdge(edge, vertex1, vertex2, EdgeType.DIRECTED);
    }

    /**
     * ______1
     * ____/   \
     * ___3     2
     * _/  \
     * 4    5
     * _____|
     * _____6
     * _____|
     * _____7
     * _____|
     * _____8
     * ____/ \
     * ___9__10
     * ______|
     * ______11
     */
    public static Graph<Integer, String> createTestBinaryTree() {
        Graph<Integer, String> graph = new DirectedSparseGraph();

        createDirectedEdge(graph, 1, 2);
        createDirectedEdge(graph, 1, 3);

        createDirectedEdge(graph, 3, 5);
        createDirectedEdge(graph, 3, 4);

        createDirectedEdge(graph, 5, 6);
        createDirectedEdge(graph, 6, 7);
        createDirectedEdge(graph, 7, 8);

        createDirectedEdge(graph, 8, 10);
        createDirectedEdge(graph, 8, 9);

        createDirectedEdge(graph, 10, 11);

        return graph;
    }

    /**
     * ______________________1
     * ______________/             \
     * ____________2                3
     * ________/     \          /      \
     * _______4       5        6        7
     * _____/  \     / \     /  \     /  \
     * ___10   11   8  14   15  16   9   19
     * __/ \_______________/ \
     * _12 13_____________17 18
     */
    public static Graph<Integer, String> createWideBinaryTree() {
        Graph<Integer, String> graph = new DirectedSparseGraph();

        createDirectedEdge(graph, 1, 3);
        createDirectedEdge(graph, 1, 2);


        createDirectedEdge(graph, 3, 7);
        createDirectedEdge(graph, 3, 6);
        createDirectedEdge(graph, 6, 15);
        createDirectedEdge(graph, 6, 16);
        createDirectedEdge(graph, 7, 9);
        createDirectedEdge(graph, 7, 19);
        createDirectedEdge(graph, 9, 17);
        createDirectedEdge(graph, 9, 18);


        createDirectedEdge(graph, 2, 4);
        createDirectedEdge(graph, 2, 5);

        createDirectedEdge(graph, 3, 6);
        createDirectedEdge(graph, 3, 7);

        createDirectedEdge(graph, 4, 10);
        createDirectedEdge(graph, 4, 11);

        createDirectedEdge(graph, 5, 8);
        createDirectedEdge(graph, 5, 14);

        createDirectedEdge(graph, 8, 12);
        createDirectedEdge(graph, 8, 13);


        return graph;
    }

    /**
     * ___1
     * ___|
     * ___2
     * ___|
     * ___3
     * ___|
     * ___4
     * ___|
     * ___5
     * ___|
     * ___6
     * __/_\
     * _7___8
     * _____|
     * _____9
     * _____|
     * ____10
     *
     * @return Instance of DirectedSparseGraph<Integer,String>
     */
    public static Graph<Integer, String>createHighBinaryTree() {
        Graph graph = new DirectedSparseGraph();

        createDirectedEdge(graph, 1, 2);
        createDirectedEdge(graph, 2, 3);
        createDirectedEdge(graph, 3, 4);
        createDirectedEdge(graph, 4, 5);
        createDirectedEdge(graph, 5, 6);
        createDirectedEdge(graph, 6, 7);
        createDirectedEdge(graph, 6, 8);
        createDirectedEdge(graph, 8, 9);
        createDirectedEdge(graph, 9, 10);

        return graph;
    }

    /**
     * ___1___6__7__8__9
     * __/_\
     * _2___3
     * _____|
     * _____4
     * _____|
     * _____5
     *
     * @return Instance of DirectedSparseGraph<Integer,String>
     */
    public static Graph createGraphWithUnconnectedComponents() {
        Graph graph = new DirectedSparseGraph();

        createDirectedEdge(graph, 1, 2);
        createDirectedEdge(graph, 1, 3);
        createDirectedEdge(graph, 3, 4);
        createDirectedEdge(graph, 4, 5);

        // unconnected vertices
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);


        return graph;
    }

    /**
     * ___1_
     * __/_\
     * _2___3
     * ____/_\
     * ___4___5
     *
     * @return Instance of DirectedSparseGraph<Integer,String>
     */
    public static Graph<Integer, String>createSmallBinaryTree() {
        Graph<Integer, String>graph = new DirectedSparseGraph();

        createDirectedEdge(graph, 1, 2);
        createDirectedEdge(graph, 1, 3);
        createDirectedEdge(graph, 3, 4);
        createDirectedEdge(graph, 3, 5);

        return graph;
    }

    /**
     *       1      8  9
     *     /   \
     *    2     3
     *    \   /  |
     *      4    |
     *   /  |  \ |
     *  7   6 -> 5
     *
     * @return Instance of DirectedSparseGraph
     */
    public static Graph<Integer, String> createSmallDAGWithUnconnectedComponents() {
        Graph<Integer, String>graph = new DirectedSparseGraph();

        createDirectedEdge(graph, 1, 2);
        createDirectedEdge(graph, 1, 3);
        createDirectedEdge(graph, 2, 4);
        createDirectedEdge(graph, 3, 4);
        createDirectedEdge(graph, 3, 5);
        createDirectedEdge(graph, 4, 6);
        createDirectedEdge(graph, 4, 5);
        createDirectedEdge(graph, 4, 7);
        createDirectedEdge(graph, 6, 5);

        graph.addVertex(8);
        graph.addVertex(9);

        return graph;
    }


}
