import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import io.GraphMLParser;
import io.JungTreeYedHandler;
import layout.PolarDendrogram;
import processing.core.PApplet;

import java.awt.geom.Point2D;
import java.io.File;

public class ProcessingSketch extends PApplet {

    private static final String TEST_GRAPH = "tree23.graphml";
    private static final String REAL_GRAPH = "RealClusterGraph.graphml";
    private static final String CLUSTER_GRAPH = "cluster.graphml";

    private AbstractLayout layout;


    public static void main(String[] args) {
        //PApplet.main(new String[]{"--present", "ProcessingSketch"}); --present - put the applet into full screen presentation mode. requires java 1.4 or later.
        PApplet.main(new String[]{"ProcessingSketch"});
    }


    public void setup() {
        size(800, 800);
        background(0);
        stroke(255);

        layout = createLayout();

        layout.setSize(getSize());
        layout.initialize();
    }

    protected AbstractLayout createLayout() {
        return new PolarDendrogram(loadGraph());
    }

    private Graph loadGraph() {
        GraphMLParser parser = new GraphMLParser(new JungTreeYedHandler());

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

            ellipse(x, y, 5, 5);
        }
    }
}
