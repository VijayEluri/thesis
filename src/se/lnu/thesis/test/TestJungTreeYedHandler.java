package se.lnu.thesis.test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Tree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.JungTreeYedHandler;
import se.lnu.thesis.io.JungYedHandler;
import se.lnu.thesis.layout.AbstractPolarDendrogramLayout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.layout.TreePolarDendrogramLayout;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 16:58:02
 */
public class TestJungTreeYedHandler {

    final int nodes = 14623;
    final int edges = 14622;

    @Test
    public void loadTree() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());
        Tree tree = (Tree) parser.load(new File("cluster.graphml")).get(0);

        assertEquals(23, tree.getVertexCount());
        assertEquals(11, tree.getHeight());

        assertTrue(tree.containsVertex("n10"));
        assertEquals(5, tree.getDepth("n10"));

        assertTrue(tree.containsVertex("n0"));
        assertEquals(0, tree.getDepth("n0"));
    }

    @Test
    public void loadTry() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

        Graph graph = null;
        long start = System.currentTimeMillis();
        try {
            graph = (Graph) parser.load("RealClusterGraph.graphml").get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        assertEquals(nodes, graph.getVertexCount());
        assertEquals(edges, graph.getEdgeCount());
    }

    @Test
    public void load() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

        Graph graph = null;
        long start = System.currentTimeMillis();
        graph = (Graph) parser.load("RealClusterGraph.graphml").get(0);
        long end = System.currentTimeMillis();

        System.out.println("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        assertEquals(nodes, graph.getVertexCount());
        assertEquals(edges, graph.getEdgeCount());
    }


    @Test
    public void treeLayoutPerfomance() {


        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load("RealClusterGraph.graphml").get(0);
        long end = System.currentTimeMillis();

        System.out.println("'JungTreeYedHandler' done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        assertEquals(nodes, graph.getVertexCount());
        assertEquals(edges, graph.getEdgeCount());


        AbstractPolarDendrogramLayout layout = new TreePolarDendrogramLayout((Tree) graph);
        layout.setRadius(0.45 * 800);
        layout.setXCenter(400);
        layout.setYCenter(400);
        layout.setSize(new Dimension(800, 800));

        System.gc();

        start = System.currentTimeMillis();
        layout.initialize();
        end = System.currentTimeMillis();

        System.out.println("Initialize layout using JUNG Tree done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
    }

    @Test
    public void graphLayoutPerfomance() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load("RealClusterGraph.graphml").get(0);
        long end = System.currentTimeMillis();

        System.out.println("'JungYedHandler' done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        assertEquals(nodes, graph.getVertexCount());
        assertEquals(edges, graph.getEdgeCount());


        AbstractPolarDendrogramLayout layout = new PolarDendrogramLayout(graph);
        layout.setRadius(0.45 * 800);
        layout.setXCenter(400);
        layout.setYCenter(400);
        layout.setSize(new Dimension(800, 800));

        System.gc();

        start = System.currentTimeMillis();
        layout.initialize();
        end = System.currentTimeMillis();

        System.out.println("Initialize layout using Graph done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
    }


}
