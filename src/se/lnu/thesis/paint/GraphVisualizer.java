package se.lnu.thesis.paint;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;

import javax.media.opengl.GLAutoDrawable;


public class GraphVisualizer extends Visualizer {

    private Graph graph;

    private AbstractLayout layout;

    private AbstractGraphElementVisualizer edgeVisualizer;
    private AbstractGraphElementVisualizer vertexVisualizer;


    protected void drawScene(GLAutoDrawable drawable) {
        for (Object edge : graph.getEdges()) { // first draw edge
            drawEdge(drawable, edge);
        }

        for (Object node : graph.getVertices()) { // draw vertex
            drawVertex(drawable, node);
        }
    }

    public void drawVertex(GLAutoDrawable drawable, Object nodeId) {
        vertexVisualizer.draw(drawable, nodeId);
    }

    public void drawEdge(GLAutoDrawable drawable, Object edgeId) {
        edgeVisualizer.draw(drawable, edgeId);
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public AbstractLayout getLayout() {
        return layout;
    }

    public void setLayout(AbstractLayout layout) {
        this.layout = layout;
    }

    public GraphElementVisualizer getEdgeVisualizer() {
        return edgeVisualizer;
    }

    public void setEdgeVisualizer(AbstractGraphElementVisualizer edgeVisualizer) {
        this.edgeVisualizer = edgeVisualizer;
    }

    public AbstractGraphElementVisualizer getVertexVisualizer() {
        return vertexVisualizer;
    }

    public void setVertexVisualizer(AbstractGraphElementVisualizer vertexVisualizer) {
        this.vertexVisualizer = vertexVisualizer;
    }

}
