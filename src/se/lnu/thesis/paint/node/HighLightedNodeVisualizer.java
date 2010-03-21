package se.lnu.thesis.paint.node;

import se.lnu.thesis.paint.Visualizer;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 */
public class HighLightedNodeVisualizer extends CircleNodeVisualizer {

    public HighLightedNodeVisualizer(Visualizer visualizer) {
        super(visualizer);
    }

    @Override
    public void color() {
        getVisualizer().getApplet().fill(255, 255, 0);
        getVisualizer().getApplet().stroke(255, 255, 0);
    }
}
