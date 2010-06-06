package se.lnu.thesis.paint;

import se.lnu.thesis.layout.RectangularSpiralLayout;
import se.lnu.thesis.paint.vertex.RectVertexVisualizer;

import javax.media.opengl.GLAutoDrawable;


public class RectangularSpiralClusterVisualizer extends GraphWithSubgraphVisualizer {

    private static final double MAX_GROUP_VERTEX_SIZE = 0.028;

    private RectVertexVisualizer groupVertexVisualizer;
    private RectVertexVisualizer selectedGroupVertexVisualizer;

    @Override
    public void drawVertex(GLAutoDrawable drawable, Object nodeId) {
        RectangularSpiralLayout rectangularSpiralLayout = (RectangularSpiralLayout) getLayout();

        if (rectangularSpiralLayout.getVertices().contains(nodeId)) {
            if (rectangularSpiralLayout.getGroupVertices().contains(nodeId)) {

                double thisGroupSize = (MAX_GROUP_VERTEX_SIZE / rectangularSpiralLayout.getMaxGroupSize()) * (Integer) rectangularSpiralLayout.getGroupSize().get(nodeId);

                if (isDrawSubgraph() && getSubGraph().containsVertex(nodeId)) { // is it part of the subgraph?
                    selectedGroupVertexVisualizer.setRadius(thisGroupSize);
                    selectedGroupVertexVisualizer.draw(drawable, nodeId);
                } else {
                    groupVertexVisualizer.setRadius(thisGroupSize);
                    groupVertexVisualizer.draw(drawable, nodeId);
                }

            } else {
//                if (GraphUtils.getInstance().isLeaf(getGraph(), nodeId)) {
                super.drawVertex(drawable, nodeId);
                //              }
            }
        }
    }

    @Override
    public void drawEdge(GLAutoDrawable drawable, Object edgeId) {
        RectangularSpiralLayout spiralLayout = (RectangularSpiralLayout) getLayout();

        Object source = getGraph().getSource(edgeId);
        Object dest = getGraph().getDest(edgeId);

        if (spiralLayout.getVertices().contains(source) && spiralLayout.getVertices().contains(dest)) {
            super.drawEdge(drawable, edgeId);
        }
    }

    public RectVertexVisualizer getGroupVertexVisualizer() {
        return groupVertexVisualizer;
    }

    public void setGroupVertexVisualizer(RectVertexVisualizer groupVertexVisualizer) {
        this.groupVertexVisualizer = groupVertexVisualizer;
    }

    public RectVertexVisualizer getSelectedGroupVertexVisualizer() {
        return selectedGroupVertexVisualizer;
    }

    public void setSelectedGroupVertexVisualizer(RectVertexVisualizer selectedGroupVertexVisualizer) {
        this.selectedGroupVertexVisualizer = selectedGroupVertexVisualizer;
    }
}