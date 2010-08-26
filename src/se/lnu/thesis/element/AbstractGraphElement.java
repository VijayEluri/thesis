package se.lnu.thesis.element;

import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.paint.visualizer.element.GraphElementVisualizer;

import javax.media.opengl.GLAutoDrawable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:33:46
 */
public abstract class AbstractGraphElement implements Drawable {

    private Integer id;
    private Object object;

    private Boolean isSelected = false;
    private Boolean isSubgraphHighlighting = false;

    private Boolean isDraw = true;

    private GraphElementVisualizer visualizer;

    public void draw(GLAutoDrawable drawable) {
        if (isDraw && visualizer != null) {
            visualizer.draw(drawable, this);
        }
    }

/*
    @Override
    public boolean equals(Object o) {
        AbstractGraphElement element = (AbstractGraphElement) o;

        return getObject() != null && element.getObject() != null && getObject().equals(element.getObject());
    }

    @Override
    public int hashCode() {
        return getObject().hashCode();
    }
*/

    public GraphElementVisualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(GraphElementVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public Object getObject() {
        return object;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setObject(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument 'object' can't be null!!");
        }

        this.object = object;
    }

    public boolean has(Object o) {
        return object.equals(o);
    }

    public boolean has(Collection collection) {
        return collection.contains(object);
    }

    public void setSubgraphHighlighting(Collection nodes) {
        if (has(nodes)) {
            setSubgraphHighlighting(true);
        }
    }

    public void resetSubgraphHighlighting() {
        setSubgraphHighlighting(false);
    }

    public abstract GraphElementType getType();

    public abstract int getDrawingOrder();

    public Boolean isSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Boolean isSubgraphHighlighting() {
        return isSubgraphHighlighting;
    }

    public void setSubgraphHighlighting(boolean subgraphHighlighting) {
        isSubgraphHighlighting = subgraphHighlighting;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}
