package se.lnu.thesis.element;

import se.lnu.thesis.paint.visualizer.element.AbstractGraphElementVisualizer;
import se.lnu.thesis.utils.IdUtils;

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

    private boolean isInnerLayoutComputed = false;

    public static GroupElement init(Object o, Point2D position, AbstractGraphElementVisualizer visualizer, List<Object> nodes) {
        GroupElement result = new GroupElement();

        result.objElementMap = new HashMap<Object, AbstractGraphElement>();
        result.idElementMap = new HashMap<Integer, AbstractGraphElement>();

        result.nodes = nodes;

        result.setId(IdUtils.next());

        result.setObject(o);
        result.setPosition(position);
        result.setVisualizer(visualizer);

        return result;
    }

    private List<Object> nodes;

    private Map<Object, AbstractGraphElement> objElementMap;
    private Map<Integer, AbstractGraphElement> idElementMap;

    @Deprecated
    public GroupElement() {
        this.objElementMap = new HashMap<Object, AbstractGraphElement>();
        this.idElementMap = new HashMap<Integer, AbstractGraphElement>();
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
        //return nodes.size() == objElementMap.size();
        return this.isInnerLayoutComputed;
    }


    public boolean setIsInnerLayoutComputed(boolean b) {
        return this.isInnerLayoutComputed = b;
    }


}