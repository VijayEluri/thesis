package se.lnu.thesis.properties;

import se.lnu.thesis.utils.MyColor;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 09.12.2010
 * Time: 18:25:33
 *
 *      Class stores color schema configuration for GoClusterViz program
 *
 */
public class ColorSchema {

    private MyColor background;
    private MyColor selection;
    private MyColor focusing;
    private MyColor subgraph;

    private MyColor goLevelNumbers;
    private MyColor goLevelLines;
    private MyColor goLeaves;
    private MyColor goNodes;

    private MyColor clusterLeaves;
    private MyColor clusterNodes;
    private MyColor clusterEdges;
    private MyColor lens;

    public ColorSchema() {
        init();
    }

    protected void init() {
        background = new MyColor();
        selection = new MyColor();
        focusing = new MyColor();
        subgraph = new MyColor();

        goLevelNumbers = new MyColor();
        goLevelLines = new MyColor();
        goLeaves = new MyColor();
        goNodes = new MyColor();

        clusterLeaves = new MyColor();
        clusterNodes = new MyColor();
        clusterEdges = new MyColor();
        
        lens = new MyColor();
    }

    public void useDefaultBlackSchema() {
        background.setColor(Color.BLACK);
        selection.setColor(Color.GREEN);
        focusing.setColor(Color.BLUE);
        subgraph.setColor(Color.YELLOW);

        goLevelNumbers.setColor(Color.WHITE);
        goLevelLines.setColor(Color.GRAY);
        goLeaves.setColor(Color.RED);
        goNodes.setColor(Color.WHITE);

        Color tmp = new Color(100, 100, 100);
        clusterLeaves.setColor(tmp);
        clusterNodes.setColor(tmp);
        clusterEdges.setColor(tmp);

        lens.setColor(Color.BLACK);
        lens.setAlfa(0.53f);
    }

    public MyColor getBackground() {
        return background;
    }

    public MyColor getSelection() {
        return selection;
    }

    public MyColor getFocusing() {
        return focusing;
    }

    public MyColor getSubgraph() {
        return subgraph;
    }

    public MyColor getGoLevelNumbers() {
        return goLevelNumbers;
    }

    public MyColor getGoLevelLines() {
        return goLevelLines;
    }

    public MyColor getGoLeaves() {
        return goLeaves;
    }

    public MyColor getGoNodes() {
        return goNodes;
    }

    public MyColor getClusterLeaves() {
        return clusterLeaves;
    }

    public MyColor getClusterNodes() {
        return clusterNodes;
    }

    public MyColor getClusterEdges() {
        return clusterEdges;
    }

    public MyColor getLens() {
        return lens;
    }
}
