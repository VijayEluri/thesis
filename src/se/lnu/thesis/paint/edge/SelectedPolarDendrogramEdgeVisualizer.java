package se.lnu.thesis.paint.edge;

import se.lnu.thesis.paint.Visualizer;


public class SelectedPolarDendrogramEdgeVisualizer extends PolarDendrogramEdgeVisualizer {

    public SelectedPolarDendrogramEdgeVisualizer(Visualizer visualizer) {
        super(visualizer);
    }

    @Override
    public void color() {
        getVisualizer().getApplet().stroke(255, 255, 0);
    }

}
