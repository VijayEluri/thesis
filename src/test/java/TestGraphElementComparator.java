

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

        List<AbstractElement> result = new LinkedList<AbstractElement>();

        assertTrue(result.add(new GroupingElement()));
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

        for (AbstractElement element : result) {
            System.out.println(element.getType());
        }

        Collections.sort(result, new Orderable.ElementDrawingOrderComparator());

        System.out.println("");

        for (AbstractElement element : result) {
            System.out.println(element.getType());
        }


    }


    @Test
    public void asSortedSet() {

        SortedSet<AbstractElement> result = new TreeSet<AbstractElement>(new Orderable.ElementDrawingOrderComparator());

        AbstractElement element = null;

        element = new GroupingElement();
        element.setObject(1);
        assertTrue(result.add(element));


        element = new PolarVertex();
        element.setObject(3);
        assertTrue(result.add(element));

        element = new GroupingElement();
        element.setObject(2);
        assertTrue(result.add(element));


        element = new EdgeElement();
        element.setObject(4);
        assertTrue(result.add(element));

        element = new VertexElement();
        element.setObject(5);
        assertTrue(result.add(element));


        assertEquals(5, result.size());

        assertEquals(ElementType.EDGE, result.first().getType());

    }

}
