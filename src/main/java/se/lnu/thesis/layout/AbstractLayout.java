package se.lnu.thesis.layout;

import com.google.common.base.Preconditions;
import edu.uci.ics.jung.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.element.Container;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.07.2010
 * Time: 16:41:46
 */
public abstract class AbstractLayout implements Layout {

    protected Graph graph;
    protected Container root;
    public static final Logger LOGGER = LoggerFactory.getLogger(UniformDistributionLayout.class);

    public AbstractLayout() {
    }

    public AbstractLayout(Graph graph) {
        setGraph(graph);
    }


    public AbstractLayout(Graph graph, Container root) {
        setGraph(graph);
        setRoot(root);
    }

    public void reset() {
        setGraph(null);
        setRoot(null);
    }

    protected boolean checkArguments() {
        Preconditions.checkNotNull(getGraph());
        Preconditions.checkNotNull(getRoot());

        return true;
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
