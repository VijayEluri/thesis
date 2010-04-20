package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.04.2010
 * Time: 19:12:37
 */
public class GraphWithSubgraphVisualizer extends GraphVisualizer {

    private Graph subGraph;

    private GraphElementVisualizer subGraphVertexVizualizer;
    private GraphElementVisualizer subGraphEdgeVizualizer;

    public GraphWithSubgraphVisualizer(PApplet applet) {
        super(applet);
    }

    @Override
    public void drawVertex(Object nodeId) {
        if (subGraph != null && subGraph.containsVertex(nodeId)) { // if part of the subtree - draw yellow
            drawSubgraphVertex(nodeId);
        } else {
            drawGraphVertex(nodeId);
        }
    }

    protected void drawGraphVertex(Object nodeId) {
        getVertexVisualizer().draw(nodeId);
    }

    protected void drawSubgraphVertex(Object nodeId) {
        getSubGraphVertexVizualizer().draw(nodeId);
    }

    @Override
    public void drawEdge(Object edgeId) {
        Object source = getGraph().getSource(edgeId);
        Object dest = getGraph().getDest(edgeId);

        if (subGraph != null && subGraph.containsVertex(source) && subGraph.containsVertex(dest)) { // if part of the subtree - draw yellow
            drawSubgraphEdge(edgeId);
        } else {
            drawGraphEdge(edgeId);
        }
    }

    protected void drawSubgraphEdge(Object edgeId) {
        getSubGraphEdgeVizualizer().draw(edgeId);
    }

    protected void drawGraphEdge(Object edgeId) {
        getEdgeVisualizer().draw(edgeId);
    }

    public GraphElementVisualizer getSubGraphVertexVizualizer() {
        return subGraphVertexVizualizer;
    }

    public void setSubGraphVertexVizualizer(GraphElementVisualizer subGraphVertexVizualizer) {
        this.subGraphVertexVizualizer = subGraphVertexVizualizer;
    }

    public GraphElementVisualizer getSubGraphEdgeVizualizer() {
        return subGraphEdgeVizualizer;
    }

    public void setSubGraphEdgeVizualizer(GraphElementVisualizer subGraphEdgeVizualizer) {
        this.subGraphEdgeVizualizer = subGraphEdgeVizualizer;
    }

    public Graph getSubGraph() {
        return subGraph;
    }

    public void setSubGraph(Graph subGraph) {
        this.subGraph = subGraph;
    }

}
