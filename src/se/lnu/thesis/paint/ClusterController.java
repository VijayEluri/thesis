package se.lnu.thesis.paint;


import se.lnu.thesis.Scene;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.layout.RectangularSpiralLayout;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.element.DimensionalContainer;


public class ClusterController extends GraphController implements Observer {

    public void init() {
        root = DimensionalContainer.init("Cluster");

        RectangularSpiralLayout layout = new RectangularSpiralLayout(graph, root);
        layout.compute();

        setState(new NormalClusterState(this));
    }

    public void notifyObserver(Subject subject, Object params) {
        Extractor extractor = (Extractor) params;

        this.setSubGraph(extractor.getClusterSubGraph());

        Scene.getInstance().getMainWindow().repaint();
    }

}
