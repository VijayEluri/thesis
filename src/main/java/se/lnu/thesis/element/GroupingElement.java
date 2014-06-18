package se.lnu.thesis.element;

import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.utils.IdGenerator;

import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:54:07
 */
public class GroupingElement extends DimensionalContainer implements Visualizable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupingElement.class);

    public static GroupingElement init(Object o, Point2D position, ElementVisualizer visualizer, Collection<Object> objects) {
        GroupingElement result = new GroupingElement();

        result.setId(IdGenerator.next());
        result.setObject(o);
        result.setPosition(position);
        result.setVisualizer(visualizer);

        result.objects = ImmutableSet.copyOf(objects);

        return result;
    }

    private ElementVisualizer visualizer;

    private int highlightedVerticesCount = 0;

    protected GroupingElement() {

    }

    public void draw(GLAutoDrawable drawable) {
        if (isDrawn() && visualizer != null) {
            visualizer.draw(drawable, this);
        }
    }

    public ElementVisualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(ElementVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    @Override
    public void setHighlighted(Collection objects) {
        super.setHighlighted(objects);

        if (isHighlighted()) {
            highlightedVerticesCount = 0;
            for (Object o : objects) {
                if (getObjects().contains(o)) {
                    highlightedVerticesCount++;
                }
            }
        }
    }

    @Override
    public void resetHighlighting() {
        super.resetHighlighting();
        highlightedVerticesCount = 0;
    }

    public int getHighlightedVerticesCount() {
        return highlightedVerticesCount;
    }
}