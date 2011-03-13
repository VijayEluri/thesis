package se.lnu.thesis.element;

import se.lnu.thesis.paint.Drawable;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 14:55:26
 *
 *
 *
 */
public interface Element extends Drawable, Orderable, Positionable {

    Integer getId();

    void setId(Integer id);

    Object getObject();

    void setObject(Object object);

    /**
     * Check does current element object equals to parameter object
     * @param o Parameter object to compare current with
     * @return True if parameter object equals to current, False otherwise
     */
    boolean has(Object o);

    boolean hasAny(Collection objects);

    /**
     *
     * Check does current element object contains in objects collection and if does then
     * highlight current element, if not doesn't do anything
     *
     * @param objects
     */
    void setHighlighted(Collection objects);

    /**
     * Set current element as highlighted or not
     * @param highlighting True or False
     */
    void setHighlighted(boolean highlighting);

    /**
     * Set highlighting to False
     */
    void resetHighlighting();

    /**
     * Check does current element is highlighted
     * @return True or False
     */
    boolean isHighlighted();

    /**
     * Return current element type
     * @return
     */
    ElementType getType();

    /**
     * Check if current element is selected by user
     * @return True if selected, False otherwise
     */
    boolean isSelected();

    /**
     * Set current element as selected or not
     * @param selected True or False
     */
    void setSelected(boolean selected);

    /**
     * Check if to draw current element or not
     * @return True if to draw, False otherwise
     */
    boolean isDrawn();

    /**
     * Set current element as drawn or not
     * @param drawn True if to draw current element, False otherwise
     */
    void setDrawn(boolean drawn);

    /**
     * Set current element as focused by user
     * @param focused True if in focus by user, False otherwise
     */
    void setFocused(boolean focused);

    /**
     * Check if current element is in focus by user
     * @return True if in focus, False otherwise
     */
    boolean isFocused();

}
