package se.lnu.thesis.paint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.gui.MainWindow;
import se.lnu.thesis.layout.HierarchyLayout;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.state.NormalGOState;
import se.lnu.thesis.paint.state.ZoomGOState;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 23:43:07
 */
@Component
public class GOController extends GraphController {

    private Element previewElement;
    private Element selectedElement;

    private HierarchyLayout graphLayout;

    @Autowired
    private MainWindow mainWindow;

    @Autowired
    private Extractor extractor;

    public GOController() {
        setGraphLayout(new HierarchyLayout());
    }

    public void init() {
        LOGGER.info("Initializing..");

        root = GOGraphContainer.init();

        try {
            if (getGraphLayout() == null) {
                LOGGER.error("Graph layout is not set!");
                return;
            }

            getGraphLayout().setGraph(graph);
            getGraphLayout().setRoot(root);
            getGraphLayout().compute();

            mainWindow.setScrollBarMax(getGraphLayout().getLevelCount());
        } catch (Exception e) {
            LOGGER.error("Initialization error!");
            LOGGER.error(e);

            e.printStackTrace();

            JOptionPane.showMessageDialog(null, "Incorrect input graph structure!", "Layout computation error", JOptionPane.ERROR_MESSAGE);

            root = null;
            return;
        }

        setState(new NormalGOState(this));

        LOGGER.info("Done.");
    }


    /**
     * Set up graph controller into "normal" state:
     * no focused elements,
     * no selections,
     * no lens showed for cluster,
     * not zooming levels for GO, etc.
     */
    @Override
    public void setNormalState() {
        setState(new NormalGOState(this));
    }

    public void reinit() {

    }

    /**
     * Gene Ontology  vertex been selected in the Gene List and corresponded subgraph computed.
     *
     * @param subject Server of the even
     * @param params  Instance of class the Extractor
     */
    public void notifyObserver(Subject subject, Object params) {
        this.setSubGraph(extractor.getGoSubGraph());

        if (extractor.getSelectedNode() != null) {
            select(extractor.getSelectedNode());
        } else {
            unselect();
        }

        mainWindow.repaint();
    }

    protected void select(Object object) {
        unselect();

        for (Element e : this.root) {
            Level level = (Level) e;

            Element element = level.getElementByObject(object);
            if (element != null) {
                selectedElement = element;
                selectedElement.setSelected(true);

                element = level.getPreview().getElementByObject(object);
                previewElement = element;
                previewElement.setSelected(true);

                updateGOStatusbar();

                return;
            }
        }
    }

    public void select(Element element) {
        unselect();

        for (Element e : this.root) {
            Level level = (Level) e;

            Element foundedPreviewElement = level.getPreview().getElementByObject(element.getObject());
            if (foundedPreviewElement != null) {
                selectedElement = element;
                selectedElement.setSelected(true);

                previewElement = foundedPreviewElement;
                previewElement.setSelected(true);

                updateGOStatusbar();

                return;
            }
        }
    }

    /**
     * Set GO status bar message: is vertex been selected - about selected node, if unselected - clear status bar
     */
    public void updateGOStatusbar() {
        if (selectedElement != null) {
            String label = getGraph().getLabel(selectedElement.getObject());
            mainWindow.setGOStatusBarText("GO: selected vertex " + label);
        } else {
            mainWindow.setGOStatusBarText("");
        }
    }

    public void unselect() {
        if (selectedElement != null && previewElement != null) {
            this.selectedElement.setSelected(false);
            this.previewElement.setSelected(false);
        }

        selectedElement = null;
        previewElement = null;

        updateGOStatusbar();
    }

    public void scrollBarValueChanged(int index) {
        if (getState() instanceof ZoomGOState) {  // only ZoomGOState traces this event
            ((ZoomGOState) getState()).scrollBarValueChanged(index);
        }
    }

    public HierarchyLayout getGraphLayout() {
        return graphLayout;
    }

    public void setGraphLayout(HierarchyLayout graphLayout) {
        this.graphLayout = graphLayout;
    }
}
