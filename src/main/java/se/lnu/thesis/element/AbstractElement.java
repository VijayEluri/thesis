package se.lnu.thesis.element;

import org.apache.log4j.Logger;

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

    private Integer id;
    private Object object;

    private boolean selected = false;
    private boolean highlighted = false;
    private boolean focused = false;
    private boolean drawed = true;

    private Point2D position;

    public abstract void draw(GLAutoDrawable drawable);

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

    public boolean isHighlighted() {
        return highlighted;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDrawn() {
        return drawed;
    }

    public void setDrawn(boolean drawn) {
        this.drawed = drawn;
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

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public boolean equals(Object o) {
        Element element = (Element) o;

        return this.object.equals(element.getObject());
    }
}
