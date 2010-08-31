package se.lnu.thesis;

import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.gui.ClusterWindow;
import se.lnu.thesis.gui.GoWindow;
import se.lnu.thesis.gui.JoglWindow;
import se.lnu.thesis.gui.SelectionDialog;
import se.lnu.thesis.io.IOFacade;
import se.lnu.thesis.paint.ClusterVisualizer;
import se.lnu.thesis.paint.GOVisualizer;
import se.lnu.thesis.paint.GraphVisualizer;

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

        GOVisualizer visualizer = new GOVisualizer();
        visualizer.setGraph(goGraph);
        visualizer.init();


        goWindow = new GoWindow();
        goWindow.setVisualizer(visualizer);

        selectionDialog.registerObserver(goWindow);

        goWindow.setVisible(true);
    }

    protected void initClusterWindow() {

        GraphVisualizer visualizer = new ClusterVisualizer();
        visualizer.setGraph(clusterGraph);
        visualizer.init();

        clusterWindow = new ClusterWindow();
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
