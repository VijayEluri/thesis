package se.lnu.thesis.element;

import se.lnu.thesis.Scene;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;
import java.util.Iterator;
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

        result.showUnconnectedComponents = true;

        return result;
    }

    protected boolean showUnconnectedComponents = true;
    protected int levelCount = 0;

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

        if (current == getLevelCount() - 1) {
            levels.add((Level) ((List) elements).get(current - 2));
            levels.add((Level) ((List) elements).get(current - 1));
            levels.add((Level) ((List) elements).get(current));

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

    public void hideUnconnectedComponents() {
        Iterator<Element> iterator = getElements();
        while (iterator.hasNext()) {
            Level level = (Level) iterator.next();

            for (Object o: level.getObjects()) {
                if (GraphUtils.isUnconnectedComponent(Scene.getInstance().getGoGraph(), o)) {  // TODO optimize reference to static field.
                    level.getElementByObject(o).setDrawn(false);
                    level.getPreview().getElementByObject(o).setDrawn(false);
                }
            }
        }
    }

    public void showUnconnectedComponents() {
        Iterator<Element> iterator = getElements();
        while (iterator.hasNext()) {
            Level level = (Level) iterator.next();

            for (Object o: level.getObjects()) {
                if (GraphUtils.isUnconnectedComponent(Scene.getInstance().getGoGraph(), o)) {  // TODO optimize reference to static field.
                    level.getElementByObject(o).setDrawn(true);
                    level.getPreview().getElementByObject(o).setDrawn(true);
                }
            }
        }
    }

    /**
     *      Delete all elements.
     * Also reset highlighting and layout computation.
     */
    @Override
    public void clearElements() {
        super.clearElements();
        levelCount = 0;
    }

    @Override
    public void addElement(Element element) {
        super.addElement(element);

        if (element instanceof Level) { // TODO change it to something better. I thing we need new ElementType.LEVEL
            levelCount++;
        }
    }

    public boolean isShowUnconnectedComponents() {
        return showUnconnectedComponents;
    }

    public int getLevelCount() {
        return levelCount;
    }
}
