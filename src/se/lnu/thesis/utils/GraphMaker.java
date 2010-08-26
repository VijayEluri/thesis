package se.lnu.thesis.utils;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.08.2010
 * Time: 15:53:01
 */
public class GraphMaker {


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

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);
        graph.addVertex(10);

        graph.addEdge("1->2", 1, 2, EdgeType.DIRECTED);
        graph.addEdge("1->3", 1, 3, EdgeType.DIRECTED);

        graph.addEdge("3->5", 3, 5, EdgeType.DIRECTED);
        graph.addEdge("3->4", 3, 4, EdgeType.DIRECTED);

        graph.addEdge("5->6", 5, 6, EdgeType.DIRECTED);
        graph.addEdge("6->7", 6, 7, EdgeType.DIRECTED);
        graph.addEdge("7->8", 7, 8, EdgeType.DIRECTED);

        graph.addEdge("8->10", 8, 10, EdgeType.DIRECTED);
        graph.addEdge("8->9", 8, 9, EdgeType.DIRECTED);

        graph.addEdge("10->11", 10, 11, EdgeType.DIRECTED);

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
    public static Graph<Integer, String> createBigBinaryTree() {
        Graph<Integer, String> graph = new DirectedSparseGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);
        graph.addVertex(11);
        graph.addVertex(12);
        graph.addVertex(13);
        graph.addVertex(14);
        graph.addVertex(15);
        graph.addVertex(16);
        graph.addVertex(17);
        graph.addVertex(18);
        graph.addVertex(19);

        graph.addEdge("1->3", 1, 3, EdgeType.DIRECTED);
        graph.addEdge("1->2", 1, 2, EdgeType.DIRECTED);


        graph.addEdge("3->7", 3, 7, EdgeType.DIRECTED);
        graph.addEdge("3->6", 3, 6, EdgeType.DIRECTED);
        graph.addEdge("6->15", 6, 15, EdgeType.DIRECTED);
        graph.addEdge("6->16", 6, 16, EdgeType.DIRECTED);
        graph.addEdge("7->9", 7, 9, EdgeType.DIRECTED);
        graph.addEdge("7->19", 7, 19, EdgeType.DIRECTED);
        graph.addEdge("9->17", 9, 17, EdgeType.DIRECTED);
        graph.addEdge("9->18", 9, 18, EdgeType.DIRECTED);


        graph.addEdge("2->4", 2, 4, EdgeType.DIRECTED);
        graph.addEdge("2->5", 2, 5, EdgeType.DIRECTED);

        graph.addEdge("3->6", 3, 6, EdgeType.DIRECTED);
        graph.addEdge("3->7", 3, 7, EdgeType.DIRECTED);

        graph.addEdge("4->10", 4, 10, EdgeType.DIRECTED);
        graph.addEdge("4->11", 4, 11, EdgeType.DIRECTED);

        graph.addEdge("5->8", 5, 8, EdgeType.DIRECTED);
        graph.addEdge("5->14", 5, 14, EdgeType.DIRECTED);

        graph.addEdge("8->12", 8, 12, EdgeType.DIRECTED);
        graph.addEdge("8->13", 8, 13, EdgeType.DIRECTED);


        return graph;
    }
}
