package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.utils.GraphUtils;

import java.util.HashMap;
import java.util.Map;

public class PolarDendrogramLayout<V, E> extends AbstractPolarDendrogramLayout<V, E> {

    public static final Logger LOGGER = Logger.getLogger(PolarDendrogramLayout.class);

    protected Map<V, Integer> nodeLevel;

    protected int graphHeight;

    protected V root;


    public PolarDendrogramLayout(Graph graph) {
        super(graph);
    }

    protected void init() {
        super.init();

        if (nodeLevel == null) {
            nodeLevel = new HashMap<V, Integer>();
        } else {
            nodeLevel.clear();
        }

        root = (V) GraphUtils.getInstance().findRoot(getGraph()); // TODO perfomance!
        if (root == null) {
            throw new IllegalArgumentException("No root vertex! Cannt draw unrooted graph..");
        }

        graphHeight = GraphUtils.getInstance().computeLevelsV2(getGraph(), nodeLevel); // TODO perfomnce!!
    }

    protected V getRoot() {
        return root;
    }


    public int getNodeLevel(V node) {
        return nodeLevel.get(node);
    }

    public int getGraphHeight() {
        return graphHeight;
    }

    public Map<V, Integer> getNodeLevel() {
        return nodeLevel;
    }

}

