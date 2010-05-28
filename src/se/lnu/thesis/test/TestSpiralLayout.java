package se.lnu.thesis.test;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.junit.Assert;
import org.junit.Test;
import se.lnu.thesis.layout.SpiralLayout;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.05.2010
 * Time: 15:42:17
 */
public class TestSpiralLayout {

    /**
     * _________1
     * _______/  \
     * ______3    2
     * ____/  \
     * __4_____5
     * /__\____|
     * 14 _13___6
     * _______|
     * _______7
     * _______|
     * _______8
     * ______/ \
     * _____9__10
     * _________|
     * _________11
     */
    private Graph createGraph() {
        Graph graph = new DirectedSparseGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(14);
        graph.addVertex(13);
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
        graph.addEdge("4->14", 4, 14, EdgeType.DIRECTED);
        graph.addEdge("4->13", 4, 13, EdgeType.DIRECTED);
        graph.addEdge("3->5", 3, 5, EdgeType.DIRECTED);
        graph.addEdge("5->6", 5, 6, EdgeType.DIRECTED);
        graph.addEdge("6->7", 6, 7, EdgeType.DIRECTED);
        graph.addEdge("7->8", 7, 8, EdgeType.DIRECTED);
        graph.addEdge("8->9", 8, 9, EdgeType.DIRECTED);
        graph.addEdge("8->10", 8, 10, EdgeType.DIRECTED);
        graph.addEdge("10->11", 10, 11, EdgeType.DIRECTED);

        return graph;
    }

    @Test
    public void layoutComputation() {
        SpiralLayout spiralLayout = new SpiralLayout(createGraph());
        spiralLayout.initialize();

        Set vertices = spiralLayout.getVertices();
        Assert.assertEquals(11, vertices.size());

        Set groupVertices = spiralLayout.getGroupVertices();
        Assert.assertEquals(1, groupVertices.size());

        Assert.assertTrue(groupVertices.contains(4));
    }

}
