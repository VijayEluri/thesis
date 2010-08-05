package se.lnu.thesis.test;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 31.07.2010
 * Time: 1:42:48
 */
public abstract class TestGraph {
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
    protected Graph createGraph() {
        Graph graph = new DirectedSparseGraph();

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
        graph.addEdge("3->4", 3, 4, EdgeType.DIRECTED);
        graph.addEdge("3->5", 3, 5, EdgeType.DIRECTED);
        graph.addEdge("5->6", 5, 6, EdgeType.DIRECTED);
        graph.addEdge("6->7", 6, 7, EdgeType.DIRECTED);
        graph.addEdge("7->8", 7, 8, EdgeType.DIRECTED);
        graph.addEdge("8->9", 8, 9, EdgeType.DIRECTED);
        graph.addEdge("8->10", 8, 10, EdgeType.DIRECTED);
        graph.addEdge("10->11", 10, 11, EdgeType.DIRECTED);

        return graph;
    }
}
