package se.lnu.thesis.paint.visualizer.vertex;

import se.lnu.thesis.element.Element;
import se.lnu.thesis.element.GroupingElement;
import se.lnu.thesis.utils.DrawingUtils;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 13.03.11
 * Time: 22:42
 */
public class GroupingElementSmartHighlightVisualizer extends RectVertexVisualizer {

    public GroupingElementSmartHighlightVisualizer(MyColor color) {
        super(color);
    }

    public GroupingElementSmartHighlightVisualizer(MyColor color, double radius) {
        super(color, radius);
    }

    public GroupingElementSmartHighlightVisualizer(double size) {
        super(size);
    }

    @Override
    public void draw(GLAutoDrawable drawable, Element element) {
        setDrawable(drawable); // update OpenGL drawing contex

        if (element.getId() != null) {
            gl().glPushName(element.getId()); // set tag id
        }
        // TODO refactor this code. What the hell does it do??
        if (element.isFocused()) {
            setCurrentDrawingColor(getFocusedColor());
            drawShape(element); // draw main shape
        } else {
            if (element.isSelected()) {
                setCurrentDrawingColor(getSelectionColor());
                drawShape(element); // draw main shape
            } else {
                if (element.isHighlighted()) {
                    setCurrentDrawingColor(getMainColor());
                    drawShape(element); // draw main shape

                    setCurrentDrawingColor(getSubgraphColor());
                    drawHighlightingBox(element); // draw inner highlighting box
                } else {
                    setCurrentDrawingColor(getMainColor());
                    drawShape(element); // draw main shape
                }
            }
        }

        if (element.getId() != null) {
            gl().glPopName();
        }
    }

    protected void drawHighlightingBox(Element element) {
        GroupingElement groupingElement = (GroupingElement) element;

        final double maximumBoxRadius = getRadius();
        final double minimumBoxRadius = maximumBoxRadius * 0.5;

        final int minimumHighlightedVerticesCount = 1;
        final int maximumHighlightedVerticesCount = groupingElement.getObjects().size();
        final int highlightedVerticesCount = groupingElement.getHighlightedVerticesCount();

        final double boxRadius = (maximumBoxRadius - minimumBoxRadius) / (maximumHighlightedVerticesCount - minimumHighlightedVerticesCount) * (highlightedVerticesCount - minimumHighlightedVerticesCount) + minimumBoxRadius;

        gl().glPushMatrix();

        gl().glTranslated(element.getPosition().getX(), element.getPosition().getY(), 0.0);

        DrawingUtils.rect(gl(), -boxRadius, boxRadius, boxRadius, boxRadius, boxRadius, -boxRadius, -boxRadius, -boxRadius);

        gl().glPopMatrix();
    }
}
