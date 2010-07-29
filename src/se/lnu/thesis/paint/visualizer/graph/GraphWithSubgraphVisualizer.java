package se.lnu.thesis.paint.visualizer.graph;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.visualizer.element.AbstractGraphElementVisualizer;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.04.2010
 * Time: 19:12:37
 */
public class GraphWithSubgraphVisualizer extends GraphVisualizer {

    private Graph subGraph;
    private boolean drawSubgraph = false;
    private Object selectedNode;

    private AbstractGraphElementVisualizer subGraphVertexVizualizer;
    private AbstractGraphElementVisualizer subGraphEdgeVizualizer;

    @Override
    public void drawVertex(GLAutoDrawable drawable, Object nodeId) {
        if (isDrawSubgraph() && subGraph.containsVertex(nodeId)) { // is it part of the subgraph?
            drawSubgraphVertex(drawable, nodeId);
        } else {
            drawGraphVertex(drawable, nodeId);
        }
    }

    protected void drawGraphVertex(GLAutoDrawable drawable, Object nodeId) {
        if (getVertexVisualizer() != null) {
            //       getVertexVisualizer().draw(drawable, nodeId);
        }
    }

    protected void drawSubgraphVertex(GLAutoDrawable drawable, Object nodeId) {
        if (getSubGraphVertexVizualizer() != null) {
            //getSubGraphVertexVizualizer().draw(drawable, nodeId);
        }
    }

    @Override
    public void drawEdge(GLAutoDrawable drawable, Object edgeId) {
        Object source = getGraph().getSource(edgeId);
        Object dest = getGraph().getDest(edgeId);

        if (isDrawSubgraph() && subGraph.containsVertex(source) && subGraph.containsVertex(dest)) { // is it part of the subgraph?
            drawSubgraphEdge(drawable, edgeId);
        } else {
            drawGraphEdge(drawable, edgeId);
        }
    }

    protected void drawSubgraphEdge(GLAutoDrawable drawable, Object edgeId) {
        if (getSubGraphEdgeVizualizer() != null) {
            //       getSubGraphEdgeVizualizer().draw(drawable, edgeId);
        }
    }

    protected void drawGraphEdge(GLAutoDrawable drawable, Object edgeId) {
        if (getEdgeVisualizer() != null) {
            //       getEdgeVisualizer().draw(drawable, edgeId);
        }
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

    public Object getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(Object selectedNode) {
        this.selectedNode = selectedNode;
    }
}
