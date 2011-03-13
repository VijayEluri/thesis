package se.lnu.thesis.element;

import se.lnu.thesis.paint.visualizer.ElementVisualizer;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.10.2010
 * Time: 19:53:14
 */
public abstract class VisualizableElement extends AbstractElement implements Visualizable {

    protected ElementVisualizer visualizer;

    public void draw(GLAutoDrawable drawable) {
        if (isDrawn() && visualizer != null) {
            visualizer.draw(drawable, this);
        }
    }

    public void setVisualizer(ElementVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public ElementVisualizer getVisualizer() {
        return visualizer;
    }
}
