package se.lnu.thesis.paint;

import se.lnu.thesis.layout.RectangularSpiralLayout;

import javax.media.opengl.GLAutoDrawable;


public class RectangularSpiralClusterVisualizer extends GraphWithSubgraphVisualizer {

    private AbstractGraphElementVisualizer groupVertexVisualizer;

    @Override
    public void drawVertex(GLAutoDrawable drawable, Object nodeId) {
        RectangularSpiralLayout spiralLayout = (RectangularSpiralLayout) getLayout();

        if (spiralLayout.getVertices().contains(nodeId)) {
            if (spiralLayout.getGroupVertices().contains(nodeId)) {
                groupVertexVisualizer.draw(drawable, nodeId);
            } else {
                //         if (getGraph().outDegree(nodeId) == 0) {
                super.drawVertex(drawable, nodeId);
                //       }
                //  getVertexVisualizer().draw(drawable, nodeId);
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

    public AbstractGraphElementVisualizer getGroupVertexVisualizer() {
        return groupVertexVisualizer;
    }

    public void setGroupVertexVisualizer(AbstractGraphElementVisualizer groupVertexVisualizer) {
        this.groupVertexVisualizer = groupVertexVisualizer;
    }
}