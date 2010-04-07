package se.lnu.thesis.paint;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;


public class Visualizer {

    public static final float DEFAULT_ZOOM_STEP = 0.05f;
    public static final float DEFAULT_MOVE_STEP = 5.0f;

    private PApplet applet;

    private Graph graph;

    private AbstractLayout layout;
    private GraphElementVisualizer edgeVisualizer;

    private GraphElementVisualizer nodeVisualizer;

    private float zoom = 1.0f;

    private float sceneCenterX = 0.0f;
    private float sceneCenterY = 0.0f;

    private float zoomStep = DEFAULT_ZOOM_STEP;
    private float moveStep = DEFAULT_MOVE_STEP;


    public Visualizer() {

    }

    public Visualizer(PApplet applet) {
        setApplet(applet);
    }

    public void draw() {
        getApplet().pushMatrix();

        getApplet().scale(zoom);
        getApplet().translate(sceneCenterX, sceneCenterY);


        getApplet().smooth();
        getApplet().background(0);

        for (Object edge : graph.getEdges()) { // first draw edges!
            edgeVisualizer.draw(edge);
        }

        for (Object node : graph.getVertices()) { // draw nodes!!
            nodeVisualizer.draw(node);
        }

        getApplet().popMatrix();
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

    public void zoomOut() {
        zoom -= zoomStep;
    }

    public void zoomIn() {
        zoom += zoomStep;
    }

    public void up() {
        sceneCenterY += moveStep;
    }

    public void down() {
        sceneCenterY -= moveStep;
    }

    public void left() {
        sceneCenterX -= moveStep;
    }

    public void right() {
        sceneCenterX += moveStep;
    }

    public void move(float stepX, float stepY) {
        sceneCenterX += stepX;
        sceneCenterY += stepY;
    }

    public float getZoomStep() {
        return zoomStep;
    }

    public void setZoomStep(float zoomStep) {
        this.zoomStep = zoomStep;
    }

    public float getMoveStep() {
        return moveStep;
    }

    public void setMoveStep(float moveStep) {
        this.moveStep = moveStep;
    }
}
