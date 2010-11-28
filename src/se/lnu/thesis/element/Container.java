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

    int getSize();

    void addElement(Element element);

    Iterator<Element> getElements();

    Iterator<Element> iterator();

    Iterator<Integer> getIds();

    Element getElementById(int i);

    Element getElementByObject(Object o);

    Collection<Object> getObjects();

    boolean isLayoutComputed();

    void setLayoutComputed(boolean b);

    void drawContent(GLAutoDrawable drawable);

}
