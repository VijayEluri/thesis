package se.lnu.thesis.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import edu.uci.ics.jung.graph.Graph;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 10.08.2010
 * Time: 14:07:06
 */
public class PrintGraphUtil {


    public static void printBinaryTree(Graph graph) {

        Multimap levels = TreeMultimap.create();
        int height = GraphUtils.computeLevels(graph, levels);

        StringBuffer result = new StringBuffer();
        Joiner joiner = Joiner.on("___");

        for (Object key : levels.keySet()) {
            System.out.println(joiner.join(levels.get(key)));
        }

    }

/*
    protected void printPart(Graph graph, Object o, Map<Object, Integer> levels) {
        int height = levels.get(o);

        for (int i = 0; i < height; i++) {
            printf("      ");
        }
        printf("%d", t - > info);
        printPart(t - > left);
        printPart(t - > right);
        printf("\n");
    }
*/
}
