package se.lnu.thesis.layout;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.utils.GraphUtils;
import se.lnu.thesis.utils.Utils;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.03.2010
 * Time: 18:26:56
 */
public class SpiralLayout<V, E> extends AbstractLayout<V, E> {

    public static final double ANGLE_STEP = 30;
    public static final double NODE_K = 0.001;
    public static final double LEAF_K = 0.0011;


    public SpiralLayout(Graph graph) {
        super(graph);
    }


    public void initialize() {
        V longestLeaf = null;
        int longestPath = 0;

        for (V node : getGraph().getVertices()) {
            if (getGraph().outDegree(node) == 0) {
                int height = GraphUtils.getInstance().getNodeHeight(getGraph(), node, 0);
                if (height > longestPath) {
                    longestPath = height;
                    longestLeaf = node;
                }
            }
        }

        List nodes = GraphUtils.getInstance().invertDfsNodes(getGraph(), longestLeaf);

        double angle;
        double x, y;
        for (int i = nodes.size() - 1; i >= 0; i--) {
            V node = (V) nodes.get(i);

            angle = Utils.inRadians(i * ANGLE_STEP);

            x = NODE_K * angle * Math.cos(angle);
            y = NODE_K * angle * Math.sin(angle);

            setLocation(node, x, y);


            Collection<V> successors = getGraph().getSuccessors(node);
            for (V successor : successors) {
                //  if (GraphUtils.getInstance().isLeaf(getGraph(),successor)) {
                x = LEAF_K * angle * Math.cos(angle);
                y = LEAF_K * angle * Math.sin(angle);

                setLocation(successor, x, y);
                //  }
            }
        }

    }

    public void reset() {

    }
}
