package se.lnu.thesis.paint;

import se.lnu.thesis.utils.GraphUtils;

import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.04.2010
 * Time: 0:11:38
 */
public class GOGraphVisualizer extends GraphWithSubgraphVisualizer {

    @Override
    protected void drawGraphVertex(GLAutoDrawable drawable, Object nodeId) {

        if (getSelectedNode() != null && getSelectedNode() == nodeId) { // selected node is blue
            getVertexVisualizer().setColor(Color.CYAN);
        } else {

            if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) {
                getVertexVisualizer().setColor(Color.RED); // leafs are red
            } else {
                getVertexVisualizer().setColor(Color.WHITE); // all the rest is white
            }
        }

        getVertexVisualizer().draw(drawable, nodeId);
    }

    @Override
    protected void drawSubgraphVertex(GLAutoDrawable drawable, Object nodeId) {

        if (getSelectedNode() != null && getSelectedNode() == nodeId) { // selected node is blue
            getVertexVisualizer().setColor(Color.CYAN);
        } else {

            if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) {
                getVertexVisualizer().setColor(Color.YELLOW); // leafs are yellow
            } else {
                getVertexVisualizer().setColor(Color.GREEN); // all the rest is white
            }
        }

        getVertexVisualizer().draw(drawable, nodeId);
    }

}
