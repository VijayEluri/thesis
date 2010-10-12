package se.lnu.thesis.paint.element;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.utils.IdUtils;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:54:07
 */
public class GroupingElement extends AbstractCompositeElement {

    private static final Logger LOGGER = Logger.getLogger(GroupingElement.class);


    public static GroupingElement init(Object o, Point2D position, ElementVisualizer visualizer, Collection<Object> objects) {
        GroupingElement result = new GroupingElement();

        if (objects != null) {
            result.objects.addAll(objects);
        }

        result.setId(IdUtils.next());

        result.setObject(o);
        result.setPosition(position);
        result.setVisualizer(visualizer);

        return result;
    }

    public GroupingElement() {
        objects = new HashSet<Object>();
    }
}