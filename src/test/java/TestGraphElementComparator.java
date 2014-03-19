

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;
import se.lnu.thesis.element.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.08.2010
 * Time: 4:09:14
 */
public class TestGraphElementComparator {

    private static final Logger LOGGER = Logger.getLogger(TestGraphElementComparator.class);

    @Test
    public void compare() {

        List<AbstractElement> result = new LinkedList<AbstractElement>();

        assertTrue(result.add(GroupingElement.createContainer(0)));
        assertTrue(result.add(new VertexElement()));
        assertTrue(result.add(new VertexElement()));
        assertTrue(result.add(new VertexElement()));
        assertTrue(result.add(new EdgeElement()));
        assertTrue(result.add(new EdgeElement()));
        assertTrue(result.add(new EdgeElement()));
        assertTrue(result.add(new PolarEdge()));
        assertTrue(result.add(new PolarEdge()));
        assertTrue(result.add(new PolarEdge()));

        assertEquals(10, result.size());

        assertEquals(ElementType.CONTAINER, result.get(0).getType());
        assertEquals(ElementType.VERTEX,    result.get(1).getType());
        assertEquals(ElementType.VERTEX,    result.get(2).getType());
        assertEquals(ElementType.VERTEX,    result.get(3).getType());
        assertEquals(ElementType.EDGE,      result.get(4).getType());
        assertEquals(ElementType.EDGE,      result.get(5).getType());
        assertEquals(ElementType.EDGE,      result.get(6).getType());
        assertEquals(ElementType.EDGE,      result.get(7).getType());
        assertEquals(ElementType.EDGE,      result.get(8).getType());
        assertEquals(ElementType.EDGE,      result.get(9).getType());

        LOGGER.info("BEFORE SORT:");
        for (AbstractElement element : result) {
            LOGGER.info(element.getType());
        }

        Collections.sort(result, new Orderable.ElementDrawingOrderComparator());

        assertEquals(ElementType.EDGE,      result.get(0).getType());
        assertEquals(ElementType.EDGE,      result.get(1).getType());
        assertEquals(ElementType.EDGE,      result.get(2).getType());
        assertEquals(ElementType.EDGE,      result.get(3).getType());
        assertEquals(ElementType.EDGE,      result.get(4).getType());
        assertEquals(ElementType.EDGE,      result.get(5).getType());
        assertEquals(ElementType.CONTAINER, result.get(6).getType());
        assertEquals(ElementType.VERTEX,    result.get(7).getType());
        assertEquals(ElementType.VERTEX,    result.get(8).getType());
        assertEquals(ElementType.VERTEX,    result.get(9).getType());

        LOGGER.info("AFTER SORT:");
        for (AbstractElement element : result) {
            LOGGER.info(element.getType());
        }


    }


    @Test
    public void asSortedSet() {

        SortedSet<AbstractElement> result = new TreeSet<AbstractElement>(new Orderable.ElementDrawingOrderComparator());

        AbstractElement element = GroupingElement.createContainer(1);
        assertTrue(result.add(element));

        element = new PolarVertex();
        element.setObject(3);
        assertTrue(result.add(element));

        element = GroupingElement.createContainer(2);
        assertTrue(result.add(element));

        element = new EdgeElement();
        element.setObject(4);
        assertTrue(result.add(element));

        element = new VertexElement();
        element.setObject(5);
        assertTrue(result.add(element));

        assertEquals(5, result.size());

        for (AbstractElement e : result) {
            LOGGER.info("Element type: " + e.getType() + ", drawing order: " + e.getDrawingOrder());
        }

        assertTrue(ElementType.EDGE == result.first().getType());
        assertTrue(ElementType.EDGE != result.last().getType());

    }

}
