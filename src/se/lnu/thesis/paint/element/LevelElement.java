package se.lnu.thesis.paint.element;

import se.lnu.thesis.paint.visualizer.LevelVisualizer;
import se.lnu.thesis.utils.IdUtils;

import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:12:17
 */
public class LevelElement extends GroupElement {


    public static LevelElement init(Object o, Point2D position, Point2D dimension, Collection<Object> nodes) {
        LevelElement result = new LevelElement();

        result.setObject(o);
        result.setId(IdUtils.next());

        result.preview.setId(result.getId());

        result.setPosition(position);
        result.setDimension(dimension);

        result.setNodes(nodes);

        result.setVisualizer(new LevelVisualizer());

        return result;
    }

    private Point2D dimension;

    private GroupElement preview;

    public LevelElement() {
        super();

        preview = new GroupElement();
    }

    public GraphElementType getType() {
        return GraphElementType.GROUP;
    }

    public GroupElement getPreview() {
        return preview;
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

    @Override
    public void setSubgraphHighlighting(Collection nodes) {
        super.setSubgraphHighlighting(nodes);

        if (isSubgraphHighlighting) {
            for (AbstractGraphElement element : getPreview().getElements()) {
                element.setSubgraphHighlighting(nodes);
            }
        }
    }

    @Override
    public void resetSubgraphHighlighting() {
        super.resetSubgraphHighlighting();

        for (AbstractGraphElement element : getPreview().getElements()) {
            element.resetSubgraphHighlighting();
        }

    }
}
