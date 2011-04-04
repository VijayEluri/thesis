package se.lnu.thesis.paint.controller;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.paint.state.GraphState;
import se.lnu.thesis.paint.state.NoneGraphState;
import se.lnu.thesis.utils.MyColor;
import se.lnu.thesis.properties.PropertiesHolder;

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

    protected MyColor background = PropertiesHolder.getInstance().getColorSchema().getBackground();

    private GraphState state = new NoneGraphState(this);

    protected MyGraph graph;
    protected Graph subGraph;

    protected Container root;

    public abstract void init();

    public void draw(GLAutoDrawable drawable) {
        getState().draw(drawable);
    }

    public void mouseMove(Point cursor) {
        getState().mouseMove(cursor);
    }

    public void leftMouseButtonClicked(Point cursor) {
        getState().leftMouseButtonClicked(cursor);
    }

    public void mouseExited() {
        getState().mouseExited();
    }

    public void rightMouseButtonClicked(Point cursor) {
        getState().rightMouseButtonClicked(cursor);
    }

    public void leftMouseButtonPressed(Point cursor) {
        getState().leftMouseButtonPressed(cursor);
    }

    public void leftMouseButtonDragged(Point cursor) {
        getState().leftMouseButtonDragged(cursor);
    }

    public void leftMouseButtonReleased(Point cursor) {
        getState().leftMouseButtonReleased(cursor);
    }

    public void setSubGraph(Graph subGraph) {
        if (subGraph != null) {
            setSubGraph(null);

            this.subGraph = subGraph;

            if (root != null) {
                root.setHighlighted(subGraph.getVertices());
            }
        } else {
            this.subGraph = null;

            if (root != null) {
                root.resetHighlighting();
            }
        }
    }

    public void setGraph(MyGraph graph) {
        this.graph = graph;
    }

    public MyGraph getGraph() {
        return graph;
    }

    public MyColor getBackground() {
        return background;
    }

    public void setBackground(MyColor background) {
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
