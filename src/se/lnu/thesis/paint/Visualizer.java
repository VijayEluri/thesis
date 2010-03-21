package se.lnu.thesis.paint;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;
import se.lnu.thesis.utils.Utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Visualizer {

    private PApplet applet;

    private Graph graph;

    private Map<Object, String> graphIdLabel;
    private Set<String> subgraphLabels;


    private AbstractLayout layout;

    private GraphElementVisualizer edgeVisualizer;
    private GraphElementVisualizer highLightedEdgeVisualizer;
    private GraphElementVisualizer nodeVisualizer;
    private GraphElementVisualizer highLightedNodeVisualizer;


    public Visualizer() {

    }

    public Visualizer(PApplet applet) {
        setApplet(applet);

        subgraphLabels = new HashSet<String>();
        Utils.loadSubtreeLabels(subgraphLabels);
    }

    public void draw() {

        getApplet().smooth();
        getApplet().background(0);

        for (Object edge : graph.getEdges()) { // first draw edges!

            Object source = graph.getSource(edge);
            Object dest = graph.getDest(edge);

            if (subgraphLabels.contains(graphIdLabel.get(source)) && subgraphLabels.contains(graphIdLabel.get(dest))) {
                highLightedEdgeVisualizer.draw(edge);
            } else {
                edgeVisualizer.draw(edge);
            }

        }

        for (Object node : graph.getVertices()) { // draw nodes!!
            if (subgraphLabels.contains(graphIdLabel.get(node))) {
                highLightedNodeVisualizer.draw(node);
            } else {
                nodeVisualizer.draw(node);
            }
        }
    }

    public PApplet getApplet() {
        return applet;
    }

    public void setApplet(PApplet applet) {
        this.applet = applet;
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

    public GraphElementVisualizer getNodeVisualizer() {
        return nodeVisualizer;
    }

    public void setNodeVisualizer(GraphElementVisualizer nodeVisualizer) {
        this.nodeVisualizer = nodeVisualizer;
    }

    public void setGraphIdLabels(Map<Object, String> idLabel) {
        this.graphIdLabel = idLabel;
    }

    public GraphElementVisualizer getHighLightedEdgeVisualizer() {
        return highLightedEdgeVisualizer;
    }

    public void setHighLightedEdgeVisualizer(GraphElementVisualizer highLightedEdgeVisualizer) {
        this.highLightedEdgeVisualizer = highLightedEdgeVisualizer;
    }

    public GraphElementVisualizer getHighLightedNodeVisualizer() {
        return highLightedNodeVisualizer;
    }

    public void setHighLightedNodeVisualizer(GraphElementVisualizer highLightedNodeVisualizer) {
        this.highLightedNodeVisualizer = highLightedNodeVisualizer;
    }
}
