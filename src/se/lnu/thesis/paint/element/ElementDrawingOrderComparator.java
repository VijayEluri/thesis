package se.lnu.thesis.paint.element;

import se.lnu.thesis.paint.Orderable;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.08.2010
 * <p/>
 * <p/>
 * Compare drawing order of two Orderable
 */
public class ElementDrawingOrderComparator implements Comparator<Orderable> {

    public int compare(Orderable element1, Orderable element2) {

        if (element1.getDrawingOrder() < element2.getDrawingOrder()) {
            return -1;
        }

        return 1;
    }

}
