package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.gui.ThesisApplet;
import se.lnu.thesis.paint.vertex.RectVertexVisualizer;
import se.lnu.thesis.utils.GraphUtils;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.04.2010
 * Time: 19:43:28
 * <p/>
 * Visualizer for cluster graph.
 */
public class ClusterGraphVisualizer extends GraphVisualizer {

    private Extractor extractor;

    private MyGraph goGraph;
    private Object selectedGoNode;
    private Graph subGraph;

    private AbstractGraphElementVisualizer selectedVerticesVisualizer = new RectVertexVisualizer(this);


    public ClusterGraphVisualizer(ThesisApplet applet) {
        super(applet);

        extractor = new Extractor();
    }

    @Override
    public void drawVertex(Object node) {

        if (GraphUtils.getInstance().isLeaf(getGraph(), node)) {
            drawLeaf(node);
        } else {
            if (GraphUtils.getInstance().isRoot(getGraph(), node)) {
                drawRoot(node);
            } else {
                drawNode(node);
            }
        }

    }

    public void drawRoot(Object root) {
        if (subGraph != null && subGraph.containsVertex(root)) { // if part of the subtree - draw yellow
            getApplet().fill(255, 255, 0);
            getApplet().stroke(255, 255, 0);
        } else {
            getApplet().fill(255); // draw root white
            getApplet().stroke(255);
        }

        getVertexVisualizer().draw(root);
    }

    @Deprecated
    public void drawNode(Object node) {
        // do not draw nodes
    }

    public void drawLeaf(Object leaf) {
        if (subGraph != null && subGraph.containsVertex(leaf)) { // if part of the subtree - draw yellow
            getApplet().fill(255, 255, 0);
            getApplet().stroke(255, 255, 0);
            selectedVerticesVisualizer.draw(leaf);
        } else {
            getApplet().stroke(255, 0, 0); // draw leaf red
            getApplet().fill(255, 0, 0);
            getVertexVisualizer().draw(leaf);
        }

    }


    @Override
    public void drawEdge(Object edge) {

        Object source = getGraph().getSource(edge);
        Object dest = getGraph().getDest(edge);

        if (subGraph != null && subGraph.containsVertex(source) && subGraph.containsVertex(dest)) { // if part of the subtree - draw yellow
            getApplet().noFill();
            getApplet().stroke(255, 255, 0);
        } else {
            getApplet().stroke(255); // set edge color to white
            getApplet().noFill();
        }

        getEdgeVisualizer().draw(edge);
    }

    public void setSelectedGoNode(Object selectedGoNode) {
        if (selectedGoNode != null) {
            this.selectedGoNode = selectedGoNode;

            System.out.println("Extracting subgraph.."); //TODO use logger
            subGraph = extractor.extractSubGraph(goGraph, (MyGraph) getGraph(), selectedGoNode);
            System.out.println("Done. [" + subGraph.getVertexCount() + ", " + subGraph.getEdgeCount() + "]"); //TODO use logger
        }
    }

    public void setGoGraph(MyGraph goGraph) {
        this.goGraph = goGraph;
    }

}
