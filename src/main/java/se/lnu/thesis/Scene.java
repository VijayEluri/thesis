package se.lnu.thesis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.gui.GeneListDialog;
import se.lnu.thesis.gui.MainWindow;
import se.lnu.thesis.gui.properties.ColorPropertiesDialog;
import se.lnu.thesis.paint.controller.ClusterController;
import se.lnu.thesis.paint.controller.GOController;
import se.lnu.thesis.paint.controller.GraphController;
import se.lnu.thesis.paint.lens.Lens;
import se.lnu.thesis.paint.lens.RadialLens;
import se.lnu.thesis.paint.lens.RectLens;
import se.lnu.thesis.utils.GraphUtils;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.07.2010
 * Time: 16:41:49
 */
@Service
public class Scene {

    public static final Logger LOGGER = Logger.getLogger(Scene.class);

    @Autowired
    private MainWindow mainWindow;

    @Autowired
    private GeneListDialog geneListDialog;

    @Autowired
    private ColorPropertiesDialog colorPropertiesDialog;

    private MyGraph goGraph = null;
    private MyGraph clusterGraph = null;

    @Autowired
    private GOController goController;
    @Autowired
    private ClusterController clusterController;

    @Autowired
    private Extractor extractor;

    private Lens lens;


    private Scene() {

    }

    @PostConstruct
    public void initUI() {
        geneListDialog.registerObserver(goController);
        geneListDialog.registerObserver(clusterController);

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
     * Turn off showing sub-graph in both graphs.
     */
    public void resetSubgraphHighlighting() {
        getGoController().setSubGraph(null);
        getClusterController().setSubGraph(null);
    }

    /**
     * Set new lens instance to draw grouping elements for cluster graph.
     * If same class as was then do nothing.
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

    // TODO provide separate iterator for vertices over GO graph container.
    public void hideUnconnectedComponents() {
        GOGraphContainer goGraphContainer = (GOGraphContainer) getGoController().getRoot();

        for (Iterator<Element> i = goGraphContainer.getLevels(); i.hasNext(); ) {
            Level level = (Level) i.next();

            for (Object o : level.getObjects()) {
                if (GraphUtils.isUnconnectedComponent(getGoGraph(), o)) {
                    level.getElementByObject(o).setDrawn(false);
                    level.getPreview().getElementByObject(o).setDrawn(false);
                }
            }
        }
    }

    public void showUnconnectedComponents() { // TODO provide separate iterator for vertices over GO graph container.
        GOGraphContainer goGraphContainer = (GOGraphContainer) getGoController().getRoot();

        for (Iterator<Element> i = goGraphContainer.getLevels(); i.hasNext(); ) {
            Level level = (Level) i.next();

            for (Object o : level.getObjects()) {
                if (GraphUtils.isUnconnectedComponent(getGoGraph(), o)) {
                    level.getElementByObject(o).setDrawn(true);
                    level.getPreview().getElementByObject(o).setDrawn(true);
                }
            }
        }
    }
}
