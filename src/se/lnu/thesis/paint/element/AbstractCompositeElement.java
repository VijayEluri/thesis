package se.lnu.thesis.paint.element;

import org.apache.log4j.Logger;

import javax.media.opengl.GLAutoDrawable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 16:32:32
 */
public abstract class AbstractCompositeElement extends AbstractElement implements CompositeElement {

    public static final Logger LOGGER = Logger.getLogger(AbstractCompositeElement.class);

    private boolean layoutComputed = false;

    protected Collection<Object> objects;

    private SortedSet<Element> elements = new TreeSet<Element>(new ElementDrawingOrderComparator());

    private Map<Object, Element> obj2Element = new HashMap<Object, Element>();
    private Map<Integer, Element> id2Element = new HashMap<Integer, Element>();


    public ElementType getType() {
        return ElementType.COMPOSITE;
    }

    public int getDrawingOrder() {
        return VertexElement.VERTEX_DRAWING_ORDER;
    }

    public int getSize() {
        return objects != null ? objects.size() : 0;
    }

    public Collection<Element> getElements() {
        return elements;
    }

    public Collection<Integer> getIds() {
        return id2Element.keySet();
    }

    public void addElement(Element element) {
        elements.add(element);

        if (element.getId() != null) {
            id2Element.put(element.getId(), element);
        }

        if (element.getObject() != null) {
            obj2Element.put(element.getObject(), element);
        }
    }

    public Element getElementById(int i) {
        return id2Element.get(i);
    }

    public Element getElementByObject(Object o) {
        return obj2Element.get(o);
    }

    public Collection<Object> getObjects() {
        return objects;
    }

    public boolean isLayoutComputed() {
        return layoutComputed;
    }

    public boolean setLayoutComputed(boolean b) {
        return layoutComputed = b;
    }

    @Override
    public boolean has(Object o) {
        return objects.contains(o) || (getObject() != null && getObject().equals(o));
    }

    @Override
    public boolean hasAny(Collection nodes) {

        for (Object o : nodes) {
            if (has(o) || obj2Element.containsKey(o)) {
                return true;
            }
        }

        for (Element element : getElements()) {
            if (element.hasAny(nodes)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setHighlighted(Collection objects) {
        if (hasAny(objects)) {
            setHighlighted(true);
            for (Element element : getElements()) {
                element.setHighlighted(objects);
            }
        }
    }

    @Override
    public void resetHighlighting() {
        setHighlighted(false);
        for (Element element : getElements()) {
            element.resetHighlighting();
        }
    }


    public void drawContent(GLAutoDrawable drawable) {
        for (Element element : getElements()) {
            element.draw(drawable);
        }
    }
}
