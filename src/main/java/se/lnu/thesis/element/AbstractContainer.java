package se.lnu.thesis.element;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
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

    public static final int CONTAINER_DRAWING_ORDER = 0;

    private boolean layoutComputed = false;

    protected Collection<Object> objects;

    protected Collection<Element> elements = new TreeSet<Element>(new Orderable.ElementDrawingOrderComparator());

    private Map<Object, Element> obj2Element = new HashMap<Object, Element>();
    private Map<Integer, Element> id2Element = new HashMap<Integer, Element>();


    public ElementType getType() {
        return ElementType.CONTAINER;
    }

    public int getDrawingOrder() {
        return CONTAINER_DRAWING_ORDER;
    }

    public int getSize() {
        return objects != null ? objects.size() : 0;
    }

    public UnmodifiableIterator<Element> getElements() {
        return iterator();
    }

    public UnmodifiableIterator<Integer> getIds() {
        return Iterators.unmodifiableIterator(id2Element.keySet().iterator());
    }

    public void addElement(Element element) {
        Preconditions.checkNotNull(element);

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
        Preconditions.checkNotNull(o);

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

        for (Object o : collection) {                    // TODO refactor it using Guave intersection check
            if (has(o) || obj2Element.containsKey(o)) {
                return true;
            }
        }

        for (Element element : this) { // TODO here all check how to remove loop and use lib intersection
            if (element.hasAny(collection)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Highlight graph elements with specified object ids inside this container
     *
     * @param objects Collection with object ids to highlight
     */
    @Override
    public void setHighlighted(Collection objects) {
        if (hasAny(objects)) {
            for (Element element : this) {
                element.setHighlighted(objects);
            }
            setHighlighted(true);
        }
    }

    @Override
    public void resetHighlighting() {
        setHighlighted(false);
        for (Element element : this) {
            element.resetHighlighting();
        }
    }

    public void draw(GLAutoDrawable drawable) {
        drawContent(drawable);
    }

    public void drawContent(GLAutoDrawable drawable) {
        for (Element element : this) {
            element.draw(drawable);
        }
    }

    /**
     * Get iterator for all elements inside this container.
     *
     * @return Returns read only Iterator object
     */
    public UnmodifiableIterator<Element> iterator() {
        return Iterators.unmodifiableIterator(elements.iterator());
    }

    /**
     * Return how many elements contain current container
     *
     * @return Elements count
     */
    public int getElementsCount() {
        if (elements != null) {
            return elements.size();
        } else {
            return 0;
        }

    }

    /**
     * Delete all elements.
     * Also reset highlighting and layout computation.
     */
    public void clearElements() {
//        setSelected(false);
        resetHighlighting();
        //      setFocused(false);
        //    setDrawn(true);

        if (elements != null) {
            elements.clear();

            setLayoutComputed(false);
        }

        if (obj2Element != null) {
            obj2Element.clear();
        }

        if (id2Element != null) {
            id2Element.clear();
        }
    }


    /**
     * Returns unmodifiable iterator over all elements inside current container and
     * inside containers belongs to this container.
     *
     * @return Instance of <code>UnmodifiableIterator</code> class.
     */
    public UnmodifiableIterator<Element> getAllElements() {
        Iterator<Element> result = getElements();

        for (Element element : this) {
            if (element.getType() == ElementType.CONTAINER) {
                result = Iterators.concat(result, ((Container) element).getAllElements());
            }
        }

        return Iterators.unmodifiableIterator(result);
    }

    /**
     * Return iterator over all edge elements for container and inner container elements.
     *
     * @return Iterator over edge elements in the GO graph container.
     */
    public UnmodifiableIterator<Element> getAllEdgeElements() {
        return Iterators.filter(getAllElements(), new Predicate<Element>() {
            public boolean apply(Element input) {
                return input.getType() == ElementType.EDGE;

            }
        });
    }
}
