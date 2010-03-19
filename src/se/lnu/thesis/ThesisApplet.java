package se.lnu.thesis;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;
import se.lnu.thesis.io.AbstractHandler;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.JungYedHandler;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;
import java.io.File;

public class ThesisApplet extends PApplet {

    static final String TEST_GRAPH = "tree23.graphml";
    static final String REAL_GRAPH = "RealClusterGraph.graphml";
    static final String CLUSTER_GRAPH = "cluster.graphml";
    static final String PART_TREE_GRAPH = "partTree.graphml";
    static final String BIG_GRAPH = "bigGraph.graphml";

    private static final int NODE_SIZE = 7;

    private AbstractLayout layout;

    private Graph goGraph;
    private Graph clusterGraph;


    public void setup() {

        smooth();
        size(800, 800);
        background(0);


        clusterGraph = loadGraph(new File(CLUSTER_GRAPH), new JungYedHandler());


        layout = initLayout();
    }

    protected AbstractLayout initLayout() {
        PolarDendrogramLayout result = new PolarDendrogramLayout(clusterGraph);

        double height = getSize().getHeight();
        double width = getSize().getWidth();

        result.setRadius(0.45 * (height < width ? height : width));
        result.setXCenter((float) (height / 2));
        result.setYCenter((float) (width / 2));

        result.setSize(getSize());
        result.initialize();

/*
        FullSizePolarDendrogramLayout result = new FullSizePolarDendrogramLayout(clusterGraph);

        result.setSize(getSize());
        result.initialize();
*/

        return result;
    }


    private Graph loadGraph(File file, AbstractHandler handler) {
        return (Graph) new GraphMLParser(handler).load(file).get(0);
    }

    public void draw() {

        drawGraphElements();

        //  drawLavels();
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

            // draw line edge
            stroke(255);
            //  drawEdge(start, end);
            drawDendrogramEdge(start, end, edge);


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

    private void drawDendrogramEdge(Point2D start, Point2D end, Object edge) {

        PolarDendrogramLayout polarDendrogramLayout = (PolarDendrogramLayout) layout;

        noFill();

        Object source = layout.getGraph().getSource(edge);
        Object dest = layout.getGraph().getDest(edge);

        Double sourceAngle = (Double) polarDendrogramLayout.getNode_angle().get(source);
        Double destAngle = (Double) polarDendrogramLayout.getNode_angle().get(dest);

        // if (sourceAngle.compareTo(destAngle) == 0) {

        //    line(new Float(start.getX()),
        //       new Float(start.getY()),
        //           new Float(end.getX()),
        //             new Float(end.getY()));

        //      } else {

        Point2D dummyNode = polarDendrogramLayout.getDummyNode(source, dest);

        line(new Float(dummyNode.getX()),
                new Float(dummyNode.getY()),
                new Float(end.getX()),
                new Float(end.getY()));


        float radius = new Float(polarDendrogramLayout.getNodeRadius(source)) * 2;

        double startAngle = Utils.min(sourceAngle, destAngle);
        double endAngle = Utils.max(sourceAngle, destAngle);

        arc(polarDendrogramLayout.getXCenter(),
                polarDendrogramLayout.getYCenter(),
                radius,
                radius,
                new Float(Utils.inRadians(startAngle)),
                new Float(Utils.inRadians(endAngle)));

//        }

        /*     stroke(0,255,0);
                line(new Float(start.getX()),
                        new Float(start.getY()),
                        new Float(end.getX()),
                        new Float(end.getY()));
        */

    }


}