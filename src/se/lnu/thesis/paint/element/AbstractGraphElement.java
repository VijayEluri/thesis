package se.lnu.thesis.paint.element;

import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.paint.Orderable;
import se.lnu.thesis.paint.visualizer.GraphElementVisualizer;

import javax.media.opengl.GLAutoDrawable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:33:46
 */
public abstract class AbstractGraphElement implements Drawable, Orderable {

    protected Integer id;
    protected Object object;

    protected Boolean isSelected = false;
    protected Boolean isSubgraphHighlighting = false;

    protected Boolean isDraw = true;

    protected GraphElementVisualizer visualizer;

    public void draw(GLAutoDrawable drawable) {
        if (isDraw && visualizer != null) {
            visualizer.draw(drawable, this);
        }
    }

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

    public boolean hasAny(Collection collection) {
        return collection.contains(object);
    }

    public void setSubgraphHighlighting(Collection nodes) {
        if (hasAny(nodes)) {
            setSubgraphHighlighting(true);
        }
    }

    public void resetSubgraphHighlighting() {
        setSubgraphHighlighting(false);
    }

    public abstract GraphElementType getType();

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
