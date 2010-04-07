package se.lnu.thesis;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;
import se.lnu.thesis.io.AbstractHandler;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.JungTreeYedHandler;
import se.lnu.thesis.io.JungYedHandler;
import se.lnu.thesis.layout.AbstractPolarDendrogramLayout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.Visualizer;
import se.lnu.thesis.paint.edge.PolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.node.CircleNodeVisualizer;

import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class ThesisApplet extends PApplet {


    private Visualizer clusterGraphVisualizer;

    private Graph clusterGraph;
    private Graph goGraph;

    @Override
    public void setup() {
        size(800, 800);

        System.out.println("Starting initialization..."); // TODO use logger

        long start = System.currentTimeMillis();

        clusterGraphVisualizer = new Visualizer(this);
        clusterGraphVisualizer.setGraph(loadClusterGraph(new JungYedHandler()));
        //clusterGraphVisualizer.setGraph(loadClusterGraph(new JungTreeYedHandler()));
        clusterGraphVisualizer.setLayout(initPolarDendrogramLayout());
        clusterGraphVisualizer.setEdgeVisualizer(new PolarDendrogramEdgeVisualizer(clusterGraphVisualizer));
        clusterGraphVisualizer.setNodeVisualizer(new CircleNodeVisualizer(clusterGraphVisualizer));

        long end = System.currentTimeMillis();

        long duration = TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS);

        System.out.println("Done in " + duration + " seconds."); // TODO use logger
    }

    private Graph loadClusterGraph(AbstractHandler handler) {

        if (args.length == 0) {
            throw new IllegalArgumentException("No cluster graph file.");
        }

        long start = System.currentTimeMillis();
        clusterGraph = loadGraph(args[0], new JungTreeYedHandler());
        long end = System.currentTimeMillis();

        System.out.println("Loading graph done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        return clusterGraph;
    }

    private Graph loadGraph(String path, AbstractHandler handler) {
        return (Graph) new GraphMLParser(handler).load(path).get(0);
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

    private void drawLavels() {
        noFill();
        stroke(255);

        int levels = ((PolarDendrogramLayout) clusterGraphVisualizer.getLayout()).getGraphHeight();

        double d = (((PolarDendrogramLayout) clusterGraphVisualizer.getLayout()).getRadius() / levels) * 2;

        for (int i = 1; i <= levels; i++) {
            Float length = new Float(d * i);

            ellipse(getWidth() / 2, getHeight() / 2, length, length);
        }

    }


}