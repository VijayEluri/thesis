

import edu.uci.ics.jung.graph.util.EdgeType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;


public class TestMyGraph {

    @Test
    public void creatGraph() {
        MyGraph graph = new MyGraph();

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);

        graph.addEdge("1->2", 1, 2, EdgeType.DIRECTED);
        graph.addEdge("1->3", 1, 3, EdgeType.DIRECTED);
        graph.addEdge("3->4", 3, 4, EdgeType.DIRECTED);
        graph.addEdge("3->5", 3, 5, EdgeType.DIRECTED);

        assertEquals(5, graph.getVertexCount());
        assertEquals(4, graph.getEdgeCount());
    }

}
