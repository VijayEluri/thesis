package se.lnu.thesis.paint.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.element.ClusterGraphContainer;
import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.gui.MainWindow;
import se.lnu.thesis.layout.RectangularSpiralLayout;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.state.LensState;
import se.lnu.thesis.paint.state.NormalClusterState;

import javax.swing.*;

@Component
public class ClusterController extends GraphController implements Observer {

    @Autowired
    private Extractor extractor;

    @Autowired
    private MainWindow mainWindow;

    public void init() {
        root = ClusterGraphContainer.init();

        try {
            RectangularSpiralLayout layout = new RectangularSpiralLayout(graph, root);
            layout.compute();
        } catch (Exception e) {
            LOGGER.error("Initialization error!");
            LOGGER.error(e);

            JOptionPane.showMessageDialog(null, "Incorrect input graph structure!", "Layout computation error", JOptionPane.ERROR_MESSAGE);

            root = null;
            return;
        }

        setNormalState();
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
        setState(new NormalClusterState(this));
    }

    public void notifyObserver(Subject subject, Object params) {
        this.setSubGraph(extractor.getClusterSubGraph());

        mainWindow.repaint();
    }

    /**
     * Clear all computed layout inside GroupElement.
     * This method is used when changing lens instance to recompute inner elements with another layout.
     */
    public void clearGroupingElementElements() {
        if (getState() instanceof LensState) {
            ((LensState) getState()).unselect();
            ((LensState) getState()).unfocus();
        }


        if (getRoot() != null) {
            for (Element element : getRoot()) {
                if (element instanceof GroupingElement) {
                    ((GroupingElement) element).clearElements();
                }
            }
        }
    }

}
