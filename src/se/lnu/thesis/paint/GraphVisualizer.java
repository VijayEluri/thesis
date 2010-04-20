package se.lnu.thesis.paint;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;


public class GraphVisualizer extends Visualizer {

    private Graph graph;

    private AbstractLayout layout;

    private GraphElementVisualizer edgeVisualizer;
    private GraphElementVisualizer vertexVisualizer;


    private GraphVisualizer() {
    }

    public GraphVisualizer(PApplet applet) {
        setApplet(applet);
    }

    protected void drawScene() {
        for (Object edge : graph.getEdges()) { // first draw edge
            drawEdge(edge);
        }

        for (Object node : graph.getVertices()) { // draw vertex
            drawVertex(node);
        }
    }

    public void drawVertex(Object nodeId) {
        vertexVisualizer.draw(nodeId);
    }

    public void drawEdge(Object edgeId) {
        edgeVisualizer.draw(edgeId);
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

    public void setEdgeVisualizer(GraphElementVisualizer edgeVisualizer) {
        this.edgeVisualizer = edgeVisualizer;
    }

    public GraphElementVisualizer getVertexVisualizer() {
        return vertexVisualizer;
    }

    public void setVertexVisualizer(GraphElementVisualizer vertexVisualizer) {
        this.vertexVisualizer = vertexVisualizer;
    }

}
