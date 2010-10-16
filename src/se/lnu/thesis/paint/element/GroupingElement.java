package se.lnu.thesis.paint.element;

import com.google.common.collect.ImmutableSet;
import org.apache.log4j.Logger;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.utils.IdUtils;

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

    private static final Logger LOGGER = Logger.getLogger(GroupingElement.class);

    public static GroupingElement init(Object o, Point2D position, ElementVisualizer visualizer, Collection<Object> objects) {
        GroupingElement result = new GroupingElement();

        result.setId(IdUtils.next());
        result.setObject(o);
        result.setPosition(position);
        result.setVisualizer(visualizer);

        result.objects = ImmutableSet.copyOf(objects);

        return result;
    }

    private ElementVisualizer visualizer;

    @Deprecated
    public GroupingElement() {

    }

    public void draw(GLAutoDrawable drawable) {
        if (isDrawed() && visualizer != null) {
            visualizer.draw(drawable, this);
        }
    }

    public ElementVisualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(ElementVisualizer visualizer) {
        this.visualizer = visualizer;
    }
}