package se.lnu.thesis.paint.element;

import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.paint.visualizer.element.GraphElementVisualizer;

import javax.media.opengl.GLAutoDrawable;

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
    private Boolean isSubgraph = false;

    private Boolean isDraw = true;

    private GraphElementVisualizer elementVisualizer;

    public AbstractGraphElement() {

    }

    public AbstractGraphElement(Object object) {
        setObject(object);
    }

    public AbstractGraphElement(Object object, GraphElementVisualizer elementVisualizer) {
        setObject(object);
        setElementVisualizer(elementVisualizer);
    }

    public void draw(GLAutoDrawable drawable) {
        if (isDraw && elementVisualizer != null) {
            elementVisualizer.draw(drawable, this);
        }
    }

    public GraphElementVisualizer getElementVisualizer() {
        return elementVisualizer;
    }

    public void setElementVisualizer(GraphElementVisualizer elementVisualizer) {
        this.elementVisualizer = elementVisualizer;
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
/*
        if (object == null) {
            throw new IllegalArgumentException("'object' argument can't be null!!");
        }
*/

        this.object = object;
    }

    public boolean has(Object o) {
        return object.equals(o);
    }

    public abstract GraphElementType getType();

    public Boolean isSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Boolean isSubgraph() {
        return isSubgraph;
    }

    public void setSubgraph(Boolean subgraph) {
        isSubgraph = subgraph;
    }

    public Boolean isDraw() {
        return isDraw;
    }

    public void setDraw(Boolean draw) {
        isDraw = draw;
    }
}
