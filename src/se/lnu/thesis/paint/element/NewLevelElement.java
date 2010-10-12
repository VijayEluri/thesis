package se.lnu.thesis.paint.element;

import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.utils.IdUtils;

import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 16:03:28
 */
public class NewLevelElement extends VertexElement implements CompositeElement {

    public static NewLevelElement init(Object o, Point2D position, ElementVisualizer visualizer, Collection<Object> objects) {
        NewLevelElement result = new NewLevelElement();

//        result.nodes = nodes;

        result.setId(IdUtils.next());

        result.setObject(o);
        result.setPosition(position);
        result.setVisualizer(visualizer);

        return result;
    }

    protected CompositeElement preview;
    protected CompositeElement content;

    public int getSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Element> getElements() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Integer> getIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addElement(Element element) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Element getElementById(int i) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Element getElementByObject(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Object> getObjects() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isLayoutComputed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean setLayoutComputed(boolean b) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void drawContent(GLAutoDrawable drawable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
