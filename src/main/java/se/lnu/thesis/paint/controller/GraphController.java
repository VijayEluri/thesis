package se.lnu.thesis.paint.controller;

import com.google.common.base.Preconditions;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import se.lnu.thesis.Scene;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.event.BackgroundChangedEvent;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.paint.Drawable;
import se.lnu.thesis.paint.state.GraphState;
import se.lnu.thesis.paint.state.NoneGraphState;
import se.lnu.thesis.properties.PropertiesHolder;
import se.lnu.thesis.utils.MyColor;

import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.08.2010
 * Time: 0:05:35
 */
public abstract class GraphController implements Drawable, Observer, ApplicationEventPublisherAware, ApplicationListener {

    public static final Logger LOGGER = Logger.getLogger(GraphController.class);

    protected MyColor background = PropertiesHolder.getInstance().getColorSchema().getBackground();

    private GraphState state = new NoneGraphState(this);

    protected MyGraph graph;
    protected Graph subGraph;

    protected Container root;

    @Autowired
    private Scene scene;

    private ApplicationEventPublisher applicationEventPublisher;

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

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof BackgroundChangedEvent) {
            getBackground().setColor(((BackgroundChangedEvent) event).getColor());
        }
    }

    /**
     * Set up graph controller into "normal" state:
     * no focused elements,
     * no selections,
     * no lens showed for cluster,
     * not zooming levels for GO, etc.
     */
    public abstract void setNormalState();

    /**
     * Set application event publisher pipe for current class.
     */
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Publish event over application context pipe.
     */
    public void publish(ApplicationEvent applicationEvent) {
        Preconditions.checkNotNull(applicationEvent);

        applicationEventPublisher.publishEvent(applicationEvent);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
