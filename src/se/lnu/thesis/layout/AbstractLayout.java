package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.CompositeElement;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.07.2010
 * Time: 16:41:46
 */
public abstract class AbstractLayout {

    protected Graph graph;
    protected CompositeElement root;

    public AbstractLayout() {
    }

    public AbstractLayout(Graph graph, CompositeElement root) {
        setGraph(graph);
        setRoot(root);
    }

    public abstract void compute();

    public void reset() {
        setGraph(null);
        setRoot(null);
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void setRoot(CompositeElement root) {
        this.root = root;
    }

    public CompositeElement getRoot() {
        return root;
    }
}
