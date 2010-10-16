package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.Container;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.07.2010
 * Time: 16:41:46
 */
public abstract class AbstractLayout {

    protected Graph graph;
    protected Container root;

    public AbstractLayout() {
    }

    public AbstractLayout(Graph graph) {
        setGraph(graph);
    }


    public AbstractLayout(Graph graph, Container root) {
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

    public void setRoot(Container root) {
        this.root = root;
    }

    public Container getRoot() {
        return root;
    }
}
