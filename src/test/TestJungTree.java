package test;

import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.graph.util.EdgeType;
import io.GraphMLParser;
import io.JungTreeYedHandler;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 12.03.2010
 * Time: 2:29:52
 * To change this template use File | Settings | File Templates.
 */
public class TestJungTree {

    @Test
    public void loadTree() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler()); // TODO implement DelegateTree in the JungTreeYedHandler
        Tree tree = (Tree) parser.load(new File("cluster.graphml")).get(0);

        assertEquals(37, tree.getVertexCount());

        assertEquals(16, tree.getHeight());

    }

    @Test
    public void makeTree() {
        DelegateTree tree = new DelegateTree();

        tree.addVertex(1); // root

        tree.addChild("1->2", 1, 2, EdgeType.DIRECTED);
        tree.addChild("1->3", 1, 3, EdgeType.DIRECTED);
        tree.addChild("3->4", 3, 4, EdgeType.DIRECTED);
        tree.addChild("3->5", 3, 5, EdgeType.DIRECTED);

        assertEquals(5, tree.getVertexCount());

        assertEquals(2, tree.getHeight());
    }

}
