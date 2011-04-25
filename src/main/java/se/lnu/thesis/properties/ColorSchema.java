package se.lnu.thesis.properties;

import com.google.common.collect.ImmutableMap;
import se.lnu.thesis.utils.MyColor;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 09.12.2010
 * Time: 18:25:33
 * <p/>
 * Class stores color schema configuration for GoClusterViz program
 */
public class ColorSchema {

    public static final String COLOR_BACKGROUND = "color.background";
    public static final String COLOR_SELECTION = "color.selection";
    public static final String COLOR_FOCUSING = "color.focusing";
    public static final String COLOR_SUBGRAPH = "color.subgraph";
    public static final String COLOR_VERTICES_TOOLTIPS = "color.verticesTooltips";

    public static final String COLOR_GO_LEVEL_NUMBERS = "color.goLevelNumbers";
    public static final String COLOR_GO_LEVEL_LINES = "color.goLevelLines";
    public static final String COLOR_GO_LEAVES = "color.goLeaves";
    public static final String COLOR_GO_NODES = "color.goNodes";
    public static final String COLOR_GO_EDGES = "color.goEdges";

    public static final String COLOR_CLUSTER_LEAVES = "color.clusterLeaves";
    public static final String COLOR_CLUSTER_NODES = "color.clusterNodes";
    public static final String COLOR_CLUSTER_EDGES = "color.clusterEdges";

    public static final String COLOR_LENS = "color.lens";

    private ImmutableMap<String, MyColor> colors = new ImmutableMap.Builder<String, MyColor>()
            .put(COLOR_BACKGROUND, new MyColor())
            .put(COLOR_SELECTION, new MyColor())
            .put(COLOR_FOCUSING, new MyColor())
            .put(COLOR_SUBGRAPH, new MyColor())
            .put(COLOR_VERTICES_TOOLTIPS, new MyColor())

            .put(COLOR_GO_LEVEL_NUMBERS, new MyColor())
            .put(COLOR_GO_LEVEL_LINES, new MyColor())
            .put(COLOR_GO_LEAVES, new MyColor())
            .put(COLOR_GO_NODES, new MyColor())
            .put(COLOR_GO_EDGES, new MyColor())

            .put(COLOR_CLUSTER_LEAVES, new MyColor())
            .put(COLOR_CLUSTER_NODES, new MyColor())
            .put(COLOR_CLUSTER_EDGES, new MyColor())

            .put(COLOR_LENS, new MyColor())
            
            .build();


    public ColorSchema() {
    }

    public void useDefaultBlackSchema() {
        colors.get(COLOR_BACKGROUND).setColor(Color.BLACK);
        colors.get(COLOR_SELECTION).setColor(Color.GREEN);
        colors.get(COLOR_FOCUSING).setColor(Color.BLUE);
        colors.get(COLOR_SUBGRAPH).setColor(Color.YELLOW);
        colors.get(COLOR_VERTICES_TOOLTIPS).setColor(Color.CYAN);

        colors.get(COLOR_GO_LEVEL_NUMBERS).setColor(Color.WHITE);
        colors.get(COLOR_GO_LEVEL_LINES).setColor(Color.GRAY);
        colors.get(COLOR_GO_LEAVES).setColor(Color.RED);
        colors.get(COLOR_GO_NODES).setColor(Color.WHITE);

        Color grey = new Color(100, 100, 100);
        colors.get(COLOR_GO_EDGES).setColor(grey);
        colors.get(COLOR_CLUSTER_LEAVES).setColor(grey);
        colors.get(COLOR_CLUSTER_NODES).setColor(grey);
        colors.get(COLOR_CLUSTER_EDGES).setColor(grey);

        colors.get(COLOR_LENS).setColor(Color.BLACK);
        colors.get(COLOR_LENS).setAlfa(0.7f);
    }

    public MyColor getColor(String key) {
        return colors.get(key);
    }

    public MyColor getBackground() {
        return getColor(COLOR_BACKGROUND);
    }

    public MyColor getSelection() {
        return getColor(COLOR_SELECTION);
    }

    public MyColor getFocusing() {
        return getColor(COLOR_FOCUSING);
    }

    public MyColor getSubgraph() {
        return getColor(COLOR_SUBGRAPH);
    }

    public MyColor getVerticesTooltips() {
        return getColor(COLOR_VERTICES_TOOLTIPS);
    }

    public MyColor getGoLevelNumbers() {
        return getColor(COLOR_GO_LEVEL_NUMBERS);
    }

    public MyColor getGoLevelLines() {
        return getColor(COLOR_GO_LEVEL_LINES);
    }

    public MyColor getGoLeaves() {
        return getColor(COLOR_GO_LEAVES);
    }

    public MyColor getGoNodes() {
        return getColor(COLOR_GO_NODES);
    }

    public MyColor getGoEdges() {
        return getColor(COLOR_GO_EDGES);
    }

    public MyColor getClusterLeaves() {
        return getColor(COLOR_CLUSTER_LEAVES);
    }

    public MyColor getClusterNodes() {
        return getColor(COLOR_CLUSTER_NODES);
    }

    public MyColor getClusterEdges() {
        return getColor(COLOR_CLUSTER_EDGES);
    }

    public MyColor getLens() {
        return getColor(COLOR_LENS);
    }
}
