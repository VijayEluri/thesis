package se.lnu.thesis.paint;

import se.lnu.thesis.Scene;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.layout.HierarchyLayout;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.state.NormalGOState;
import se.lnu.thesis.paint.state.ZoomGOState;

import javax.swing.*;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 23:43:07
 */
public class GOController extends GraphController {

    private Element previewElement;
    private Element selectedElement;

    public GOController() {

    }

    public void init() {
        LOGGER.info("Initializing..");

        root = GOGraphContainer.init();

        try {
            HierarchyLayout layout = new HierarchyLayout(graph, root);
            layout.compute();

            Scene.getInstance().getMainWindow().setScrollBarMax(layout.getLevelCount());
        } catch (Exception e) {
            LOGGER.error("Initialization error!");
            LOGGER.error(e);

            JOptionPane.showMessageDialog(null, "Incorrect input graph structure!", "Layout computation error", JOptionPane.ERROR_MESSAGE);

            root = null;
            return;
        }

        setState(new NormalGOState(this));

        LOGGER.info("Done.");
    }

    /**
     * Gene Ontology  vertex been selected in the Gene List and corresponded subgraph computed.
     *
     * @param subject Server of the even
     * @param params  Instance of class the Extractor
     */
    public void notifyObserver(Subject subject, Object params) {
        Extractor extractor = Scene.getInstance().getExtractor();

        this.setSubGraph(extractor.getGoSubGraph());

        if (extractor.getSelectedNode() != null) {
            select(extractor.getSelectedNode());
        } else {
            unselect();
        }

        Scene.getInstance().getMainWindow().repaint();
    }

    protected void select(Object object) {
        unselect();

        for (Iterator<Element> i = this.root.getElements(); i.hasNext();) {
            Level level = (Level) i.next();

            Element element = level.getElementByObject(object);
            if (element != null) {
                selectedElement = element;
                selectedElement.setSelected(true);

                element = level.getPreview().getElementByObject(object);
                previewElement = element;
                previewElement.setSelected(true);

                return;
            }
        }
    }

    public void select(Element element) {
        unselect();

        for (Iterator<Element> i = this.root.getElements(); i.hasNext();) {
            Level level = (Level) i.next();

            Element foundedPreviewElement = level.getPreview().getElementByObject(element.getObject());
            if (foundedPreviewElement != null) {
                selectedElement = element;
                selectedElement.setSelected(true);

                previewElement = foundedPreviewElement;
                previewElement.setSelected(true);

                return;
            }
        }
    }

    public void unselect() {
        if (selectedElement != null && previewElement != null) {
            this.selectedElement.setSelected(false);
            this.previewElement.setSelected(false);
        }

        selectedElement = null;
        previewElement = null;
    }

    public void scrollBarValueChanged(int index) {
        if (getState() instanceof ZoomGOState) {  // only ZoomGOState traces this event
            ((ZoomGOState) getState()).scrollBarValueChanged(index);
        }
    }

}
