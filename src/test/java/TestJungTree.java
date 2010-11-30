

import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 12.03.2010
 * Time: 2:29:52
 */
public class TestJungTree {


    /**
     * Create simple tree
     * <p/>
     * ______1
     * ____/   \
     * ___2     3
     * _/  \
     * 4    5
     *
     * @return Instance of the Delegate tree
     */
    private DelegateTree createSimpleTree() {
        DelegateTree tree = new DelegateTree();

        tree.addVertex(1); // root

        tree.addChild("1->2", 1, 2, EdgeType.DIRECTED);
        tree.addChild("1->3", 1, 3, EdgeType.DIRECTED);
        tree.addChild("3->4", 3, 4, EdgeType.DIRECTED);
        tree.addChild("3->5", 3, 5, EdgeType.DIRECTED);
        return tree;
    }

    @Test
    public void testSimpleTree() {
        DelegateTree tree = createSimpleTree();

        assertEquals(5, tree.getVertexCount());

        assertEquals(2, tree.getHeight());


        assertEquals(2, tree.getDepth(5));
        assertEquals(0, tree.getDepth(1));

    }

    @Test
    public void testTreePathOn5() {
        DelegateTree tree = createSimpleTree();

        assertEquals(5, tree.getVertexCount());
        assertEquals(2, tree.getHeight());

        List path = tree.getPath(5);
        assertNotNull(path);
        assertEquals(3, path.size());

        assertEquals(new Object[]{1, 3, 5}, path.toArray());
    }

    @Test
    public void testTreePathOn2() {
        DelegateTree tree = createSimpleTree();

        assertEquals(5, tree.getVertexCount());
        assertEquals(2, tree.getHeight());

        List path = tree.getPath(2);
        assertNotNull(path);
        assertEquals(2, path.size());

        assertEquals(new Object[]{1, 2}, path.toArray());
    }

    @Test
    public void fromGraphToTree() {
        Graph graph = new DirectedSparseGraph();

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


        DelegateTree tree = new DelegateTree();

    }

}
