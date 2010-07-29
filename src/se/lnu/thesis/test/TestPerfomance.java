package se.lnu.thesis.test;

import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.Graph;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.JungTreeYedHandler;
import se.lnu.thesis.io.JungYedHandler;
import se.lnu.thesis.io.MyGraphYedHandler;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.04.2010
 * Time: 16:58:02
 */
public class TestPerfomance {

    final int nodes = 14623;
    final int edges = 14622;

    @Test
    public void iterate() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

        Graph graph = (Graph) parser.load("RealClusterGraph.graphml").get(0);

        assertEquals(nodes, graph.getVertexCount());
        assertEquals(edges, graph.getEdgeCount());


        long start = System.currentTimeMillis();

        Set visited = new HashSet();
        Stack stack = new Stack();

        Object root = GraphUtils.getInstance().findRoot((Graph) graph);

        stack.push(root);
        visited.add(root);

        while (!stack.isEmpty()) {
            Object parent = stack.pop();

            for (Object child : ((Graph) graph).getSuccessors(parent)) {
                if (!visited.contains(child)) {
                    visited.add(child);
                    stack.push(child);
                }
            }

        }

        long end = System.currentTimeMillis();

        assertEquals(nodes, visited.size());

        //System.out.println("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
        System.out.println("Done in " + (end - start) + "s");

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
    public void loadMyGraph() {
        GraphMLParser parser = new GraphMLParser(new MyGraphYedHandler());

        long start = System.currentTimeMillis();
        MyGraph graph = (MyGraph) parser.load(new File("RealClusterGraph.graphml")).get(0);
        long end = System.currentTimeMillis();

        System.out.println("Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        assertEquals(nodes, graph.getVertexCount());
        assertEquals(edges, graph.getEdgeCount());

        assertEquals("AFFX-HXB2_5_at", graph.getLabel("n8"));
        assertEquals("n8", graph.getNodeByLabel("AFFX-HXB2_5_at"));
    }


    @Test
    public void graphLayoutPerfomance() {
/*
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load("RealClusterGraph.graphml").get(0);
        long end = System.currentTimeMillis();

        System.out.println("'JungYedHandler' done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        assertEquals(nodes, graph.getVertexCount());
        assertEquals(edges, graph.getEdgeCount());


        PolarDendrogramLayout layout = new PolarDendrogramLayout(graph);
        layout.setRadius(0.9);
*/
/*
        layout.setXCenter(0);
        layout.setYCenter(0);
        layout.setSize(new Dimension(800, 800));
*/
/*

        System.gc();

        start = System.currentTimeMillis();
        layout.initialize();
        end = System.currentTimeMillis();

        System.out.println("Initialize layout using Graph done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
*/
    }


    public void DagLayoutPerfomance() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load("RealGOGraph.graphml").get(0);
        long end = System.currentTimeMillis();

        System.out.println("Loading graph from file done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");


        KKLayout layout = new KKLayout(graph);
        layout.setSize(new Dimension(1280, 1024));

        System.gc();

        start = System.currentTimeMillis();
        while (!layout.done()) {
            layout.step();
        }
        end = System.currentTimeMillis();

        System.out.println("Computing layout done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
    }

    @Test
    public void dag() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        long start = System.currentTimeMillis();
        Graph graph = (Graph) parser.load("RealGOGraph.graphml").get(0);
        long end = System.currentTimeMillis();

        System.out.println("Loading graph from file done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        int leafs = 0;
        int nodes = 0;
        int roots = 0;

        for (Object nodeId : graph.getVertices()) {
            if (graph.outDegree(nodeId) == 0) {
                leafs++;
            } else {
                if (graph.inDegree(nodeId) == 0) {
                    roots++;
                } else {
                    nodes++;
                }
            }
        }

        Assert.assertEquals(leafs + roots + nodes, graph.getVertexCount());

        System.out.println("Vertex count:" + graph.getVertexCount());
        System.out.println("Edge count:" + graph.getEdgeCount());
        System.out.println("Leafs:" + leafs);
        System.out.println("Nodes:" + nodes);
        System.out.println("Roots:" + roots);


        System.out.println("Computing layout done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");
    }


}
