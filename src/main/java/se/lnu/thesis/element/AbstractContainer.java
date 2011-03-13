package se.lnu.thesis.element;

import org.apache.log4j.Logger;

import javax.media.opengl.GLAutoDrawable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 16:32:32
 */
public abstract class AbstractContainer extends AbstractElement implements Container {

    public static final Logger LOGGER = Logger.getLogger(AbstractContainer.class);

    private boolean layoutComputed = false;

    protected Collection<Object> objects;

    protected Collection<Element> elements = new TreeSet<Element>(new Orderable.ElementDrawingOrderComparator());

    private Map<Object, Element> obj2Element = new HashMap<Object, Element>();
    private Map<Integer, Element> id2Element = new HashMap<Integer, Element>();


    public ElementType getType() {
        return ElementType.CONTAINER;
    }

    public int getDrawingOrder() {
        return VertexElement.VERTEX_DRAWING_ORDER;
    }

    public int getSize() {
        return objects != null ? objects.size() : 0;
    }

    public Iterator<Element> getElements() {
        return iterator();
    }

    public Iterator<Integer> getIds() {
        return id2Element.keySet().iterator();
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

    public void setLayoutComputed(boolean b) {
        this.layoutComputed = b;
    }

    @Override
    public boolean has(Object o) {
        return (objects != null && objects.contains(o)) || (getObject() != null && getObject().equals(o));
    }

    @Override
    public boolean hasAny(Collection collection) {

        for (Object o : collection) {
            if (has(o) || obj2Element.containsKey(o)) {
                return true;
            }
        }

        for (Element element : this) {
            if (element.hasAny(collection)) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * Highlight graph elements with specified object ids inside this container
     *
     * @param objects Collection with object ids to highlight
     */
    @Override
    public void setHighlighted(Collection objects) {
        if (hasAny(objects)) {
            Iterator<Element> iterator = iterator();
            while (iterator.hasNext()) {
                Element element = iterator.next();
                element.setHighlighted(objects);
            }
            setHighlighted(true);
        }
    }

    @Override
    public void resetHighlighting() {
        setHighlighted(false);
        for (Iterator<Element> i = getElements(); i.hasNext();) {
            Element element = i.next();
            element.resetHighlighting();
        }
    }

    public void draw(GLAutoDrawable drawable) {
        drawContent(drawable);
    }

    public void drawContent(GLAutoDrawable drawable) {
        for (Iterator<Element> i = getElements(); i.hasNext();) {
            Element element = i.next();
            element.draw(drawable);
        }
    }

    /**
     *
     * Get iterator for all elements inside this container.
     *
     * @return Returns read only Iterator object
     */
    public Iterator<Element> iterator() {
        final Iterator<Element> i = elements.iterator();

        return new Iterator<Element>() {
            public boolean hasNext() {
                return i.hasNext();
            }

            public Element next() {
                return i.next();
            }

            public void remove() {
                throw new UnsupportedOperationException("This iterator does not allow removing operation!");
            }
        };
    }

    /**
     * Return how many elements contain current container
     * @return Elements count
     */
    public int getElementsCount() {
        if (elements != null) {
            return elements.size();
        } else {
            return 0;
        }

    }
}
