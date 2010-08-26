package se.lnu.thesis.layout;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.element.GroupElement;
import se.lnu.thesis.element.VertexElement;
import se.lnu.thesis.paint.visualizer.element.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 22:15:20
 */
public class LevelsLayout extends AbstractLayout {

    public static double distance = 0.025;

    private Multimap levels;
    private int height;

    private double levelHeight;

    private Point2D position;

    public LevelsLayout(Graph graph, GroupElement root) {
        setGraph(graph);
        setRoot(root);
    }

    public void compute() {

        init();

        computeLevels();

        computePositions();

    }

    private void init() {
        levels = TreeMultimap.create();

        position = new Point2D.Double(-1 + distance, 1 - distance);
    }

    private void computeLevels() {
        height = GraphUtils.computeLevels(graph, levels);

        levelHeight = 2.0 / height;
    }

    private void computePositions() {


        for (Object level : levels.keySet()) {

            position.setLocation(-1, 1 - (levelHeight * (Integer) level));

            int elements = (int) Math.abs(Math.sqrt(levels.get(level).size()));
            int current = 0;
            double x_distance = 2.0 / elements;
            double y_distance = levelHeight / elements;

            for (Object o : levels.get(level)) {
                addElement(o);

                if (current > elements) {
                    position.setLocation(-1, position.getY() - y_distance);
                    current = 0;
                } else {
                    position.setLocation(position.getX() + x_distance, position.getY());
                    current++;
                }
            }
        }

    }

    private void addElement(Object o) {
        root.addElement(VertexElement.init(o, position.getX(), position.getY(), ElementVisualizerFactory.getInstance().getPointVisualizer()));
/*
        if (GraphUtils.isLeaf(graph, o)) {
            root.addElement(VertexElement.init(o, position.getX(), position.getY(), ElementVisualizerFactory.getInstance().getCircleVisualizer()));
        } else {
            root.addElement(VertexElement.init(o, position.getX(), position.getY(), ElementVisualizerFactory.getInstance().getRectVisualizer()));
        }
*/
    }

    public Multimap getLevels() {
        return levels;
    }

    public int getHeight() {
        return height;
    }

    public double getLevelHeight() {
        return levelHeight;
    }
}
