package se.lnu.thesis.io.graphml;

import se.lnu.thesis.core.MyGraph;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 23:42:08
 * <p/>
 * Load graph and labels from graphml file made by Yed to MyGraph class
 * Graph stores id -> label pair for each node as bidirectional map - it means that node id and label should
 * be unique otherwise it modifies label = id_label
 */
public class MyGraphYedHandler extends AbstractYedHandler {

    public MyGraphYedHandler() {
        initGraphsList();
    }

    protected void startTagGraph() {
        graph = new MyGraph();
    }

    protected void labelTagValue(String label) {
        MyGraph myGraph = (MyGraph) graph;
        myGraph.addLabel(currentNode, label);
    }


    /**
     * Deprecated!
     * Use MyGraph.getNodeLabel() insteed.
     *
     * @return Allways returns null;
     */
    @Deprecated
    public Map getIdLabel() {
        return null;
    }

}
