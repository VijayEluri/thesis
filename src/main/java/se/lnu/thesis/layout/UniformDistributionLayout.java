package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.VertexElement;
import se.lnu.thesis.paint.visualizer.AbstractElementVisualizer;
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

    protected AbstractElementVisualizer vertexVisualizer;

    public UniformDistributionLayout() {

    }

    public UniformDistributionLayout(Graph graph) {
        super(graph);
        setVertexVisualizer(ElementVisualizerFactory.getInstance().getPointVisualizer());
    }

    public UniformDistributionLayout(Graph graph, Container root) {
        super(graph, root);
        setVertexVisualizer(ElementVisualizerFactory.getInstance().getPointVisualizer());
    }

    public void compute() {

        checkArguments();

        if (nodes.size() == 1) { // find place for only one node?
            p = new Point2D.Double(start.getX() + (dimension.getX() / 2), start.getY() - (dimension.getY() / 2));
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
                    if (columns != 0) {
                        rows += more / columns;
                        if (more % columns > 0) {
                            rows++;
                        }
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
                if (j == (rows - 1)) { // last row
                    step.setLocation(dimension.getX() / (nodes.size() - count), step.getY());
                }

                for (int i = 0; i < columns; i++) {
                    if (count < nodes.size()) {
                        setElementPosition(element.next());
                        count++;
                    }

                    p.setLocation(p.getX() + step.getX(), p.getY());
                }

                p.setLocation(start.getX(), p.getY() - step.getY());
            }
        }


    }

    @Override
    protected boolean checkArguments() {
        if (super.checkArguments()) {
            if (getStart() == null) {
                throw new IllegalStateException("No start position been set!");
            }

            if (getDimension() == null) {
                throw new IllegalStateException("No dimension been set!");
            }

            return true;
        }

        return false;
    }

    protected void setElementPosition(Object o) {
        VertexElement vertexElement = VertexElement.init(o, p.getX(), p.getY(), getVertexVisualizer());
        vertexElement.setTooltip(((MyGraph) getGraph()).getLabel(o));
        root.addElement(vertexElement);
    }

    public AbstractElementVisualizer getVertexVisualizer() {
        return vertexVisualizer;
    }

    public void setVertexVisualizer(AbstractElementVisualizer vertexVisualizer) {
        this.vertexVisualizer = vertexVisualizer;
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
