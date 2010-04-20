package se.lnu.thesis.paint;

import processing.core.PApplet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.04.2010
 * Time: 0:11:38
 */
public class GOGraphVisualizer extends GraphWithSubgraphVisualizer {

    public GOGraphVisualizer(PApplet applet) {
        super(applet);
    }

    @Override
    protected void drawGraphVertex(Object nodeId) {
        getApplet().fill(255);
        getApplet().stroke(255); // set edge color to white
        getVertexVisualizer().draw(nodeId);
    }

    @Override
    protected void drawSubgraphVertex(Object nodeId) {
        getApplet().fill(255, 255, 0);  // color yellow
        getApplet().stroke(255, 255, 0);
        getVertexVisualizer().draw(nodeId);
    }

    @Override
    protected void drawGraphEdge(Object edgeId) {
        getApplet().noFill();
        getApplet().stroke(255); // set edge color to white
        getEdgeVisualizer().draw(edgeId);
    }

    @Override
    protected void drawSubgraphEdge(Object edgeId) {
        getApplet().noFill();
        getApplet().stroke(255, 255, 0); // color yellow
        getSubGraphEdgeVizualizer().draw(edgeId);
    }

}
