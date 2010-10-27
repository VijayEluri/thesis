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

    public static void printBinaryTree(Graph graph) {

        Multimap levels = TreeMultimap.create();
        GraphUtils.computeLevels(graph, levels);

        Joiner joiner = Joiner.on("___");

        for (Object key : levels.keySet()) {
            System.out.println(joiner.join(levels.get(key)));
        }

    }

}
