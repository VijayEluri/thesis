package se.lnu.thesis.element;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 25.04.11
 * Time: 02:31
 *
 *
 *      Element has tooltip
 *
 */
public interface Tooltipable {

    /**
     * @param tooltip Set a tooltip
     */
    void setTooltip(String tooltip);


    /**
     * @return Return current tooltip
     */
    String getTooltip();

}
