package se.lnu.thesis.paint.visualizer.graph;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.07.2010
 * Time: 16:10:43
 * <p/>
 * <p/>
 * Drawing state class contains several states such as normal draw, selected,
 */
public class State {

    private final String name;

    private State(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static final State NORMAL = new State("NORMAL");
    public static final State SELECTING = new State("SELECTING");
    public static final State SELECTED = new State("SELECTED");
    public static final State SUBGRAPH = new State("SUBGRAPH");

}
