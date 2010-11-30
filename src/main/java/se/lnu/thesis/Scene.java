package se.lnu.thesis;

import org.apache.log4j.Logger;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.gui.GeneListDialog;
import se.lnu.thesis.gui.MainWindow;
import se.lnu.thesis.paint.ClusterController;
import se.lnu.thesis.paint.GOController;
import se.lnu.thesis.paint.GraphController;

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
    private GeneListDialog geneListDialog;

    private MyGraph goGraph = null;
    private MyGraph clusterGraph = null;

    private GOController goController;
    private GraphController clusterController;

    private Extractor extractor;


    private Scene() {

    }

    public void initUI() {
        goController = new GOController();
        clusterController = new ClusterController();

        mainWindow = new MainWindow();

        geneListDialog = new GeneListDialog();
        geneListDialog.registerObserver(goController);
        geneListDialog.registerObserver(clusterController);

        extractor = new Extractor();
    }

    public void setGoGraph(MyGraph goGraph) {
        this.goGraph = goGraph;

        initController(goController, goGraph);

        geneListDialog.setGraph(goGraph);
    }

    public void setClusterGraph(MyGraph clusterGraph) {
        this.clusterGraph = clusterGraph;

        initController(clusterController, clusterGraph);
    }

    protected void initController(GraphController graphController, MyGraph graph) {
        graphController.setGraph(graph);

        LOGGER.info("Initializing controller..");

        getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        graphController.init();
        getMainWindow().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        LOGGER.info("Done.");
    }

    public void showGeneList() {
        geneListDialog.setVisible(true);
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

    public GOController getGoController() {
        return goController;
    }

    public GraphController getClusterController() {
        return clusterController;
    }

    public Extractor getExtractor() {
        return extractor;
    }

    public GeneListDialog getGeneListDialog() {
        return geneListDialog;
    }
}
