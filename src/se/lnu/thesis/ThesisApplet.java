package se.lnu.thesis;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;
import se.lnu.thesis.io.AbstractHandler;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.JungYedHandler;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.Visualizer;
import se.lnu.thesis.paint.edge.PolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.node.CircleNodeVisualizer;

import java.awt.event.KeyEvent;
import java.io.File;
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

        loadClusterGraph();
        clusterGraphVisualizer = new Visualizer(this);
        clusterGraphVisualizer.setGraph(clusterGraph);
        clusterGraphVisualizer.setLayout(initPolarDendrogramLayout());
        clusterGraphVisualizer.setEdgeVisualizer(new PolarDendrogramEdgeVisualizer(clusterGraphVisualizer));
        clusterGraphVisualizer.setNodeVisualizer(new CircleNodeVisualizer(clusterGraphVisualizer));

        long end = System.currentTimeMillis();

        long duration = TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS);

        System.out.println("Done in " + duration + " seconds."); // TODO use logger

        //noLoop(); // TODO delete it?
    }

    private void loadClusterGraph() {

        try {
            clusterGraph = loadGraph(new File(args[0]), new JungYedHandler()); // TODO use FileChooser to pick cluster graph file
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No cluster graph file.");
        }

    }

    private Graph loadGraph(File file, AbstractHandler handler) {
        return (Graph) new GraphMLParser(handler).load(file).get(0);
    }

    protected AbstractLayout initPolarDendrogramLayout() {
        PolarDendrogramLayout result = new PolarDendrogramLayout(clusterGraph);

        double height = getSize().getHeight();
        double width = getSize().getWidth();

        result.setRadius(0.45 * (height < width ? height : width));

        result.setXCenter((float) (height / 2));
        result.setYCenter((float) (width / 2));

        result.setSize(getSize());


        result.initialize(); // compute nodes positions


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