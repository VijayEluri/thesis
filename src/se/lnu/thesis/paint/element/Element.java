package se.lnu.thesis.paint.element;

import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.paint.Orderable;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 14:55:26
 */
public interface Element extends Drawable, Visualizable, Orderable, Positionable {

    Integer getId();

    void setId(Integer id);

    Object getObject();

    void setObject(Object object);

    boolean has(Object o);

    boolean hasAny(Collection collection);

    void setHighlighted(Collection objects);

    void setHighlighted(boolean highlighting);

    void resetHighlighting();

    Boolean isHighlighted();

    ElementType getType();

    Boolean isSelected();

    void setSelected(Boolean selected);

    boolean isDrawed();

    void setDrawed(boolean drawed);

}
