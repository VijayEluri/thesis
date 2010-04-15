package se.lnu.thesis.gui;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.AbstractHandler;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.MyGraphYedHandler;
import se.lnu.thesis.layout.AbstractPolarDendrogramLayout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.ClusterGraphVisualizer;
import se.lnu.thesis.paint.edge.PolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.vertex.CircleVertexVisualizer;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

public class ThesisApplet extends PApplet implements Observer {


    private static final int DEFAULT_APPLET_HEGIHT = 800;
    private static final int DEFAULT_APPLET_WIDTH = 800;

    private SelectionDialog selectionDialog;

    private ClusterGraphVisualizer clusterGraphVisualizer;

    private MyGraph clusterGraph;
    private MyGraph goGraph;

    @Override
    public void setup() {
        size(DEFAULT_APPLET_HEGIHT, DEFAULT_APPLET_WIDTH);

        System.out.println("Starting initialization..."); // TODO use logger

        long start = System.currentTimeMillis();

        loadGoGraph();

        setupClusterVisualizer();


        long end = System.currentTimeMillis();
        long duration = TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS);

        System.out.println("Initializaton done in " + duration + " seconds."); // TODO use logger

        if (goGraph != null) {   // TODO move it separete method 
            selectionDialog = new SelectionDialog();
            selectionDialog.initListContent(goGraph.getNodeLabel());
            selectionDialog.setObserver(this);
            selectionDialog.setVisible(true);
        }
    }

    private void setupClusterVisualizer() {

        clusterGraphVisualizer = new ClusterGraphVisualizer(this);

        clusterGraph = (MyGraph) loadGraph(args[1], new MyGraphYedHandler());

        clusterGraphVisualizer.setGoGraph(goGraph);
        clusterGraphVisualizer.setGraph(clusterGraph);
        clusterGraphVisualizer.setLayout(initPolarDendrogramLayout());
        clusterGraphVisualizer.setEdgeVisualizer(new PolarDendrogramEdgeVisualizer(clusterGraphVisualizer));
        clusterGraphVisualizer.setVertexVisualizer(new CircleVertexVisualizer(clusterGraphVisualizer));
    }

    private Graph loadGoGraph() {
        goGraph = (MyGraph) loadGraph(args[0], new MyGraphYedHandler());

        return goGraph;
    }

    private Graph loadGraph(String path, AbstractHandler handler) {
        Graph graph = null;

        long start = System.currentTimeMillis();
        graph = (Graph) new GraphMLParser(handler).load(path).get(0);
        long end = System.currentTimeMillis();

        System.out.println("Loading graph from file '" + path + "'.. Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s"); // TODO use logger

        return graph;
    }

    protected AbstractLayout initPolarDendrogramLayout() {
        AbstractPolarDendrogramLayout result = new PolarDendrogramLayout(clusterGraph);
        //AbstractPolarDendrogramLayout result = new TreePolarDendrogramLayout((Tree) clusterGraph);

        double height = getSize().getHeight();
        double width = getSize().getWidth();

        result.setRadius(0.45 * (height < width ? height : width));

        result.setXCenter((float) (height / 2));
        result.setYCenter((float) (width / 2));

        result.setSize(getSize());

        long start = System.currentTimeMillis();
        result.initialize(); // compute nodes positions
        long end = System.currentTimeMillis();

        System.out.println("Layout computing done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s"); // TODO use logger

        return result;
    }

    @Override
    public void draw() {
        clusterGraphVisualizer.draw();
    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (key == CODED) {
            System.out.println(keyCode);// TODO use logger

            if (keyCode == ALT) {
                clusterGraphVisualizer.zoomOut();
            }
            if (keyCode == SHIFT) {
                clusterGraphVisualizer.zoomIn();
            }

            if (keyCode == UP) {
                clusterGraphVisualizer.down();
            }
            if (keyCode == DOWN) {
                clusterGraphVisualizer.up();
            }

            if (keyCode == LEFT) {
                clusterGraphVisualizer.left();
            }
            if (keyCode == RIGHT) {
                clusterGraphVisualizer.right();
            }


            redraw();
        } else {
            System.out.println("No key code!");// TODO use logger
        }

    }

    public void update(Observable observable, Object o) {
        // some vertex selected in the list
        clusterGraphVisualizer.setSelectedGoNode(selectionDialog.getSelectedNode());
        redraw();
    }
}