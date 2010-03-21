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
import se.lnu.thesis.paint.edge.SelectedPolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.node.CircleNodeVisualizer;
import se.lnu.thesis.paint.node.HighLightedNodeVisualizer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Map;

public class ThesisApplet extends PApplet {


    private static final float ZOOM_STEP = 0.05f;
    private static final float MOVE_STEP = 5.0f;

    private Visualizer clusterGraphVisualizer;

    private Graph clusterGraph;
    private Graph goGraph;

    private float zoom = 1.0f;
    private float sceneX = 0.0f;
    private float sceneY = 0.0f;

    @Override
    public void setup() {
        size(800, 800);

        System.out.println("Starting initialization..."); // TODO use logger
        long start = System.currentTimeMillis();

        initClusterVisualizer();

        long end = System.currentTimeMillis();
        double time = (end - start) / 1000;

        System.out.println("Done in " + time + " seconds."); // TODO use logger

        noLoop(); // TODO delete it?
    }

    private Graph loadGraph(File file, AbstractHandler handler) {
        return (Graph) new GraphMLParser(handler).load(file).get(0);
    }

    private Map<Object, String> loadClusterGraph() {
        JungYedHandler yedHandler = new JungYedHandler();

        try {
            clusterGraph = loadGraph(new File(args[0]), yedHandler); // TODO use FileChooser to pick cluster graph file
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No cluster graph file.");
        }

        return yedHandler.getIdLabel();
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

    private void initClusterVisualizer() {
        clusterGraphVisualizer = new Visualizer(this);
        clusterGraphVisualizer.setGraphIdLabels(loadClusterGraph());
        clusterGraphVisualizer.setGraph(clusterGraph);
        clusterGraphVisualizer.setLayout(initPolarDendrogramLayout());
        clusterGraphVisualizer.setEdgeVisualizer(new PolarDendrogramEdgeVisualizer(clusterGraphVisualizer));
        clusterGraphVisualizer.setHighLightedEdgeVisualizer(new SelectedPolarDendrogramEdgeVisualizer(clusterGraphVisualizer));
        clusterGraphVisualizer.setNodeVisualizer(new CircleNodeVisualizer(clusterGraphVisualizer));
        clusterGraphVisualizer.setHighLightedNodeVisualizer(new HighLightedNodeVisualizer(clusterGraphVisualizer));
    }

    @Override
    public void draw() {
        pushMatrix();

        scale(zoom);

        translate(sceneX, sceneY);

        clusterGraphVisualizer.draw();

        popMatrix();
        //  drawLavels();
    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (key == CODED) {
            System.out.println(keyCode);// TODO use logger

            if (keyCode == ALT) {
                zoom -= ZOOM_STEP;
            }
            if (keyCode == SHIFT) {
                zoom += ZOOM_STEP;
            }

            if (keyCode == UP) {
                sceneY += MOVE_STEP;
            }
            if (keyCode == DOWN) {
                sceneY -= MOVE_STEP;
            }

            if (keyCode == LEFT) {
                sceneX -= MOVE_STEP;
            }
            if (keyCode == RIGHT) {
                sceneX += MOVE_STEP;
            }


            redraw();
        } else {

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