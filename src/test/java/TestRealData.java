import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.slf4j.Logger;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.utils.GraphStatisticUtil;
import se.lnu.thesis.utils.GraphUtils;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 31.05.2010
 * Time: 17:23:41
 */
public class TestRealData {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRealData.class);

    public static final int CLUSTER_NODE_COUNT = 14623;
    public static final int CLUSTER_EDGE_COUNT = 14622;

    public static final int GO_NODE_COUNT = 10041;
    public static final int GO_EDGE_COUNT = 24153;
    public static final int GO_LEVEL_COUNT = 17;

    @Test
    public void testDagDataQuantity() {


        IOFacade ioFacade = new IOFacade();
        MyGraph go = ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealGOGraph.gml").getPath());

        assertEquals(GO_NODE_COUNT, go.getVertexCount());
        assertEquals(GO_EDGE_COUNT, go.getEdgeCount());

        GraphStatisticUtil.printGraphStatistic(go);
    }

    @Test
    public void testClusterDataQuantity() {


        IOFacade ioFacade = new IOFacade();
        MyGraph cluster = ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealClusterGraph.gml").getPath());

        assertEquals(CLUSTER_NODE_COUNT, cluster.getVertexCount());
        assertEquals(CLUSTER_EDGE_COUNT, cluster.getEdgeCount());

        GraphStatisticUtil.printGraphStatistic(cluster);
    }

    @Test
    public void findGeneOntologyLabelDuplicates() throws Exception {

        IOFacade ioFacade = new IOFacade();

        try {
            ioFacade.loadMyGraphFromGml(getClass().getClassLoader().getResource("data/RealGOGraph.gml").getPath());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            fail();
        }

    }

    @Test
    public void findClusterLabelDuplicates() throws Exception {

        IOFacade ioFacade = new IOFacade();

        try {
            ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            fail();
        }
    }

    @Test
    public void checkGroupCluster() throws Exception {

        IOFacade ioFacade = new IOFacade();
        MyGraph graph = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("data/RealClusterGraph.graphml").getPath());

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
        MyGraph graph = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("data/RealGOGraph.graphml").getPath());

        assertEquals(GO_NODE_COUNT, graph.getVertexCount());
        assertEquals(GO_EDGE_COUNT, graph.getEdgeCount());

        Set roots = GraphUtils.getRoots(graph);
        System.out.println("Roots: " + Joiner.on(" ,").join(roots));

        Multimap levels = TreeMultimap.create();
        int i = GraphUtils.computeLevels(graph, levels);

        assertEquals(GO_LEVEL_COUNT, i);

        Collection level0 = levels.get(0);
        assertTrue(level0.size() == roots.size());
        assertTrue(level0.containsAll(roots));

        for (Object key : levels.keySet()) {
            System.out.println(key + ": " + Joiner.on(" ").join(levels.get(key)));
        }


    }


}
