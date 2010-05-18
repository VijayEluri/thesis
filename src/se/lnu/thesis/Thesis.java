package se.lnu.thesis;

import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.gui.ClusterWindow;
import se.lnu.thesis.gui.GoWindow;
import se.lnu.thesis.gui.JoglWindow;
import se.lnu.thesis.gui.SelectionDialog;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.layout.RadialLayout;
import se.lnu.thesis.layout.SpiralLayout;
import se.lnu.thesis.paint.AbstractGraphElementVisualizer;
import se.lnu.thesis.paint.ClusterGraphVisualizer;
import se.lnu.thesis.paint.GOGraphVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;
import se.lnu.thesis.paint.edge.LineEdgeVisualizer;
import se.lnu.thesis.paint.vertex.CircleVertexVisualizer;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.03.2010
 * Time: 18:22:20
 * <p/>
 * <p/>
 * Program entry point!
 * <p/>
 * java se.lnu.thesis.Thesis [processing params] start <<cluster_graphml_file_path>>
 */
public class Thesis {

    public static final Logger LOGGER = Logger.getLogger(Thesis.class);

    private static Thesis instance = new Thesis();

    public static Thesis getInstance() {
        return instance;
    }

    private MyGraph clusterGraph;
    private MyGraph goGraph;

    private JoglWindow goWindow;
    private JoglWindow clusterWindow;
    private SelectionDialog selectionDialog;

    private Thesis() {

    }

    public void init(String[] args) {
        if (args.length == 2) {
            IOFacade ioFacade = new IOFacade();

            goGraph = ioFacade.loadFromYedGraphml(args[0]);
            clusterGraph = ioFacade.loadFromYedGraphml(args[1]);

            initSelectionDialog();
            initGOWindow();
            initClusterWindow();

        } else {
            System.out.println("Error starting program.");
            System.out.println("java se.lnu.thesis.Thesis <<gene_ontology_file_path>> <<cluster_file_path>>");
            System.out.println("<<gene_ontology_file_path>> - path to graphml gene ontology file");
            System.out.println("<<cluster_file_path>> - path to graphml cluster file");
        }
    }

    protected void initGOWindow() {
        goWindow = new GoWindow();

        GraphWithSubgraphVisualizer visualizer = new GOGraphVisualizer();

        visualizer.setGraph(goGraph);

        RadialLayout layout = new RadialLayout(goGraph);
        layout.initialize();
        visualizer.setLayout(layout);

        visualizer.setEdgeVisualizer(new LineEdgeVisualizer(visualizer, Color.WHITE));
        visualizer.setSubGraphEdgeVizualizer(new LineEdgeVisualizer(visualizer, Color.YELLOW));

        AbstractGraphElementVisualizer vertexVisualizer = new CircleVertexVisualizer(visualizer, Color.RED);
        visualizer.setVertexVisualizer(vertexVisualizer);
        visualizer.setSubGraphVertexVizualizer(vertexVisualizer);

        goWindow.setVisualizer(visualizer);

        selectionDialog.registerObserver(goWindow);

        goWindow.setVisible(false); // TODO for testing only
    }

    protected void initClusterWindow() {
        clusterWindow = new ClusterWindow();

        GraphWithSubgraphVisualizer visualizer = new ClusterGraphVisualizer();

        visualizer.setGraph(clusterGraph);

/*
        PolarDendrogramLayout layout = new PolarDendrogramLayout(clusterGraph);
        layout.setCenter(0, 0);
        layout.setRadius(0.9);
        layout.initialize();
*/
        SpiralLayout layout = new SpiralLayout(clusterGraph);
        layout.initialize();

        visualizer.setLayout(layout);

/*
        visualizer.setEdgeVisualizer(new PolarDendrogramEdgeVisualizer(visualizer, Color.WHITE));
        visualizer.setSubGraphEdgeVizualizer(new PolarDendrogramEdgeVisualizer(visualizer, Color.YELLOW));
*/
        visualizer.setEdgeVisualizer(new LineEdgeVisualizer(visualizer, Color.WHITE));
        visualizer.setSubGraphEdgeVizualizer(new LineEdgeVisualizer(visualizer, Color.YELLOW));

        AbstractGraphElementVisualizer vertexVisualizer = new CircleVertexVisualizer(visualizer, Color.RED);
        visualizer.setVertexVisualizer(vertexVisualizer);
        visualizer.setSubGraphVertexVizualizer(vertexVisualizer);

        clusterWindow.setVisualizer(visualizer);

        selectionDialog.registerObserver(clusterWindow);

        clusterWindow.setVisible(true);

    }

    protected void initSelectionDialog() {
        selectionDialog = new SelectionDialog();
        selectionDialog.initListContent(getGOGraph().getNodeLabel());
        selectionDialog.setVisible(true);
    }

    public MyGraph getClusterGraph() {
        return this.clusterGraph;
    }

    public MyGraph getGOGraph() {
        return this.goGraph;
    }


    /**
     * Program entry point.
     * Two params: file path to Gene Ontology file and Cluster graph file
     *
     * @param args GO and Cluster file
     */
    public static void main(String[] args) {
        Thesis.getInstance().init(args);
    }
}
