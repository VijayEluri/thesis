import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import io.GraphMLParser;
import io.JungTreeYedHandler;
import layout.RadialLayout;
import processing.core.PApplet;

import java.awt.geom.Point2D;
import java.io.File;

public class ProcessingSketch extends PApplet {

    private static final String TEST_GRAPH = "tree23.graphml";
    private static final String REAL_GRAPH = "RealClusterGraph.graphml";
    private static final String CLUSTER_GRAPH = "cluster.graphml";

    private static final int NODE_SIZE = 7;

    private AbstractLayout layout;


    public static void main(String[] args) {
        //PApplet.main(new String[]{"--present", "ProcessingSketch"}); --present - put the applet into full screen presentation mode. requires java 1.4 or later.
        PApplet.main(new String[]{"ProcessingSketch"});
    }


    public void setup() {
        size(800, 800);
        background(0);

        layout = createLayout();

        layout.setSize(getSize());
        layout.initialize();
    }

    protected AbstractLayout createLayout() {
        return new RadialLayout(loadGraph());
    }

    private Graph loadGraph() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

        return (Graph) parser.load(new File(CLUSTER_GRAPH)).get(0);
    }

    public void draw() {

        drawGraphElements();

    }

    private void drawGraphElements() {
        Graph graph = layout.getGraph();

        for (Object edge : graph.getEdges()) {

            fill(255);
            stroke(255);

            // draw start node
            Point2D start = layout.transform(graph.getSource(edge));
            ellipse(new Float(start.getX()), new Float(start.getY()), NODE_SIZE, NODE_SIZE);


            if (layout.getGraph().outDegree(graph.getDest(edge)) == 0) {
                stroke(255, 0, 0);
                fill(255, 0, 0);
            }

            // draw end node
            Point2D end = layout.transform(graph.getDest(edge));
            ellipse(new Float(end.getX()), new Float(end.getY()), NODE_SIZE, NODE_SIZE);


            // draw line edge
            stroke(255);

            line(new Float(start.getX()),
                    new Float(start.getY()),
                    new Float(end.getX()),
                    new Float(end.getY()));
        }
    }

}
