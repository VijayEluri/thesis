package se.lnu.thesis.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import edu.uci.ics.jung.graph.Graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 10.08.2010
 * Time: 14:07:06
 */
public class PrintGraphUtil {

    private Map<Object, Integer> length = new HashMap<Object, Integer>();

    private int distance = 10;

    public static void printBinaryTree(Graph graph) {

        Multimap levels = TreeMultimap.create();
        int height = GraphUtils.computeLevels(graph, levels);

        StringBuffer result = new StringBuffer();

        Joiner joiner = Joiner.on("___");

        for (Object key : levels.keySet()) {
            System.out.println(joiner.join(levels.get(key)));
        }

    }

/*    public void computeLayout(Graph graph) {

        Object root = GraphUtils.getRoot(graph);

        calculateDimensionX(graph, root);

        int current = length.get(root) / 2 + distance;
        StringBuffer result = new StringBuffer();

        buildTree(graph, root, current, result);
    }

    protected void buildTree(Graph graph, Object node, int x, StringBuffer result) {

        int currentLength = length.get(node);

        int lastX = x - currentLength / 2;

        int sizeXofChild;
        int startXofChild;

        for (Object child: graph.getSuccessors(node)) {

            sizeXofChild = length.get(child);
            startXofChild = lastX + sizeXofChild / 2;

            buildTree(graph, child, startXofChild);

            lastX = lastX + sizeXofChild + distance;
        }

        this.currentPoint.y -= stepY;
    }

    protected int calculateDimensionX(Graph graph, Object root, StringBuffer result) {

        int length = 0;

        if (graph.outDegree(root) > 0) {
            for (Object child: graph.getSuccessors(root)) {
                length += calculateDimensionX(graph, child, result) + distance + child.toString().length();
            }
        }

        length = Math.max(0, length - distance);
        this.length.put(root, length);

        return length;
    }*/
}
