package se.lnu.thesis.paint;

import processing.core.PApplet;
import se.lnu.thesis.utils.GraphUtils;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.04.2010
 * Time: 19:43:28
 * <p/>
 * Visualizer for cluster graph.
 */
public class ClusterGraphVisualizer extends GraphWithSubgraphVisualizer {

    public ClusterGraphVisualizer(PApplet applet) {
        super(applet);
    }

    @Override
    protected void drawGraphVertex(Object nodeId) {
        if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) { // draw only leafs
            getVertexVisualizer().draw(nodeId);
        }
    }

    @Override
    protected void drawSubgraphVertex(Object nodeId) {
        if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) { // draw only leafs
            getSubGraphVertexVizualizer().draw(nodeId);
        }
    }
}
