package se.lnu.thesis.test;

import com.google.common.base.Joiner;
import edu.uci.ics.jung.graph.Graph;
import org.junit.Test;
import se.lnu.thesis.utils.GraphMaker;
import se.lnu.thesis.utils.GraphUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.08.2010
 * Time: 16:26:00
 * To change this template use File | Settings | File Templates.
 */
public class TestGraphTraversal {

    @Test
    public void testDfs() {
        Graph<Integer, String> binaryTree = GraphMaker.createBigBinaryTree();

        List result = new LinkedList();
        GraphUtils.getLeafs(binaryTree, 1, result);

        System.out.println(Joiner.on(" ").join(result));
    }

}
