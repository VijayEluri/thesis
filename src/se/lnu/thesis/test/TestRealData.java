package se.lnu.thesis.test;

import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.IOFacade;
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


    @Test
    public void testDagDataQuantity() {
        final int DAG_NODE_COUNT = 10042;
        final int DAG_EDGE_COUNT = 24155;

        IOFacade ioFacade = new IOFacade();
        MyGraph go = ioFacade.loadFromYedGraphml("RealGOGraph.graphml");

        assertEquals(DAG_NODE_COUNT, go.getVertexCount());
        assertEquals(DAG_EDGE_COUNT, go.getEdgeCount());

        GraphUtils.getInstance().printGraphInfo(go);
    }

    @Test
    public void testClusterDataQuantity() {
        final int CLUSTER_NODE_COUNT = 14623;
        final int CLUSTER_EDGE_COUNT = 14622;

        IOFacade ioFacade = new IOFacade();
        MyGraph cluster = ioFacade.loadFromYedGraphml("RealClusterGraph.graphml");

        assertEquals(CLUSTER_NODE_COUNT, cluster.getVertexCount());
        assertEquals(CLUSTER_EDGE_COUNT, cluster.getEdgeCount());

        GraphUtils.getInstance().printGraphInfo(cluster);
    }

    @Test
    public void findGeneOntologyLabelDublicates() throws Exception {

        IOFacade ioFacade = new IOFacade();
        MyGraph go = ioFacade.loadFromYedGraphml("RealGOGraph.graphml");

        Set<String> dublicates = new HashSet<String>();

        Collection<String> labels = go.getNodeLabelMap().values();
        for (String label : labels) {
            if (Collections.frequency(labels, label) > 1) {
                dublicates.add(label);
            }
        }

        for (String label : dublicates) {
            LOGGER.error(label);
        }

        assertEquals(1, dublicates.size()); // TODO should be zero! 'biological_process'
    }

    @Test
    public void findClusterLabelDublicates() throws Exception {

        IOFacade ioFacade = new IOFacade();
        MyGraph go = ioFacade.loadFromYedGraphml("RealClusterGraph.graphml");

        Set<String> dublicates = new HashSet<String>();

        Collection<String> labels = go.getNodeLabelMap().values();
        for (String label : labels) {
            if (Collections.frequency(labels, label) > 1) {
                dublicates.add(label);
            }
        }

        for (String label : dublicates) {
            LOGGER.error(label);
        }

        assertEquals(0, dublicates.size());
    }


}
