package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.paint.element.Container;

import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.08.2010
 * Time: 0:05:35
 */
public abstract class GraphController implements Drawable, Observer {

    public static final Logger LOGGER = Logger.getLogger(GraphController.class);

    public static final Color DEFAULT_BACKGROUND = Color.BLACK;

    protected Color background = DEFAULT_BACKGROUND;

    private GraphState state = new NoneGraphState(this);

    protected MyGraph graph;
    protected Graph subGraph;

    protected Container root;

    public abstract void init();

    public void draw(GLAutoDrawable drawable) {
        getState().draw(drawable);
    }

    public void mouseMove(Point point) {
        getState().mouseMove(point);
    }

    public void leftMouseButtonClicked(Point point) {
        getState().leftMouseButtonClicked(point);
    }

    public void setSubGraph(Graph subGraph) {
        this.subGraph = subGraph;
        getState().setSubGraph(subGraph);
    }

    public void setGraph(MyGraph graph) {
        this.graph = graph;
    }

    public MyGraph getGraph() {
        return graph;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Container getRoot() {
        return root;
    }

    public GraphState getState() {
        return state;
    }

    public void setState(GraphState state) {
        this.state = state;
    }

    public Graph getSubGraph() {
        return subGraph;
    }
}
