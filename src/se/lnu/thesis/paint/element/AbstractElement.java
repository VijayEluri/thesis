package se.lnu.thesis.paint.element;

import org.apache.log4j.Logger;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;

import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 02.07.2010
 * Time: 17:33:46
 */
public abstract class AbstractElement implements Element {

    public static final Logger LOGGER = Logger.getLogger(AbstractElement.class);

    protected Integer id;
    protected Object object;

    protected Boolean selected = false;
    protected Boolean highlighted = false;
    protected Boolean drawed = true;

    protected Point2D position;

    protected ElementVisualizer visualizer;

    public void draw(GLAutoDrawable drawable) {
        if (drawed && visualizer != null) {
            visualizer.draw(drawable, this);
        }
    }

    public Object getObject() {
        return object;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setObject(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Argument 'object' can't be null!!");
        }

        this.object = object;
    }

    public boolean has(Object o) {
        return object.equals(o);
    }

    public boolean hasAny(Collection objects) {
        return objects.contains(object);
    }

    public void setHighlighted(Collection objects) {
        if (hasAny(objects)) {
            setHighlighted(true);
        }
    }

    public void setHighlighted(boolean highlighting) {
        highlighted = highlighting;
    }

    public void resetHighlighting() {
        setHighlighted(false);
    }

    public Boolean isHighlighted() {
        return highlighted;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public boolean isDrawed() {
        return drawed;
    }

    public void setDrawed(boolean drawed) {
        this.drawed = drawed;
    }

    public ElementVisualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(ElementVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D p) {
        if (p != null) {
            if (position == null) {
                position = new Point2D.Double();
            }

            position.setLocation(p.getX(), p.getY());
        } else {
            AbstractElement.LOGGER.error("Can't set element position value cause argument is null!");
        }
    }

}
