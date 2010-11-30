

import edu.uci.ics.jung.graph.Graph;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import se.lnu.thesis.utils.GraphMaker;
import se.lnu.thesis.utils.PrintGraphUtil;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 10.08.2010
 * Time: 16:00:32
 * To change this template use File | Settings | File Templates.
 */
public class TestPrint {

    @Test
    public void print() {

        Graph graph = GraphMaker.createBigBinaryTree();

        assertTrue(graph.containsVertex(8));

        PrintGraphUtil.printBinaryTree(graph);
    }

}
