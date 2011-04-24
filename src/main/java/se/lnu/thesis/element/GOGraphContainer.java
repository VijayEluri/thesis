package se.lnu.thesis.element;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.sun.istack.internal.Nullable;
import se.lnu.thesis.Scene;
import se.lnu.thesis.utils.GraphUtils;

import javax.swing.text.html.HTMLDocument;
import java.awt.geom.Point2D;
import java.util.*;

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
        for (Iterator<Element> i = getLevels(); i.hasNext();) {
            Level level = (Level) i.next();

            for (Object o : level.getObjects()) {
                if (GraphUtils.isUnconnectedComponent(Scene.getInstance().getGoGraph(), o)) {
                    level.getElementByObject(o).setDrawn(false);
                    level.getPreview().getElementByObject(o).setDrawn(false);
                }
            }
        }
    }

    public void showUnconnectedComponents() {
        for (Iterator<Element> i = getLevels(); i.hasNext();) {
            Level level = (Level) i.next();

            for (Object o : level.getObjects()) {
                if (GraphUtils.isUnconnectedComponent(Scene.getInstance().getGoGraph(), o)) {
                    level.getElementByObject(o).setDrawn(true);
                    level.getPreview().getElementByObject(o).setDrawn(true);
                }
            }
        }
    }

    /**
     * @return Return iterator over only Level elements of the GO container
     */
    public UnmodifiableIterator<Element> getLevels() {
        return Iterators.unmodifiableIterator(Iterators.filter(getElements(), new Predicate<Element>() {
            public boolean apply(Element input) {
                return input instanceof Level;

            }
        }));
    }

    /**
     * Delete all elements.
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


    public void showSubgraphEdges() {
        showSubgraphEdges(true);
    }

    public void showSubgraphEdges(boolean b) {
        for (Element element : this) {
            if (element.getType() == ElementType.EDGE) {
                ((GOEdgeElement) element).setShowHighlightedEdge(b);
            }
        }

    }

    public void hideSubgraphEdges() {
        showSubgraphEdges(false);
    }

}
