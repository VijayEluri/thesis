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
    private boolean drawSubgraph;

    private AbstractGraphElementVisualizer subGraphVertexVizualizer;
    private AbstractGraphElementVisualizer subGraphEdgeVizualizer;

    public GraphWithSubgraphVisualizer(PApplet applet) {
        super(applet);
    }

    @Override
    public void drawVertex(Object nodeId) {
        if (isDrawSubgraph() && subGraph.containsVertex(nodeId)) { // is it part of the subgraph?
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

        if (isDrawSubgraph() && subGraph.containsVertex(source) && subGraph.containsVertex(dest)) { // is it part of the subgraph?
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

    public AbstractGraphElementVisualizer getSubGraphVertexVizualizer() {
        return subGraphVertexVizualizer;
    }

    public void setSubGraphVertexVizualizer(AbstractGraphElementVisualizer subGraphVertexVizualizer) {
        this.subGraphVertexVizualizer = subGraphVertexVizualizer;
    }

    public AbstractGraphElementVisualizer getSubGraphEdgeVizualizer() {
        return subGraphEdgeVizualizer;
    }

    public void setSubGraphEdgeVizualizer(AbstractGraphElementVisualizer subGraphEdgeVizualizer) {
        this.subGraphEdgeVizualizer = subGraphEdgeVizualizer;
    }

    public Graph getSubGraph() {
        return subGraph;
    }

    public void setSubGraph(Graph subGraph) {
        this.subGraph = subGraph;
    }

    public boolean isDrawSubgraph() {
        return drawSubgraph;
    }

    public void drawSubgraph() {
        if (subGraph != null) {
            setDrawSubgraph(true);
        }
    }

    public void setDrawSubgraph(boolean drawSubgraph) {
        this.drawSubgraph = drawSubgraph;
    }
}
