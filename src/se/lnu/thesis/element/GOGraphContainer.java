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

    public List<Level> getZoomLevels(Level level) {
        List result = new LinkedList();

        int current = ((List) elements).indexOf(level);

        if (current == 0) {
            result.add(((List) elements).get(current));
            result.add(((List) elements).get(current + 1));
            result.add(((List) elements).get(current + 2));

            return result;
        }

        if (current == elements.size() - 1) {
            result.add(((List) elements).get(current));
            result.add(((List) elements).get(current - 1));
            result.add(((List) elements).get(current - 2));

            return result;
        }

        result.add(((List) elements).get(current - 1));
        result.add(((List) elements).get(current));
        result.add(((List) elements).get(current + 1));

        return result;
    }


}
