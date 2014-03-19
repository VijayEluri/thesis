package se.lnu.thesis.element;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:34:49
 * <p/>
 * <p/>
 * <p>
 * This interface summarize drawing order for elements
 * </p>
 */
public interface Orderable {

    int getDrawingOrder();

    public static class ElementDrawingOrderComparator implements Comparator<Orderable> {
        public int compare(Orderable o1, Orderable o2) {
            return o1.getDrawingOrder() <= o2.getDrawingOrder() ? -1 : 1;
        }
    }
}
