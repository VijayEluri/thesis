package se.lnu.thesis.core;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 23:08:16
 * <p/>
 * My graph implementation
 */
public class MyGraph<V, E> extends DirectedSparseGraph<V, E> {

    /*TODO improve this implementation by making my own, where leafs and nodes should be stored separatedly; also maybe make my own tree implementation with getRoot() and node levels
    */

    private Map<V, String> nodeLabel = new HashMap<V, String>(); // TODO use GoogleCollection Bidirectional Map

    public boolean addLabel(V o, String label) {
        nodeLabel.put(o, label);

        return nodeLabel.containsKey(o);
    }

    @Override
    public boolean removeVertex(V v) {
        if (super.removeVertex(v)) {
            nodeLabel.remove(v);

            return true;
        }

        return false;
    }

    public boolean removeLabel(V v) {
        nodeLabel.remove(v);

        return !nodeLabel.containsKey(v);
    }

    public ImmutableCollection<String> getLabels() {
        return ImmutableMultiset.copyOf(nodeLabel.values()); // TODO use ImmutableSet after switching to BiMap
    }

    public Iterator<String> getLabelsIterator() {

        final Iterator<String> labelsIterator = nodeLabel.values().iterator();

        return new Iterator() {

            public void remove() {
                throw new UnsupportedOperationException("This iterator doesnt allow collection modifications");
            }

            public boolean hasNext() {
                return labelsIterator.hasNext();
            }

            public Object next() {
                return labelsIterator.next();
            }

        };

    }


    /**
     * Returns all nodes with selected label in graph
     *
     * @param label Label string
     * @return Set of node keys
     */
    public ImmutableSet<V> getNodesByLabel(String label) {
        Set result = new HashSet();

        for (V v : nodeLabel.keySet()) {
            if (label.compareTo(nodeLabel.get(v)) == 0) {
                result.add(v);
            }
        }

        return ImmutableSet.copyOf(result);
    }

    /**
     * Returns all leafs with selected label in graph
     *
     * @param label Label string
     * @return Set of node keys
     */
    public ImmutableSet<V> getLeafsByLabel(String label) {
        Set result = new HashSet();

        for (V v : nodeLabel.keySet()) {
            if (label.compareTo(nodeLabel.get(v)) == 0) {
                if (outDegree(v) == 0) {
                    result.add(v);
                }
            }
        }

        return ImmutableSet.copyOf(result);
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

    public int getLabelCount() {
        return nodeLabel.values().size();
    }

    public boolean containsLabel(String label) {
        return nodeLabel.values().contains(label);
    }
}
