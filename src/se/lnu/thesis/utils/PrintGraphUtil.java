package se.lnu.thesis.utils;

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


    public static void printBinaryTree(Graph graph) {
        Map<Object, Integer> levels = new HashMap();

        int height = GraphUtils.computeLevelsV2(graph, levels);


    }


}
