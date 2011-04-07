import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.utils.GraphStatisticUtil;
import se.lnu.thesis.utils.GraphUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 31.05.2010
 * Time: 17:23:41
 */
public class TestRealData {

    private static final Logger LOGGER = Logger.getLogger(TestRealData.class);

    final int CLUSTER_NODE_COUNT = 14623;
    final int CLUSTER_EDGE_COUNT = 14622;

    final int DAG_NODE_COUNT = 10042;
    final int DAG_EDGE_COUNT = 24155;

    @Test
    public void testDagDataQuantity() {


        IOFacade ioFacade = new IOFacade();
        MyGraph go = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("RealGOGraph.graphml").getPath());

        assertEquals(DAG_NODE_COUNT, go.getVertexCount());
        assertEquals(DAG_EDGE_COUNT, go.getEdgeCount());

        GraphStatisticUtil.printGraphStatistic(go);
    }

    @Test
    public void testClusterDataQuantity() {


        IOFacade ioFacade = new IOFacade();
        MyGraph cluster = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath());

        assertEquals(CLUSTER_NODE_COUNT, cluster.getVertexCount());
        assertEquals(CLUSTER_EDGE_COUNT, cluster.getEdgeCount());

        GraphStatisticUtil.printGraphStatistic(cluster);
    }

    @Test
    public void findGeneOntologyLabelDuplicates() throws Exception {

        IOFacade ioFacade = new IOFacade();
        MyGraph go = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("RealGOGraph.graphml").getPath());

        Set<String> duplicates = new HashSet<String>();

        Collection<String> labels = go.getLabels();
        for (String label : labels) {
            if (Collections.frequency(labels, label) > 1) {
                duplicates.add(label);
            }
        }

        LOGGER.info("LABEL DUPLICATES: ");
        for (String label : duplicates) {
            LOGGER.info(" -> " + label + " [" + Joiner.on(", ").join(go.getNodesByLabel(label)) + "]");
        }

        assertEquals(1, duplicates.size()); // one dublicate 'biological_process'
        assertTrue(duplicates.contains("biological_process"));

    }

    @Test
    public void findClusterLabelDuplicates() throws Exception {

        IOFacade ioFacade = new IOFacade();
        MyGraph cluster = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath());

        Set<String> duplicates = new HashSet<String>();

        Collection<String> labels = cluster.getLabels();
        for (String label : labels) {
            if (Collections.frequency(labels, label) > 1) {
                duplicates.add(label);
            }
        }

        for (String label : duplicates) {
            LOGGER.info(label);
        }

        assertEquals(0, duplicates.size());
    }

    @Test
    public void checkGroupCluster() throws Exception {

        IOFacade ioFacade = new IOFacade();
        MyGraph graph = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("RealClusterGraph.graphml").getPath());

        MyGraph subGraph = new MyGraph();

        final String node = "n12859";
        assertTrue(graph.containsVertex(node));

        GraphUtils.extractSubgraph(graph, subGraph, node);


        assertEquals(19, subGraph.getVertexCount());

        GraphStatisticUtil.printGraphStatistic(subGraph);
    }


    @Test
    public void testGeneOntologyLevels() throws Exception {

        IOFacade ioFacade = new IOFacade();
        MyGraph graph = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("RealGOGraph.graphml").getPath());

        Set roots = GraphUtils.getRoots(graph);
        System.out.println("Roots: " + Joiner.on(" ,").join(roots));

        Multimap levels = TreeMultimap.create();
        GraphUtils.computeLevels(graph, levels);

        Collection level0 = levels.get(0);
        assertTrue(level0.size() == roots.size());
        assertTrue(level0.containsAll(roots));

        for (Object key : levels.keySet()) {
            System.out.println(key + ": " + Joiner.on(" ").join(levels.get(key)));
        }


    }


}
