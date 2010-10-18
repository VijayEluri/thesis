package se.lnu.thesis;

import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.gui.LensDialog;
import se.lnu.thesis.gui.MainWindow;
import se.lnu.thesis.gui.SelectionDialog;
import se.lnu.thesis.paint.ClusterVisualizer;
import se.lnu.thesis.paint.GOVisualizer;
import se.lnu.thesis.paint.GraphVisualizer;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.07.2010
 * Time: 16:41:49
 */
public class Scene {

    public static final Logger LOGGER = Logger.getLogger(Scene.class);

    private static Scene instance = new Scene();

    public static Scene getInstance() {
        return instance;
    }

    private MainWindow mainWindow;
    private SelectionDialog selectionDialog;

    private LensDialog lensDialog;

    private MyGraph goGraph = null;
    private MyGraph clusterGraph = null;

    private GraphVisualizer goVisualizer;
    private GraphVisualizer clusterVisualizer;


    private Scene() {

    }

    public void initUI() {
        goVisualizer = new GOVisualizer();
        clusterVisualizer = new ClusterVisualizer();

        mainWindow = new MainWindow();

        selectionDialog = new SelectionDialog();
        selectionDialog.registerObserver(goVisualizer);
        selectionDialog.registerObserver(clusterVisualizer);

        //lensDialog = new LensDialog();
    }

    public void setGoGraph(MyGraph goGraph) {
        this.goGraph = goGraph;

        initVisualizer(goVisualizer, goGraph);

        selectionDialog.initListContent(Scene.getInstance().getGoGraph().getNodeLabel());
        selectionDialog.setVisible(true);
    }

    public void setClusterGraph(MyGraph clusterGraph) {
        this.clusterGraph = clusterGraph;

        initVisualizer(clusterVisualizer, clusterGraph);
    }

    protected void initVisualizer(GraphVisualizer graphVisualizer, MyGraph graph) {
        graphVisualizer.setGraph(graph);

        LOGGER.info("Initializing visualizer..");

        getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        graphVisualizer.init();
        getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        LOGGER.info("Done.");
    }

    public MyGraph getGoGraph() {
        return goGraph;
    }

    public MyGraph getClusterGraph() {
        return clusterGraph;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public LensDialog getLensDialog() {
        return lensDialog;
    }

    public GraphVisualizer getGoVisualizer() {
        return goVisualizer;
    }

    public GraphVisualizer getClusterVisualizer() {
        return clusterVisualizer;
    }

}
