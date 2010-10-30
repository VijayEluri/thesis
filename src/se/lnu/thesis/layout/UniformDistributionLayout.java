package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.paint.element.Container;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 22:15:20
 */
public class UniformDistributionLayout extends AbstractLayout {

    public static final Logger LOGGER = Logger.getLogger(UniformDistributionLayout.class);

    protected Collection nodes;

    protected Point2D p;

    protected Point2D step;

    protected Point2D start;
    protected Point2D dimension;

    public UniformDistributionLayout(Graph graph) {
        super(graph);
    }

    public UniformDistributionLayout(Graph graph, Container root) {
        super(graph, root);
    }

    public void compute() {

        if (nodes.size() == 1) { // find place for only one node?
            p = new Point2D.Double(start.getX() + (dimension.getX() / 2), start.getY() + (dimension.getY() / 2));
            setElementPosition(nodes.iterator().next());
        } else {
            double pixelPerElement = Math.sqrt((dimension.getX() * dimension.getY()) / nodes.size());

            int columns = (int) (dimension.getX() / pixelPerElement);
            int rows = (int) (dimension.getY() / pixelPerElement);

            LOGGER.debug("columns " + columns);
            LOGGER.debug("rows " + rows);


            int more = nodes.size() - (columns * rows); // is this row x columns count is enouph?

            if (more > 0) { // No! More elements needs to be disctributed
                LOGGER.debug("Find more space for " + more + " count");

                if (more == 1) {
                    rows++;
                } else {
                    rows += more / columns;
                    if (more % columns > 0) {
                        rows++;
                    }
                }
            }


            LOGGER.debug("columns " + columns);
            LOGGER.debug("rows " + rows);

            step = new Point2D.Double(dimension.getX() / columns, dimension.getY() / rows);
            LOGGER.debug("step [" + step.getX() + ", " + step.getY() + "]");

            p = new Point2D.Double(start.getX(), start.getY());

            int count = 0;
            Iterator element = nodes.iterator();
            for (int j = 0; j < rows; j++) {
                if (j == (rows - 1)) {
                    step.setLocation(dimension.getX() / (nodes.size() - count), step.getY());
                }

                for (int i = 0; i < columns; i++) {
                    if (count < nodes.size()) {
                        setElementPosition(element.next());
                        //point(pX, pY);
                        count++;
                    }
                    p.setLocation(p.getX() + step.getX(), p.getY());
                }
                p.setLocation(start.getX(), p.getY() - step.getY());
            }
        }


    }

    protected void setElementPosition(Object o) {
        root.addElement(VertexElement.init(o, p.getX(), p.getY(), ElementVisualizerFactory.getInstance().getPointVisualizer()));
    }

    public void setObjects(Collection nodes) {
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
