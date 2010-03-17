package layout;

import edu.uci.ics.jung.graph.Graph;
import processing.core.PApplet;

public class FullSizePolarDendrogramLayout<V, E> extends PolarDendrogramLayout<V, E> {


    public FullSizePolarDendrogramLayout(Graph graph) {
        super(graph);
    }

    @Override
    protected void computePositions() {

        computeRadius(getLeafs().size() * 10, 10);

        setXCenter((float) (getRadius() + 50));
        setYCenter((float) (getRadius() + 50));

        super.computePositions();
    }

    private void computeRadius(int elements, int distance) {
        setRadius((elements * distance) / PApplet.TWO_PI);
    }
}