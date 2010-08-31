package se.lnu.thesis.paint.element;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.visualizer.AbstractGraphElementVisualizer;
import se.lnu.thesis.utils.IdUtils;

import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:54:07
 */
public class GroupElement extends VertexElement {

    private static final Logger LOGGER = Logger.getLogger(GroupElement.class);


    public static GroupElement init(Object o, Point2D position, AbstractGraphElementVisualizer visualizer, Collection<Object> nodes) {
        GroupElement result = new GroupElement();

        result.nodes = nodes;

        result.setId(IdUtils.next());

        result.setObject(o);
        result.setPosition(position);
        result.setVisualizer(visualizer);

        return result;
    }

    private boolean isLayoutComputed = false;

    private Collection<Object> nodes;

    private SortedSet<AbstractGraphElement> elements;

    private Map<Object, AbstractGraphElement> obj2Element;
    private Map<Integer, AbstractGraphElement> id2Element;


    public GroupElement() {
        this.nodes = new HashSet<Object>();

        this.obj2Element = new HashMap<Object, AbstractGraphElement>();
        this.id2Element = new HashMap<Integer, AbstractGraphElement>();

        this.elements = new TreeSet<AbstractGraphElement>(new GraphElementDrawingOrderComparator());
    }

    @Override
    public boolean has(Object o) {
        //return obj2Element.containsKey(o) || getObject().equals(o);
        return nodes.contains(o) || (getObject() != null && getObject().equals(o));
    }

    @Override
    public boolean hasAny(Collection nodes) {

        for (Object o : nodes) {
            if (has(o) || obj2Element.containsKey(o)) {
                return true;
            }
        }

        for (AbstractGraphElement element : getElements()) {
            if (element.hasAny(nodes)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void setSubgraphHighlighting(Collection nodes) {
        if (hasAny(nodes)) {
            setSubgraphHighlighting(true);
            for (AbstractGraphElement element : getElements()) {
                element.setSubgraphHighlighting(nodes);
            }
        }
    }

    @Override
    public void resetSubgraphHighlighting() {
        setSubgraphHighlighting(false);
        for (AbstractGraphElement element : getElements()) {
            element.resetSubgraphHighlighting();
        }
    }

    public GraphElementType getType() {
        return GraphElementType.GROUP;
    }

    public int getSize() {
        if (getNodes() != null) {
            return getNodes().size();
        }

        return 0;
    }

    public Collection<AbstractGraphElement> getElements() {
        return elements;
    }

    public Collection<Integer> getIds() {
        return id2Element.keySet();
    }

    public void addElement(AbstractGraphElement element) {
        if (element != null && element.getObject() != null) {
            elements.add(element);

            obj2Element.put(element.getObject(), element);
            id2Element.put(element.getId(), element);
        } else {
            LOGGER.error("Cant add new element to GroupElement!");
        }

    }

    public AbstractGraphElement getElementById(int i) {
        return id2Element.get(i);
    }

    public AbstractGraphElement getElementByObject(Object obj) {
        return obj2Element.get(obj);
    }

    public Collection<Object> getNodes() {
        return nodes;
    }

    protected void setNodes(Collection<Object> nodes) {
        this.nodes = nodes;
    }

    public boolean isLayoutComputed() {
        return this.isLayoutComputed;
    }


    public boolean setIsLayoutComputed(boolean b) {
        return this.isLayoutComputed = b;
    }


    public void drawElements(GLAutoDrawable drawable) {
        for (AbstractGraphElement element : getElements()) {
            element.draw(drawable);
        }
    }

}