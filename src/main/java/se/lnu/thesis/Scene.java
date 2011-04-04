package se.lnu.thesis;

import org.apache.log4j.Logger;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.*;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.gui.GeneListDialog;
import se.lnu.thesis.gui.MainWindow;
import se.lnu.thesis.gui.properties.ColorPropertiesDialog;
import se.lnu.thesis.paint.controller.ClusterController;
import se.lnu.thesis.paint.controller.GOController;
import se.lnu.thesis.paint.controller.GraphController;
import se.lnu.thesis.paint.lens.Lens;
import se.lnu.thesis.paint.lens.RadialLens;
import se.lnu.thesis.paint.lens.RectLens;

import java.awt.*;
import java.util.Iterator;

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
    private ColorPropertiesDialog colorPropertiesDialog;

    private MyGraph goGraph = null;
    private MyGraph clusterGraph = null;

    private GOController goController;
    private ClusterController clusterController;

    private Extractor extractor;

    private Lens lens;


    private Scene() {

    }

    public void initUI() {
        goController = new GOController();
        clusterController = new ClusterController();

        mainWindow = new MainWindow();

        geneListDialog = new GeneListDialog();
        geneListDialog.registerObserver(goController);
        geneListDialog.registerObserver(clusterController);

        colorPropertiesDialog = new ColorPropertiesDialog();

        extractor = new Extractor();

        setLens(new RadialLens());
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

    public ClusterController getClusterController() {
        return clusterController;
    }

    public Extractor getExtractor() {
        return extractor;
    }

    public GeneListDialog getGeneListDialog() {
        return geneListDialog;
    }

    public ColorPropertiesDialog getColorPropertiesDialog() {
        return colorPropertiesDialog;
    }

    public Lens getLens() {
        return lens;
    }

    /**
     *
     *      Turn off showing sub-graph in both graphs.
     *
     */
    public void resetSubgraphHighlighting() {
        getGoController().setSubGraph(null);
        getClusterController().setSubGraph(null);
    }

    /**
     *
     *      Set new lens instance to draw grouping elements for cluster graph.
     *  If same class as was then do nothing.
     *
     * @param lens Implementation of <code>Lens</code> class.
     */
    protected void setLens(Lens lens) {
        this.lens = lens;

        resetSubgraphHighlighting();

        getGoController().unselect();

        getClusterController().clearGroupingElementElements();

        getClusterController().setNormalState();
    }

    public void setRadialLens() {
        setLens(new RadialLens());
    }

    public void setRectLens() {
        setLens(new RectLens());
    }
}
