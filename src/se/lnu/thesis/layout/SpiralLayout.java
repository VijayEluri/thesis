package se.lnu.thesis.layout;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.03.2010
 * Time: 18:26:56
 */
public class SpiralLayout<V, E> extends AbstractLayout<V, E> {

    public static final double ANGLE_STEP = 15;
    public static final double NODE_K = 0.018;
    public static final double LEAF_K = 0.019;

    protected Set groupVertices;
    protected Set vertices;


    public SpiralLayout(Graph graph) {
        super(graph);
    }


    public void initialize() {

        List<V> path = GraphUtils.getInstance().longestPath(getGraph());

        vertices = new HashSet(path);
        groupVertices = new HashSet();

        double angle;
        double x, y;
        for (V node : path) {

            angle = Utils.inRadians(path.indexOf(node) * ANGLE_STEP);

            x = NODE_K * angle * Math.cos(angle);
            y = NODE_K * angle * Math.sin(angle);

            setLocation(node, x, y);


            for (V successor : getGraph().getSuccessors(node)) {
                if (!path.contains(successor)) {
                    x = x / NODE_K * LEAF_K; // x = LEAF_K * angle * Math.cos(angle);
                    y = y / NODE_K * LEAF_K; // y = LEAF_K * angle * Math.sin(angle);

                    setLocation(successor, x, y);

                    if (GraphUtils.getInstance().isLeaf(getGraph(), successor)) {
                        vertices.add(successor);
                    } else {
                        groupVertices.add(successor);
                    }

                }


            }
        }

        vertices.addAll(groupVertices);


    }

    public void reset() {

    }

    public Set getGroupVertices() {
        return groupVertices;
    }

    public void setGroupVertices(Set groupVertices) {
        this.groupVertices = groupVertices;
    }

    public Set getVertices() {
        return vertices;
    }

    public void setVertices(Set vertices) {
        this.vertices = vertices;
    }

}
