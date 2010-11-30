package se.lnu.thesis.element;

import se.lnu.thesis.paint.visualizer.ElementVisualizer;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 23.10.2010
 * Time: 15:18:10
 */
public class LevelPreview extends DimensionalContainer implements Visualizable {

    private ElementVisualizer visualizer;

    public ElementVisualizer getVisualizer() {
        return this.visualizer;
    }

    public void setVisualizer(ElementVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        visualizer.draw(drawable, this);
    }

}
