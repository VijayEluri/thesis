package io;

import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.03.2010
 * Time: 21:24:09
 * <p/>
 * Load cluster graph from graphml file to JUNG Tree
 */
public class JungTreeYedHandler extends JungYedHandler {

    public JungTreeYedHandler() {
        super();
    }

    @Override
    protected void endTagGraph() {


        graphs.add(new DelegateTree((DirectedGraph) graph));

        graph = null;
    }
}
