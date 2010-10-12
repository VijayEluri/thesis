package se.lnu.thesis.paint.element;

import javax.media.opengl.GLAutoDrawable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 15:01:47
 */
public interface CompositeElement extends Element {

    int getSize();

    Collection<Element> getElements();

    Collection<Integer> getIds();

    void addElement(Element element);

    Element getElementById(int i);

    Element getElementByObject(Object o);

    Collection<Object> getObjects();

    boolean isLayoutComputed();

    boolean setLayoutComputed(boolean b);

    void drawContent(GLAutoDrawable drawable);

}
