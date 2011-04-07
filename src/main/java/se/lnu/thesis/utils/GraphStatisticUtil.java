package se.lnu.thesis.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import edu.uci.ics.jung.graph.Graph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 07.04.11
 * Time: 21:05
 */
public class GraphStatisticUtil {

    public static class GraphStatistic<V, E> {

        protected int nodes = 0;
        protected int leafs = 0;
        protected int unconnectedComponents = 0;

        protected Collection<V> roots = null;

        private int edgeCount;

        protected Multimap successorsStatistic = null;
        protected Multimap predecessorsStatistic = null;

        public void clear() {
            nodes = 0;
            leafs = 0;
            unconnectedComponents = 0;

            if (roots == null) {
                roots = new HashSet<V>();
            } else {
                roots.clear();
            }

            edgeCount = 0;

            if (successorsStatistic == null) {
                successorsStatistic = TreeMultimap.create();
            } else {
                successorsStatistic.clear();
            }

            if (predecessorsStatistic == null) {
                predecessorsStatistic = TreeMultimap.create();
            } else {
                predecessorsStatistic.clear();
            }

        }

        public void compute(Graph<V, E> graph) {
            clear();

            for (V vertex : graph.getVertices()) {
                if (GraphUtils.isUnconnectedComponent(graph, vertex)) {
                    unconnectedComponents++;
                } else {
                    if (GraphUtils.isLeaf(graph, vertex)) {
                        leafs++;
                    } else {
                        nodes++;

                        predecessorsStatistic.put(graph.inDegree(vertex), vertex);
                        successorsStatistic.put(graph.outDegree(vertex), vertex);

                        if (GraphUtils.isRoot(graph, vertex)) {
                            roots.add(vertex);
                        }
                    }

                }
            }

            edgeCount = graph.getEdgeCount();

        }

        /**
         * Returns printable information.
         *
         * @return Returns formatted information. Sample is showed above.
         */
        @Override
        public String toString() {
            StringBuffer result = new StringBuffer();

            Joiner joiner = Joiner.on(", ");

            result.append("***********************************");
            result.append("\n");
            result.append("   Graph statistic::");
            result.append("\n");
            result.append("       Vertex count " + getVertexCount());
            result.append("\n");
            result.append("           Roots " + roots.size());
            result.append("\n");
            result.append("               " + joiner.join(roots));
            result.append("\n");
            result.append("           Nodes " + nodes);
            result.append("\n");
            result.append("           Leafs " + leafs);
            result.append("\n");

            result.append("       Unconnected components: " + unconnectedComponents);
            result.append("\n");

            result.append("       Edge count " + getEdgeCount());
            result.append("\n");

            result.append("           Maximum predecessor count is " + getMaxPredecessorCount() + ", this vertex amount is  " + predecessorsStatistic.get(getMaxPredecessorCount()).size());
            result.append("\n");

            result.append("           Predecessor statistic [predecessor_count -> vertex_count]");
            result.append("\n");
            for (Object count : predecessorsStatistic.keySet()) {
                result.append("             " + count + " -> " + predecessorsStatistic.get(count).size() + "\n");

            }

            result.append("\n");

            result.append("           Maximum successors count is " + getMaxSuccessorCount() + ", this vertex amount is " + successorsStatistic.get(getMaxSuccessorCount()).size());
            result.append("\n");

            result.append("           Successors statistic [successor_count -> vertex_count]");
            result.append("\n");
            for (Object count : successorsStatistic.keySet()) {
                result.append("             " + count + " -> " + successorsStatistic.get(count).size() + "\n");

            }

            result.append("***********************************");


            return result.toString();
        }

        /**
         * @return Maximum successor count for vertex
         */
        public Integer getMaxSuccessorCount() {
            return (Integer) Collections.max(successorsStatistic.keySet());
        }

        /**
         * @return Maximum predecessors count for vertex
         */
        public Integer getMaxPredecessorCount() {
            return (Integer) Collections.max(predecessorsStatistic.keySet());
        }

        /**
         * Return vertex count for current graph.
         *
         * @return Vertex count
         */
        public int getVertexCount() {
            return leafs + nodes + unconnectedComponents;
        }

        public int getEdgeCount() {
            return edgeCount;
        }

        public int getNodes() {
            return nodes;
        }

        public int getLeafs() {
            return leafs;
        }

        public int getUnconnectedComponents() {
            return unconnectedComponents;
        }

        /**
         * Returns immutable collection of roots for current graph.
         *
         * @return Returns <code>ImmutableSet</code> instance
         */
        public ImmutableCollection<V> getRoots() {
            return ImmutableSet.copyOf(roots);
        }
    }

    /**
     * This method computes GraphStatistic and prints it to standard output.
     * Here is sample output for testing graph:
     *
     * @param graph
     * @param <V>
     * @param <E>
     */
    public static <V, E> void printGraphStatistic(Graph<V, E> graph) {

        GraphStatistic graphStatistic = new GraphStatistic();
        graphStatistic.compute(graph);

        System.out.println(graphStatistic);

    }
}
