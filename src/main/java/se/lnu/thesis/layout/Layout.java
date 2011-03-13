package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.element.Container;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 13.03.11
 * Time: 22:24
 * To change this template use File | Settings | File Templates.
 */
public interface Layout {
    void compute();

    void reset();

    Graph getGraph();

    void setGraph(Graph graph);

    void setRoot(Container root);

    Container getRoot();
}
