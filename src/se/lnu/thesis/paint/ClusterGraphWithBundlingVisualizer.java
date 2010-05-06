package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.04.2010
 * Time: 19:43:28
 * <p/>
 * Visualizer for cluster graph.
 */
public class ClusterGraphWithBundlingVisualizer extends ClusterGraphVisualizer {

    public ClusterGraphWithBundlingVisualizer(PApplet applet) {
        super(applet);
    }

    @Override
    public void setSubGraph(Graph subGraph) {
        super.setSubGraph(subGraph);
    }

    @Override
    protected void drawGraphEdge(Object edgeId) {
        getApplet().noFill();
        getApplet().stroke(255); // set edge color to white

        if (getSubGraph() != null) {
            getEdgeVisualizer().draw(edgeId);
        } else {
            getSubGraphEdgeVizualizer().draw(edgeId);
        }
    }
}