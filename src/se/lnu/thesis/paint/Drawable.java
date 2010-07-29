package se.lnu.thesis.paint;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.07.2010
 * <p/>
 * Common interface for all drawing elements on the scene
 */
public interface Drawable {

    void draw(GLAutoDrawable drawable);

}
