package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 22:15:20
 */
public class UniformDistributionLayout extends AbstractLayout {

    protected Collection nodes;

    protected Point2D p;

    protected Point2D start;
    protected Point2D dimension;

    public UniformDistributionLayout(Graph graph) {
        setGraph(graph);
    }

    public void compute() {

        p = new Point2D.Double(start.getX(), start.getY());

        int elementsInRow = (int) Math.abs(Math.sqrt(nodes.size()));

        Point2D step = new Point2D.Double(dimension.getX() / elementsInRow, dimension.getY() / elementsInRow);

        int current = 0;
        for (Object o : nodes) {
            addElement(o);

            if (current > elementsInRow) {
                p.setLocation(start.getX(), p.getY() - step.getY());
                current = 0;
            } else {
                p.setLocation(p.getX() + step.getX(), p.getY());
                current++;
            }
        }

    }

    protected void addElement(Object o) {
        //root.addElement(VertexElement.init(o, p.getX(), p.getY(), ElementVisualizerFactory.getInstance().getPointVisualizer()));
        if (GraphUtils.isLeaf(graph, o)) {
            root.addElement(VertexElement.init(o, p.getX(), p.getY(), ElementVisualizerFactory.getInstance().getCircleVisualizer()));
        } else {
            root.addElement(VertexElement.init(o, p.getX(), p.getY(), ElementVisualizerFactory.getInstance().getTriangleVisualizer()));
        }
    }

    public void setNodes(Collection nodes) {
        this.nodes = nodes;
    }

    public Point2D getStart() {
        return start;
    }

    public void setStart(Point2D start) {
        this.start = start;
    }

    public Point2D getDimension() {
        return dimension;
    }

    public void setDimension(double width, double height) {
        if (dimension == null) {
            dimension = new Point2D.Double();
        }

        dimension.setLocation(width, height);

    }

    public void setDimension(Point2D dimension) {
        setDimension(dimension.getX(), dimension.getY());
    }
}
