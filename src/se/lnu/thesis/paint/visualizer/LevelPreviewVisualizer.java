package se.lnu.thesis.paint.visualizer;

import se.lnu.thesis.element.DimensionalContainer;
import se.lnu.thesis.element.Element;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 10.11.2010
 * Time: 22:43:33
 */
public class LevelPreviewVisualizer extends LevelVisualizer {

    @Override
    public void draw(GLAutoDrawable drawable, Element element) {
        gl = drawable.getGL(); // update GL context

        DimensionalContainer container = (DimensionalContainer) element;

        if (container.isDrawed()) {

            if (element.getId() != null) {
                gl.glPushName(element.getId()); // set id for background
            }

            drawLevelBackground(container);

            drawLevelNumber(container);

            if (element.isFocused()) {
                drawLevelBorderBox(container);
            } else {
                drawLevelLines(container);
            }

            drawContent(drawable, container);

            if (element.getId() != null) {
                gl.glPopName();
            }

        }

    }

}
