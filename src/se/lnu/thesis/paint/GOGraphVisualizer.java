package se.lnu.thesis.paint;

import processing.core.PApplet;
import se.lnu.thesis.Thesis;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.*;

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

        if (Thesis.getInstance().getSelectionDialog().getSelectedNode() == nodeId) { // selected node is blue
            getVertexVisualizer().setColor(Color.CYAN);
        } else {

            if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) {
                getVertexVisualizer().setColor(Color.RED); // leafs are red
            } else {
                getVertexVisualizer().setColor(Color.WHITE); // all the rest is white
            }
        }

        getVertexVisualizer().draw(nodeId);
    }

    @Override
    protected void drawSubgraphVertex(Object nodeId) {

        if (Thesis.getInstance().getSelectionDialog().getSelectedNode() == nodeId) { // selected node is blue
            getVertexVisualizer().setColor(Color.CYAN);
        } else {

            if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) {
                getVertexVisualizer().setColor(Color.YELLOW); // leafs are yellow
            } else {
                getVertexVisualizer().setColor(Color.GREEN); // all the rest is white
            }
        }

        getVertexVisualizer().draw(nodeId);
    }

/*
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
*/

}
