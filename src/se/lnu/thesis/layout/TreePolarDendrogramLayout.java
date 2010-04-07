package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Tree;
import org.apache.log4j.Logger;

/**
 * Class draws polar dendrogram like PolarDendrogramLaout but takes as graph argument instance of the edu.uci.ics.jung.graph.Tree
 */
public class TreePolarDendrogramLayout<V, E> extends AbstractPolarDendrogramLayout<V, E> {

    public static final Logger LOGGER = Logger.getLogger(TreePolarDendrogramLayout.class);

    public TreePolarDendrogramLayout(Tree graph) {
        super(graph);
    }

    protected V getRoot() {
        return (V) getTree().getRoot();
    }

    public int getNodeLevel(V node) {
        return getTree().getDepth(node);
    }

    public int getGraphHeight() {
        return getTree().getHeight();
    }

    public Tree getTree() {
        return (Tree) getGraph();
    }

}