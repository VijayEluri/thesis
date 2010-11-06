package se.lnu.thesis.paint;

import javax.media.opengl.GLAutoDrawable;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 05.11.2010
 * Time: 16:32:22
 */
public class NoneGraphState extends GraphState {

    protected NoneGraphState() {

    }

    public NoneGraphState(GraphController controller) {
        setGraphController(controller);
    }

    protected void drawCurrentState(GLAutoDrawable drawable) {

    }
}
