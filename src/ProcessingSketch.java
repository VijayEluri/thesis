import edu.uci.ics.jung.graph.Graph;
import io.GraphMLParser;
import io.JungYedHandler;
import layout.RadialLayout;
import processing.core.PApplet;

import java.awt.geom.Point2D;
import java.io.File;

public class ProcessingSketch extends PApplet {

    private static final String TEST_GRAPH = "tree23.graphml";
    private static final String REAL_GRAPH = "RealClusterGraph.graphml";
    private static final String CLUSTER_GRAPH = "cluster.graphml";

    private RadialLayout layout;

    public static void main(String[] args) {
        PApplet.main(new String[]{"--present", "ProcessingSketch"});
    }

    public void setup() {

        size(800, 800);
        background(0);
        stroke(255);

        layout = new RadialLayout(loadGraph());
        layout.setSize(getSize());
        layout.initialize();
    }

    private Graph loadGraph() {
        GraphMLParser parser = new GraphMLParser(new JungYedHandler());

        return (Graph) parser.load(new File(CLUSTER_GRAPH)).get(0);
    }

    public void draw() {

        fill(255);

        drawNodes();

        drawEdges();

    }

    private void drawEdges() {
        Graph graph = layout.getGraph();

        for (Object edge : graph.getEdges()) {
            Point2D start = layout.transform(graph.getSource(edge));
            Point2D end = layout.transform(graph.getDest(edge));

            line(new Float(start.getX()),
                    new Float(start.getY()),
                    new Float(end.getX()),
                    new Float(end.getY()));
        }
    }

    private void drawNodes() {
        for (Object node : layout.getGraph().getVertices()) {
            Point2D nodePosition = layout.transform(node);

            Float x = new Float(nodePosition.getX());
            Float y = new Float(nodePosition.getY());

/*
            pushMatrix();
            rotate(radians(45.0f));
            textFont(loadFont("Arial"));
            text((String) node, x+6, y);
            popMatrix();
*/


            ellipse(x, y, 5, 5);
        }
    }
}
