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
public class ClusterGraphVisualizer extends GOGraphVisualizer {

    public ClusterGraphVisualizer(PApplet applet) {
        super(applet);
    }

    @Override
    protected void drawGraphVertex(Object nodeId) {
        if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) {
            getApplet().fill(255, 0, 0); // draw red leafs
            getApplet().stroke(255, 0, 0);
            getVertexVisualizer().draw(nodeId);
        }
    }

    @Override
    protected void drawSubgraphVertex(Object nodeId) {
        if (GraphUtils.getInstance().isLeaf(getSubGraph(), nodeId)) {
            getApplet().fill(255, 255, 0);     // draw yellow
            getApplet().stroke(255, 255, 0);
            getSubGraphVertexVizualizer().draw(nodeId);
        }
    }
}
