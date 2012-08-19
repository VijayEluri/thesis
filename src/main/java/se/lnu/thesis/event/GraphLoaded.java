package se.lnu.thesis.event;

import org.springframework.context.ApplicationEvent;
import se.lnu.thesis.core.MyGraph;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 19.08.12
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class GraphLoaded extends ApplicationEvent {

    private final MyGraph graph;

    public GraphLoaded(Object source, MyGraph graph) {
        super(source);

        this.graph = graph;
    }

    public MyGraph getGraph() {
        return graph;
    }
}
