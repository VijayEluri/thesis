package se.lnu.thesis.paint.element;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.visualizer.element.AbstractGraphElementVisualizer;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:54:07
 */
public class GroupElement extends VertexElement {

    private Map<Object, AbstractGraphElement> objElementMap;
    private Map<Integer, AbstractGraphElement> idElementMap;

    public GroupElement() {

    }

    public GroupElement(Graph graph, Object o, Point2D position) {
        super(graph, o, position);
    }

    public GroupElement(Graph graph, Object o, Point2D position, AbstractGraphElementVisualizer visualizer) {
        super(graph, o, position, visualizer);
    }

    @Override
    public void init(Graph graph, Object o, Point2D position, AbstractGraphElementVisualizer visualizer) {
        super.init(graph, o, position, visualizer);

        setType(GraphElementType.GROUP);

        objElementMap = new HashMap<Object, AbstractGraphElement>();
        idElementMap = new HashMap<Integer, AbstractGraphElement>();
    }


    @Override
    public boolean has(Object o) {
        return objElementMap.containsKey(o) || (getObject() != null && getObject().equals(o));
    }

    public int getSize() {
        if (getElements() != null) {
            return getElements().size();
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

}