package se.lnu.thesis.paint;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 15:35:44
 */
public interface Drawable {

    /**
     *
     *      Draw using OpenGL
     *
     * @param drawable OpenGL drawing context
     */
    void draw(GLAutoDrawable drawable);

}
