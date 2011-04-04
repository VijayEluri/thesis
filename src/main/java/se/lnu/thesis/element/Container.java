package se.lnu.thesis.element;

import javax.media.opengl.GLAutoDrawable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 15:01:47
 */
public interface Container extends Element, Iterable<Element> {

    /**
     * Get amount of elements inside container
     * @return Amount of elements
     */
    int getSize();

    /**
     * Add element to container
     * @param element New instance of Element to add
     */
    void addElement(Element element);

    /**
     * Get Iterator object for all elements inside container
     * @return Iterator object
     */
    Iterator<Element> getElements();


    /**
     * Get elements count in the current container
     * @return Element count value
     */
    int getElementsCount();

    /**
     * Get read only Iterator object for all elements inside container
     * @return Read only iterator
     */
    Iterator<Element> iterator();

    /**
     * Get Iterator object for all element ids inside container
     * @return
     */
    Iterator<Integer> getIds();

    /**
     * Find Element object by drawing id
     * @param i Element id
     * @return Element instance with corresponded id
     */
    Element getElementById(int i);

    /**
     * Find Element object by object
     * @param o Element object
     * @return Element instance with corresponded object
     */
    Element getElementByObject(Object o);

    /**
     * Return Collection of objects inside container
     * @return Collection instance
     */
    Collection<Object> getObjects();

    /**
     * Is layout computed for current container and for current objects
     * @return True if layout been computed, False otherwise
     */
    boolean isLayoutComputed();

    /**
     * Set layout computation
     * @param b True or False
     */
    void setLayoutComputed(boolean b);

    /**
     * Set OpenGL drawable object
     * @param drawable GLAutoDrawable instance
     */
    void drawContent(GLAutoDrawable drawable);

    /**
     *          Delete all elements.
     *      Also reset highlighting and layout computation.
     */
    void clearElements();

}
