package se.lnu.thesis.paint.element;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.visualizer.element.AbstractGraphElementVisualizer;
import se.lnu.thesis.utils.Utils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:54:07
 */
public class VertexElement extends AbstractGraphElement {

    private Point2D position;

    public VertexElement() {
        init(null, null, null);
    }

    public VertexElement(Object o) {
        init(o, null, null);
    }

    public VertexElement(Object o, double x, double y) {
        setObject(o);
        setPosition(x, y);
    }

    public VertexElement(Object o, Point2D position) {
        init(o, position, null);
    }

    public VertexElement(Object o, Point2D position, AbstractGraphElementVisualizer visualizer) {
        init(o, position, visualizer);
    }


    public void init(Object o, Point2D position, AbstractGraphElementVisualizer visualizer) {
        setObject(o);

        setId(Utils.nextId());

        if (position != null) {
            setPosition(position);
        }

        setElementVisualizer(visualizer);
    }

    public GraphElementType getType() {
        return GraphElementType.NODE;
    }

    public GraphElementType checkType(Graph graph, Object o) {
        if (graph.inDegree(o) == 0) {
            return GraphElementType.ROOT;
        } else {
            if (graph.outDegree(o) == 0) {
                return GraphElementType.LEAF;
            } else {
                return GraphElementType.NODE;
            }
        }
    }


    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        setPosition(position.getX(), position.getY());
    }

    public void setPosition(double x, double y) {
        position = new Point2D.Double(x, y);
    }


}
