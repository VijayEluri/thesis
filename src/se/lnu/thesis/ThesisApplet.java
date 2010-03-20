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

import java.io.File;

public class ThesisApplet extends PApplet {


    private Visualizer clusterGraphVisualizer;

    private Graph clusterGraph;
    private Graph goGraph;


    public void setup() {

        System.out.println("Starting initialization..."); // TODO use logger

        smooth();
        size(800, 800);
        background(0);

        loadClusterGraph();
        clusterGraphVisualizer = new Visualizer(this);
        clusterGraphVisualizer.setGraph(clusterGraph);
        clusterGraphVisualizer.setLayout(initPolarDendrogramLayout());
        clusterGraphVisualizer.setEdgeVisualizer(new PolarDendrogramEdgeVisualizer(clusterGraphVisualizer));
        clusterGraphVisualizer.setNodeVisualizer(new CircleNodeVisualizer(clusterGraphVisualizer));

        System.out.println("Done."); // TODO use logger

        noLoop(); // TODO delete it?
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


    public void draw() {

        clusterGraphVisualizer.draw();

        //  drawLavels();
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