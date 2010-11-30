package se.lnu.thesis.paint;


import se.lnu.thesis.Scene;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.element.ClusterGraphContainer;
import se.lnu.thesis.layout.RectangularSpiralLayout;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.state.NormalClusterState;

import javax.swing.*;


public class ClusterController extends GraphController implements Observer {

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

        setState(new NormalClusterState(this));
    }

    public void notifyObserver(Subject subject, Object params) {
        Extractor extractor = Scene.getInstance().getExtractor();

        this.setSubGraph(extractor.getClusterSubGraph());

        Scene.getInstance().getMainWindow().repaint();
    }

}
