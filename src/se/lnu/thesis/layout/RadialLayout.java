package se.lnu.thesis.layout;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.utils.Utils;

public class RadialLayout<V, E> extends AbstractLayout<V, E> {

    private double radius = 0.9;

    public RadialLayout(Graph graph) {
        super(graph);
    }


    public void initialize() {
        //main work here
        double step = 360.0 / getGraph().getVertices().size();

        double angle;
        int i = 0;
        for (V node : getGraph().getVertices()) {
            angle = step * i++;

            double x = Math.cos(Utils.inRadians(angle)) * getRadius();
            double y = Math.sin(Utils.inRadians(angle)) * getRadius();

            setLocation(node, x, y);
        }
    }

    public void reset() {
        setGraph(null);
        locations.clear();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}