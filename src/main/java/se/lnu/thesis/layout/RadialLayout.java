package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.VertexElement;
import se.lnu.thesis.utils.Utils;

public class RadialLayout extends AbstractLayout {

    public static final double DEFAULT_RADIUS = 0.9;

    private double radius = DEFAULT_RADIUS;

    public RadialLayout() {
    }

    public RadialLayout(Graph graph, Container root) {
        super(graph, root);
    }

    public RadialLayout(Graph graph, Container root, double radius) {
        super(graph, root);
        setRadius(radius);
    }

    public void compute() {
        //main work here
        double step = 360.0 / getGraph().getVertices().size();

        double angle;
        int i = 0;
        for (Object node : getGraph().getVertices()) {
            angle = step * i++;

            double x = Math.cos(Utils.inRadians(angle)) * getRadius();
            double y = Math.sin(Utils.inRadians(angle)) * getRadius();

            root.addElement(VertexElement.init(node, x, y));
        }

        root.setLayoutComputed(true);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}