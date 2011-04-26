package se.lnu.thesis.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 23:08:16
 * <p/>
 * My graph implementation
 */
public class MyGraph<V, E> extends DirectedSparseGraph<V, E> {


    private BiMap<V, String> node2Label = HashBiMap.create();

    /**
     * Add label for specific vertex.
     *
     * @param o     Vertex object id.
     * @param label Label string.
     * @return <code>True</code> if label successfully added for vertex, <code>False</code> otherwise.
     * @throws IllegalArgumentException Throws exception if label is already exist for another vertex.
     */
    public boolean addLabel(V o, String label) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(label);

        if (node2Label.containsValue(label)) {
            throw new IllegalArgumentException("There is vertex with same label! " + node2Label.inverse().get(label) + " -> '" + label + "'");
        }

        node2Label.put(o, label);

        return node2Label.containsKey(o) && node2Label.containsValue(label);
    }

    /**
     * Change label for specific vertex, if label does not exist for vertex then it will be added.
     *
     * @param o     Vertex object id.
     * @param label String label.
     * @return <code>True</code> if changed successfully, <code>False</code> otherwise.
     */
    public boolean changeLabel(V o, String label) {
        node2Label.put(o, label);

        return node2Label.containsKey(o) && node2Label.containsValue(label);
    }

    @Override
    public boolean removeVertex(V v) {
        if (super.removeVertex(v)) {
            node2Label.remove(v);

            return true;
        }

        return false;
    }

    public boolean removeLabel(V v) {
        node2Label.remove(v);

        return !node2Label.containsKey(v);
    }

    /**
     * Return copy of all labels for this graph
     *
     * @return Immutable collection of labels
     */
    public ImmutableCollection<String> getLabels() {
        return ImmutableSet.copyOf(node2Label.values());
    }

    public UnmodifiableIterator<String> getLabelsIterator() {
        return Iterators.unmodifiableIterator(node2Label.values().iterator());
    }

    /**
     * Get vertex with specified label
     *
     * @param label String label
     * @return node Vertex object id
     */
    public V getNodeByLabel(String label) {
        return node2Label.inverse().get(label);
    }

    /**
     * Get label for vertex
     *
     * @param node Vertex object id
     * @return String label
     */
    public String getLabel(V node) {
        return node2Label.get(node);
    }

    /**
     * @return Label count for this graph
     */
    public int getLabelCount() {
        return node2Label.values().size();
    }

    /**
     * Check if graph contains label.
     *
     * @param label String label to check.
     * @return True if graph contains label, False otherwise.
     */
    public boolean containsLabel(String label) {
        return node2Label.values().contains(label);
    }
}
