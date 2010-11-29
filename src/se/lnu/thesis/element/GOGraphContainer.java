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

    public int getZoomedLevels(Level level, List<Level> levels) {
        if (!levels.isEmpty()) {
            levels.clear();
        }

        int current = ((List) elements).indexOf(level);

        if (current == 0) {
            levels.add((Level) ((List) elements).get(current));
            levels.add((Level) ((List) elements).get(current + 1));
            levels.add((Level) ((List) elements).get(current + 2));

            return current;
        }

        if (current == elements.size() - 1) {
            levels.add((Level) ((List) elements).get(current));
            levels.add((Level) ((List) elements).get(current - 1));
            levels.add((Level) ((List) elements).get(current - 2));

            return current;
        }

        levels.add((Level) ((List) elements).get(current - 1));
        levels.add((Level) ((List) elements).get(current));
        levels.add((Level) ((List) elements).get(current + 1));

        return current - 1;
    }

    public int getZoomedLevels(int topLevelIndex, List<Level> levels) {
        if (!levels.isEmpty()) {
            levels.clear();
        }

        levels.add((Level) ((List) elements).get(topLevelIndex));
        levels.add((Level) ((List) elements).get(topLevelIndex + 1));
        levels.add((Level) ((List) elements).get(topLevelIndex + 2));

        return topLevelIndex;
    }


}
