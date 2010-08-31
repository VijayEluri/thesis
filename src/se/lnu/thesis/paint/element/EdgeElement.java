package se.lnu.thesis.paint.element;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.paint.visualizer.GraphElementVisualizer;
import se.lnu.thesis.utils.IdUtils;

import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:44:55
 */
public class EdgeElement extends AbstractGraphElement {

    public static EdgeElement init(Object o, Object source, Object target, Point2D start, Point2D end, GraphElementVisualizer visualizer) {
        EdgeElement result = new EdgeElement();

        result.setId(IdUtils.next());
        result.setObject(o);

        result.setFrom(source);
        result.setTo(target);

        result.setDraw(true);

        result.setStartPosition(start);
        result.setEndPosition(end);

        result.setVisualizer(visualizer);

        return result;
    }


    public static EdgeElement init(Object o, Graph graph, GroupElement root) {
        EdgeElement result = new EdgeElement();

        result.setId(IdUtils.next());
        result.setObject(o);

        Object source = graph.getSource(o);
        result.setFrom(source);

        Object dest = graph.getDest(o);
        result.setTo(dest);

        result.setDraw(false);

        result.setStartPosition(((VertexElement) (root.getElementByObject(source))).getPosition());
        result.setEndPosition(((VertexElement) (root.getElementByObject(dest))).getPosition());

        result.setVisualizer(ElementVisualizerFactory.getInstance().getLineEdgeVisializer());

        return result;
    }

    private Object from;
    private Object to;
    private Point2D startPosition;
    private Point2D endPosition;

    public EdgeElement() {

    }

    @Override
    public boolean has(Object o) {
        return from.equals(o) || to.equals(o);
    }


    @Override
    public boolean hasAny(Collection nodes) {
        return nodes.contains(from) && nodes.contains(to);
    }

    @Override
    public GraphElementType getType() {
        return GraphElementType.EDGE;
    }

    public int getDrawingOrder() {
        return -1;
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public Object getTo() {
        return to;
    }

    public void setTo(Object to) {
        this.to = to;
    }

    public Point2D getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Point2D startPosition) {
        this.startPosition = startPosition;
    }

    public Point2D getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Point2D endPosition) {
        this.endPosition = endPosition;
    }

    @Override
    public void setSubgraphHighlighting(boolean b) {
        isSubgraphHighlighting = b;
        isDraw = b;
    }
}
