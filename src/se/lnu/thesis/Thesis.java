package se.lnu.thesis;

import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.gui.SelectionDialog;
import se.lnu.thesis.io.AbstractHandler;
import se.lnu.thesis.io.GraphMLParser;
import se.lnu.thesis.io.MyGraphYedHandler;

import java.util.concurrent.TimeUnit;

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

    private static Thesis instance = new Thesis();

    public static Thesis getInstance() {
        return instance;
    }

    public static final String GO_APPLET_CLASS = "se.lnu.thesis.gui.GOApplet";
    public static final String CLUSTER_APPLET_CLASS = "se.lnu.thesis.gui.ClusterApplet";

    private Graph clusterGraph;
    private Graph goGraph;

    private SelectionDialog selectionDialog;

    private Thesis() {

    }

    public void init(String[] args) {
        if (args.length == 2) {
            initSelectionDialog(args[0], new MyGraphYedHandler());

            initGOApplet();
            initClusterApplet(args[1], new MyGraphYedHandler());

        } else {
            System.out.println("Error starting program.");
            System.out.println("java se.lnu.thesis.Thesis <<gene_ontology_file_path>> <<cluster_file_path>>");
            System.out.println("<<gene_ontology_file_path>> - path to graphml gene ontology file");
            System.out.println("<<cluster_file_path>> - path to graphml cluster file");
        }
    }

    protected void initGOApplet() {
        PApplet.main(new String[]{GO_APPLET_CLASS});
    }

    protected void initClusterApplet(String graphFilePath, AbstractHandler handler) {
        clusterGraph = loadGraph(graphFilePath, handler);
        PApplet.main(new String[]{CLUSTER_APPLET_CLASS});
    }

    private void initSelectionDialog(String goGraphFilePath, AbstractHandler handler) {
        goGraph = loadGraph(goGraphFilePath, handler);

        selectionDialog = new SelectionDialog();
        selectionDialog.initListContent(((MyGraph) getGOGraph()).getNodeLabel());
        selectionDialog.setVisible(true);
    }

    protected Graph loadGraph(String path, AbstractHandler handler) {
        Graph result = null;

        long start = System.currentTimeMillis();
        result = (Graph) new GraphMLParser(handler).load(path).get(0);
        long end = System.currentTimeMillis();

        System.out.println("Loading graph from file '" + path + "'.. Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s"); // TODO use logger

        return result;
    }

    public Graph getClusterGraph() {
        return this.clusterGraph;
    }

    public Graph getGOGraph() {
        return this.goGraph;
    }

    public SelectionDialog getSelectionDialog() {
        return selectionDialog;
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
