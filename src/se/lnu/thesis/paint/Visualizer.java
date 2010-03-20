package se.lnu.thesis.paint;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;


public class Visualizer {

    private PApplet applet;

    private Graph graph;

    private AbstractLayout layout;

    private GraphElementVisualizer edgeVisualizer;
    private GraphElementVisualizer nodeVisualizer;


    public Visualizer() {

    }

    public Visualizer(PApplet applet) {
        setApplet(applet);
    }

    public void draw() {

        getApplet().smooth();
        getApplet().background(0);

        for (Object edge : graph.getEdges()) { // first draw edges!
            edgeVisualizer.draw(edge);
        }

        for (Object node : graph.getVertices()) { // draw nodes!!
            nodeVisualizer.draw(node);
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

}
