import edu.uci.ics.jung.graph.Graph;
import org.junit.Test;
import static org.junit.Assert.*;

import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.utils.GraphMaker;
import se.lnu.thesis.utils.GraphStatisticUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 07.04.11
 * Time: 21:25
 */
public class TestGraphStatistic {

    @Test
    public void tryOnSmallBinaryTree() {
        Graph graph = GraphMaker.createGraphWithUnconnectedComponents();

        assertEquals(9, graph.getVertexCount());
        assertEquals(4, graph.getEdgeCount());

        GraphStatisticUtil.GraphStatistic graphStatistic = new GraphStatisticUtil.GraphStatistic();
        graphStatistic.compute(graph);

        assertEquals(graph.getVertexCount(), graphStatistic.getVertexCount());
        assertEquals(9, graphStatistic.getVertexCount());

        assertEquals(graph.getEdgeCount(), graphStatistic.getEdgeCount());
        assertEquals(4, graphStatistic.getEdgeCount());

        assertEquals(1, graphStatistic.getRoots().size());

        assertEquals(2, graphStatistic.getLeafs());
        assertEquals(3, graphStatistic.getNodes());
        assertEquals(4, graphStatistic.getUnconnectedComponents());

        assertEquals(Integer.valueOf(1), graphStatistic.getMaxPredecessorCount());
        assertEquals(Integer.valueOf(2), graphStatistic.getMaxSuccessorCount());

    }

    @Test
    public void computeAsterixGeneralTree() {
        IOFacade ioFacade = new IOFacade();

        Graph graph = ioFacade.loadFromGml(getClass().getClassLoader().getResource("Asterix.gml").getPath());

        assertEquals(100, graph.getVertexCount());
        assertEquals(99, graph.getEdgeCount());

        GraphStatisticUtil.GraphStatistic graphStatistic = new GraphStatisticUtil.GraphStatistic();
        graphStatistic.compute(graph);

        assertEquals(graph.getVertexCount(), graphStatistic.getVertexCount());
        assertEquals(100, graphStatistic.getVertexCount());

        assertEquals(graph.getEdgeCount(), graphStatistic.getEdgeCount());
        assertEquals(99, graphStatistic.getEdgeCount());

        assertEquals(1, graphStatistic.getRoots().size());

        assertEquals(37, graphStatistic.getLeafs());
        assertEquals(63, graphStatistic.getNodes());
        assertEquals(0, graphStatistic.getUnconnectedComponents());

        assertEquals(Integer.valueOf(1), graphStatistic.getMaxPredecessorCount());
        assertEquals(Integer.valueOf(4), graphStatistic.getMaxSuccessorCount());

        System.out.println(graphStatistic);
    }

    @Test
    public void computeSmallGOGraph() {
        IOFacade ioFacade = new IOFacade();

        Graph graph = ioFacade.loadFromYedGraphml(getClass().getClassLoader().getResource("smallGO.graphml").getPath());

        assertEquals(10, graph.getVertexCount());
        assertEquals(12, graph.getEdgeCount());

        GraphStatisticUtil.GraphStatistic graphStatistic = new GraphStatisticUtil.GraphStatistic();
        graphStatistic.compute(graph);

        assertEquals(graph.getVertexCount(), graphStatistic.getVertexCount());
        assertEquals(10, graphStatistic.getVertexCount());

        assertEquals(graph.getEdgeCount(), graphStatistic.getEdgeCount());
        assertEquals(12, graphStatistic.getEdgeCount());

        assertEquals(4, graphStatistic.getRoots().size());

        assertEquals(4, graphStatistic.getLeafs());
        assertEquals(6, graphStatistic.getNodes());
        assertEquals(0, graphStatistic.getUnconnectedComponents());

        assertEquals(Integer.valueOf(2), graphStatistic.getMaxPredecessorCount());
        assertEquals(Integer.valueOf(3), graphStatistic.getMaxSuccessorCount());

        System.out.println(graphStatistic);
    }

}
