package se.lnu.thesis.element;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:34:49
 *
 *
 * <p>
 * This interface summarize drawing order for elements
 * </p>
 */
public interface Orderable {

    int getDrawingOrder();


    public static class ElementDrawingOrderComparator implements Comparator<Orderable> {

        public int compare(Orderable element1, Orderable element2) {

            if (element1.getDrawingOrder() < element2.getDrawingOrder()) {
                return -1;
            }

            return 1;
        }

    }
}
