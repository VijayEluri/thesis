import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import io.GraphMLParser;
import io.JungTreeYedHandler;
import layout.PolarDendrogramLayout;
import processing.core.PApplet;

import java.awt.geom.Point2D;
import java.io.File;

public class ThesisApplet extends PApplet {

    static final String TEST_GRAPH = "tree23.graphml";
    static final String REAL_GRAPH = "RealClusterGraph.graphml";
    static final String CLUSTER_GRAPH = "cluster.graphml";
    static final String PART_TREE_GRAPH = "partTree.graphml";

    private static final int NODE_SIZE = 7;

    private AbstractLayout layout;

    private Graph goGraph;
    private Graph clusterGraph;


    public void setup() {
        size(800, 800);
        background(0);


        clusterGraph = loadClusterGraph(CLUSTER_GRAPH, new JungTreeYedHandler());


        layout = createLayout();

        layout.setSize(getSize());
        layout.initialize();
    }

    protected AbstractLayout createLayout() {
        return new PolarDendrogramLayout(clusterGraph);
    }

    private Graph loadClusterGraph(String path, JungTreeYedHandler handler) {
        return (Graph) new GraphMLParser(handler).load(new File(path)).get(0);
    }

    public void draw() {

        drawGraphElements();

        //  drawLavels();

        //save("picture.PNG");
    }

    private void drawLavels() {
        noFill();
        stroke(255);

        int levels = ((PolarDendrogramLayout) layout).getGraphHeight();

        double d = (((PolarDendrogramLayout) layout).getRadius() / levels) * 2;

        for (int i = 1; i <= levels; i++) {
            Float length = new Float(d * i);

            ellipse(getWidth() / 2, getHeight() / 2, length, length);
        }

    }

    private void drawGraphElements() {
        Graph graph = layout.getGraph();

        for (Object edge : graph.getEdges()) {

            Point2D start = layout.transform(graph.getSource(edge));
            Point2D end = layout.transform(graph.getDest(edge));

            fill(255);
            stroke(255);

            // TODO think how to optimize drawing: dont draw some nodes twise..
            // draw start node
            drawNode(start);


            if (layout.getGraph().outDegree(graph.getDest(edge)) == 0) {
                stroke(255, 0, 0);
                fill(255, 0, 0);
            }

            drawNode(end);


            // draw line edge
            stroke(255);
            drawEdge(start, end);
        }
    }

    private void drawNode(Point2D position) {
        ellipse(new Float(position.getX()), new Float(position.getY()), NODE_SIZE, NODE_SIZE);
    }

    private void drawEdge(Point2D start, Point2D end) {


        line(new Float(start.getX()),
                new Float(start.getY()),
                new Float(end.getX()),
                new Float(end.getY()));
    }

}
