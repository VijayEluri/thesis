package se.lnu.thesis.element;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.08.2010
 * <p/>
 * Compare drawing order of two AbstractGraphElements
 */
public class GraphElementDrawingOrderComparator implements Comparator<AbstractGraphElement> {

    public int compare(AbstractGraphElement element1, AbstractGraphElement element2) {

        if (element1.getDrawingOrder() < element2.getDrawingOrder()) {
            return -1;
        }

        return 1;
    }

}
