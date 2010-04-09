package se.lnu.thesis.io;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * User: vady
 * Date: 28.01.2010
 * Time: 0:01:49
 */
public class JungYedHandler extends AbstractYedHandler {

    protected Map<Object, String> idLabel;

    public JungYedHandler() {

        initGraphsList();

        idLabel = new HashMap<Object, String>();
    }

    protected void labelTagValue(String label) {
        idLabel.put(currentNode, label);
    }

    protected void startTagGraph() {
        graph = new DirectedSparseGraph();
    }

    public Map getIdLabel() {
        return idLabel;
    }

}
