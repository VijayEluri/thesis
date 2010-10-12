package se.lnu.thesis.paint.element;

import se.lnu.thesis.paint.visualizer.ElementVisualizer;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.07.2010
 * <p/>
 * <p/>
 * <p/>
 * Common interface for all drawing elements on the scene
 */
public interface Visualizable {

    ElementVisualizer getVisualizer();

    void setVisualizer(ElementVisualizer visualizer);

}
