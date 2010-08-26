package se.lnu.thesis.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Test
    public void compare() {

        List<AbstractGraphElement> result = new LinkedList<AbstractGraphElement>();

        assertTrue(result.add(new GroupElement()));
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

        for (AbstractGraphElement element : result) {
            System.out.println(element.getType());
        }

        Collections.sort(result, new GraphElementDrawingOrderComparator());

        System.out.println("");

        for (AbstractGraphElement element : result) {
            System.out.println(element.getType());
        }


    }


    @Test
    public void asSortedSet() {

        SortedSet<AbstractGraphElement> result = new TreeSet<AbstractGraphElement>(new GraphElementDrawingOrderComparator());

        AbstractGraphElement element = null;

        element = new GroupElement();
        element.setObject(1);
        assertTrue(result.add(element));


        element = new PolarVertex();
        element.setObject(3);
        assertTrue(result.add(element));

        element = new GroupElement();
        element.setObject(2);
        assertTrue(result.add(element));


        element = new EdgeElement();
        element.setObject(4);
        assertTrue(result.add(element));

        element = new VertexElement();
        element.setObject(5);
        assertTrue(result.add(element));


        assertEquals(5, result.size());

        assertEquals(GraphElementType.EDGE, result.first().getType());

    }

}
