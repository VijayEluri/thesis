package se.lnu.thesis.paint.visualizer.graph;

import se.lnu.thesis.utils.GraphUtils;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.04.2010
 * Time: 19:43:28
 * <p/>
 * Visualizer for cluster graph.
 */
public class ClusterGraphVisualizer extends GraphWithSubgraphVisualizer {

    @Override
    protected void drawGraphVertex(GLAutoDrawable drawable, Object nodeId) {
        if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) { // draw only leafs
            //        getVertexVisualizer().draw(drawable, nodeId);
        }
    }

    @Override
    protected void drawSubgraphVertex(GLAutoDrawable drawable, Object nodeId) {
        if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) { // draw only leafs
            //      getSubGraphVertexVizualizer().draw(drawable, nodeId);
        }
    }

}
