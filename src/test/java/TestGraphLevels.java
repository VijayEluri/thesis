

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

        // RESULT SHOULD BE: {0=[1], 1=[2, 3], 2=[4, 5], 3=[6], 4=[7], 5=[8], 6=[9, 10], 7=[11]}

        assertEquals(height, levels.keySet().size());
        assertEquals(8, height);
        assertEquals(8, levels.keySet().size());

        // level 0 -> node 1
        assertEquals(1, levels.get(0).size());
        assertTrue(levels.get(0).contains(1));

        // level 1 -> nodes 3 and 2
        assertEquals(2, levels.get(1).size());
        assertTrue(levels.get(1).contains(2));
        assertTrue(levels.get(1).contains(3));

        // level 2 -> nodes 4 and 5
        assertEquals(2, levels.get(2).size());
        assertTrue(levels.get(2).contains(4));
        assertTrue(levels.get(2).contains(5));

        // level 3 -> nodes 6
        assertEquals(1, levels.get(3).size());
        assertTrue(levels.get(3).contains(6));

        // level 4 -> nodes 7
        assertEquals(1, levels.get(4).size());
        assertTrue(levels.get(4).contains(7));

        // level 5 -> nodes 8
        assertEquals(1, levels.get(5).size());
        assertTrue(levels.get(5).contains(8));

        // level 6 -> nodes 9 and 10
        assertEquals(2, levels.get(6).size());
        assertTrue(levels.get(6).contains(9));
        assertTrue(levels.get(6).contains(10));

        // level 7 -> nodes 9 and 11
        assertTrue(levels.get(7).contains(11));

    }

    @Test
    public void checkLeafsInTheBottom() {
        Graph graph = GraphMaker.createTestBinaryTree();

        assertTrue(graph.containsVertex(8));

        Multimap levels = TreeMultimap.create();
        int height = GraphUtils.computeLevels(graph, levels);

        // RESULT SHOULD BE: {0=[1], 1=[2, 3], 2=[4, 5], 3=[6], 4=[7], 5=[8], 6=[9, 10], 7=[11]}

        assertEquals(height, levels.keySet().size());
        assertEquals(8, height);
        assertEquals(8, levels.keySet().size());

        // level 1 -> nodes 3 and 2
        assertTrue(levels.get(1).contains(3));
        assertTrue(levels.get(1).contains(2));

        GraphUtils.moveAllLeafBottomLevel(graph, levels);

        // RESULT SHOULD BE: {0=[1], 1=[3], 2=[5], 3=[6], 4=[7], 5=[8], 6=[10], 7=[2, 4, 9, 11]}

        assertEquals(8, levels.keySet().size());

        // level 1 -> nodes 3 and 2
        assertEquals(4, levels.get(7).size());
        assertTrue(levels.get(7).contains(2));
        assertTrue(levels.get(7).contains(4));
        assertTrue(levels.get(7).contains(9));
        assertTrue(levels.get(7).contains(11));




    }

}
