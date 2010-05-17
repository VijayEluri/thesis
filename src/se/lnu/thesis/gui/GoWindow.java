package se.lnu.thesis.gui;

import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 07.05.2010
 * Time: 1:35:35
 */
public class GoWindow extends JoglWindow implements Observer {

    public void notifyObserver(Subject subject, Object params) {
        Extractor extractor = (Extractor) params;

        visualizer.setSubGraph(extractor.getGoSubGraph());
        visualizer.setSelectedNode(extractor.getSelectedNode());
        visualizer.drawSubgraph();

        repaint();
    }

}
