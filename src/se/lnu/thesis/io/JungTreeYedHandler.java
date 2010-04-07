package se.lnu.thesis.io;

import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Tree;
import org.apache.log4j.Logger;
import se.lnu.thesis.utils.GraphUtils;

import java.util.Stack;

/**
 * User: vady
 * Date: 28.01.2010
 * Time: 0:01:49
 */
public class JungTreeYedHandler extends JungYedHandler {

    private static final Logger LOGGER = Logger.getLogger(JungTreeYedHandler.class);

    protected void endTagGraph() {
        // add it to the list
        graphs.add(graph2tree());
        graph = null;
    }

    protected Tree graph2tree() {
        DelegateTree tree = new DelegateTree();

        Object root = GraphUtils.getInstance().findRoot((Graph) graph);

        tree.addVertex(root);


        Stack stack = new Stack();
        stack.push(root);

        while (!stack.isEmpty()) {
            Object parent = stack.pop();

            for (Object child : ((Graph) graph).getSuccessors(parent)) {
                if (!tree.containsVertex(child)) {
                    tree.addChild(parent + "->" + child, parent, child);

                    stack.push(child);
                }
            }

        }


        return tree;
    }

}