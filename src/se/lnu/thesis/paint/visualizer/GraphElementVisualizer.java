package se.lnu.thesis.paint.visualizer;

import se.lnu.thesis.paint.element.AbstractGraphElement;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 0:59:03
 */
public interface GraphElementVisualizer {

    public void draw(GLAutoDrawable drawable, AbstractGraphElement element);

}
