package se.lnu.thesis.paint.element;

import se.lnu.thesis.paint.visualizer.element.AbstractGraphElementVisualizer;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:54:07
 */
public class GroupElement extends VertexElement {

    private List<Object> nodes;

    private Map<Object, AbstractGraphElement> objElementMap;
    private Map<Integer, AbstractGraphElement> idElementMap;

    public GroupElement() {
        super();
    }

    public GroupElement(Object o, Point2D position, AbstractGraphElementVisualizer visualizer, List<Object> nodes) {
        init(o, position, visualizer, nodes);
    }

    @Override
    public void init(Object o, Point2D position, AbstractGraphElementVisualizer visualizer) {
        init(o, position, visualizer, null);
    }

    public void init(Object o, Point2D position, AbstractGraphElementVisualizer visualizer, List<Object> nodes) {
        super.init(o, position, visualizer);

        objElementMap = new HashMap<Object, AbstractGraphElement>();
        idElementMap = new HashMap<Integer, AbstractGraphElement>();

        this.nodes = nodes;
    }


    @Override
    public boolean has(Object o) {
        return objElementMap.containsKey(o) || (getObject() != null && getObject().equals(o));
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
        return objElementMap.values();
    }

    public Collection<Object> getObjects() {
        return objElementMap.keySet();
    }

    public Collection<Integer> getIds() {
        return idElementMap.keySet();
    }

    public void addElement(AbstractGraphElement element) {
        if (element != null && element.getObject() != null && element.getId() != null) {
            objElementMap.put(element.getObject(), element);
            idElementMap.put(element.getId(), element);
        }

    }

    public AbstractGraphElement getElementById(int i) {
        return idElementMap.get(i);
    }

    public AbstractGraphElement getElementByObject(Object obj) {
        return objElementMap.get(obj);
    }

    public List<Object> getNodes() {
        return nodes;
    }

    public boolean isInnerLayoutComputed() {
        return nodes.size() == objElementMap.size();
    }
}