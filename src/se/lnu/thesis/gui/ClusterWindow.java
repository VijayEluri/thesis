package se.lnu.thesis.gui;

import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.myobserver.Subject;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.05.2010
 * Time: 15:31:52
 */
public class ClusterWindow extends JoglWindow {
    public void notifyObserver(Subject subject, Object params) {
        Extractor extractor = (Extractor) params;

        visualizer.setSubGraph(extractor.getClusterSubGraph());
        visualizer.setSelectedNode(extractor.getSelectedNode());
        visualizer.drawSubgraph();

        repaint();
    }
}
