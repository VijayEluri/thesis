package se.lnu.thesis.core;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 23:08:16
 * <p/>
 * My graph implementation
 */
public class MyGraph<V, E> extends DirectedSparseGraph<V, E> {

    /*TODO improve this implementation by making my own, where leafs and nodes should be stored separatedly;
    also maybe make my own tree implementation with getRoot() and node levels
    * */

    private Map<V, String> nodeLabel = new HashMap<V, String>(); // TODO use GoogleCollection Bidirectional Map

    public Map<V, String> getNodeLabel() {
        return nodeLabel;
    }


    /**
     * Returns all nodes with selected label in graph
     *
     * @param label Label string
     * @return Set of node keys
     */
    public Set<V> getNodesByLabel(String label) {
        Set result = new HashSet();

        for (V v : nodeLabel.keySet()) {
            if (label.compareTo(nodeLabel.get(v)) == 0) {
                result.add(v);
            }
        }

        return result;
    }

    /**
     * Returns all leafs with selected label in graph
     *
     * @param label Label string
     * @return Set of node keys
     */
    public Set<V> getLeafsByLabel(String label) {
        Set result = new HashSet();

        for (V v : nodeLabel.keySet()) {
            if (label.compareTo(nodeLabel.get(v)) == 0) {
                if (outDegree(v) == 0) {
                    result.add(v);
                }
            }
        }

        return result;
    }

    /**
     * Get first node with selected label
     *
     * @param label Node label string
     * @return node key
     */
    public V getNodeByLabel(String label) {

        for (V v : nodeLabel.keySet()) {
            if (label.compareTo(nodeLabel.get(v)) == 0) {
                return v;
            }
        }

        return null;
    }

    public String getLabel(V node) {
        return nodeLabel.get(node);
    }

    public Map<V, String> getNodeLabelMap() {
        return nodeLabel;
    }


}
