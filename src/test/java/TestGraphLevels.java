

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import edu.uci.ics.jung.graph.Graph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import se.lnu.thesis.utils.GraphMaker;
import se.lnu.thesis.utils.GraphUtils;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 10.08.2010
 * Time: 14:14:24
 */
public class TestGraphLevels {

    @Test
    public void computeLevels() {
        Graph graph = GraphMaker.createTestBinaryTree();

        assertTrue(graph.containsVertex(8));

        Multimap levels = TreeMultimap.create();
        int height = GraphUtils.computeLevels(graph, levels);

        assertEquals(8, height);
        assertEquals(8, levels.keySet().size());

        // level 0 -> node 1
        assertTrue(levels.get(0).contains(1));

        // level 1 -> nodes 3 and 2
        assertTrue(levels.get(1).contains(3));
        assertTrue(levels.get(1).contains(2));

        // level 6 -> nodes 9 and 10
        assertTrue(levels.get(6).contains(9));
        assertTrue(levels.get(6).contains(10));

        // level 7 -> nodes 9 and 11
        assertTrue(levels.get(7).contains(11));

    }

}
