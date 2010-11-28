package se.lnu.thesis.layout;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.Container;
import se.lnu.thesis.element.DimensionalContainer;
import se.lnu.thesis.element.GOGraphContainer;
import se.lnu.thesis.element.Level;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.10.2010
 * Time: 15:10:52
 */
public class HierarchyLayout extends AbstractLayout {

    private int levelCount;
    private Multimap levels;

    private LevelBarLayout levelLayout;
    private LevelBarLayout levelPreviewLayout;

    public HierarchyLayout(MyGraph graph, Container root) {
        super(graph, root);
    }

    public void compute() {
        checkArguments();

        Point2D d = ((GOGraphContainer) root).getDimension(); // graph container dimension
        Point2D p = root.getPosition(); // graph container position

        computeGeneOntologyLevels();


        levelLayout = new LevelBarLayout();
        levelLayout.setGraph(graph);
        levelLayout.setPartsLayout(new LevelLayout());

        Point2D levelDimension = new Point2D.Double(d.getX(), d.getY() / 3);
        Point2D levelPosition = new Point2D.Double(p.getX(), p.getY() - levelDimension.getY());


        levelPreviewLayout = new LevelBarLayout();
        levelPreviewLayout.setGraph(graph);
        levelPreviewLayout.setPartsLayout(new LevelPreviewLayout());

        Point2D previewDimension = new Point2D.Double(d.getX(), d.getY() / levelCount);

        for (int i = 0; i < levelCount; i++) {
            Point2D previewPosition = new Point2D.Double(p.getX(), p.getY() - previewDimension.getY() * i);

            Level level = Level.init(i, levels.get(i));

            // comptute elements positions for level preview
            computePositions(level.getPreview(), level.getObjects(), levelPreviewLayout, previewDimension, previewPosition);

            // compute elements positions for level
            computePositions(level, level.getObjects(), levelLayout, levelDimension, levelPosition);

            root.addElement(level);
        }

    }

    private void computePositions(DimensionalContainer root, Collection objects, LevelBarLayout layout, Point2D dimension, Point2D position) {
        root.setPosition(position);
        root.setDimension(dimension);

        layout.setRoot(root);

        layout.setObjects(objects);

        layout.setStart(position);
        layout.setDimension(dimension);

        layout.compute();
    }

    private void computeGeneOntologyLevels() {
        levels = TreeMultimap.create();
        levelCount = GraphUtils.computeLevels(graph, levels);
    }

}
