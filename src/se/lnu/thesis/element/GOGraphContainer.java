package se.lnu.thesis.element;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 27.11.2010
 * Time: 21:38:58
 * <p/>
 * Container for Gene Ontology graph
 */
public class GOGraphContainer extends GraphContainer {

    public static GOGraphContainer init() {
        GOGraphContainer result = new GOGraphContainer();

        result.setObject("Gene Ontology graph");

        result.setPosition(new Point2D.Double(-1, 1));
        result.setDimension(new Point2D.Double(2, 2));

        result.objects = new LinkedList<Object>();
        result.elements = new LinkedList<Element>();

        return result;
    }

    public Level getPrev(Level level) {
        Level result = null;

        int i = ((List) elements).indexOf(level);

        if (i > 0) {
            result = (Level) ((List) elements).get(i - 1);
        }

        return result;
    }

    public Level getNext(Level level) {
        Level result = null;

        int i = ((List) elements).indexOf(level);

        if (i < elements.size() - 1) {
            result = (Level) ((List) elements).get(i + 1);
        }

        return result;
    }


}
