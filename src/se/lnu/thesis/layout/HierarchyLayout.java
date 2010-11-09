package se.lnu.thesis.layout;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.paint.element.Container;
import se.lnu.thesis.paint.element.DimensionalContainer;
import se.lnu.thesis.paint.element.Level;
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


    public HierarchyLayout(MyGraph graph, Container root) {
        super(graph, root);
    }

    public void compute() {
        checkArguments();

        Multimap levels = TreeMultimap.create();
        int levelCount = GraphUtils.computeLevels(graph, levels);

        Point2D levelDimension = new Point2D.Double(2.0, 1.0);
        Point2D levelPosition = new Point2D.Double(-1.0, 0.5);

        LevelBarLayout levelLayout = new LevelBarLayout();
        levelLayout.setGraph(graph);
        levelLayout.setPartsLayout(new LevelLayout());


        Point2D previewDimension = new Point2D.Double(2.0, 2.0 / levelCount);

        LevelBarLayout previewLayout = new LevelBarLayout();
        previewLayout.setGraph(graph);
        previewLayout.setPartsLayout(new LevelPreviewLayout());

        for (int i = 0; i < levelCount; i++) {
            Point2D.Double previewPosition = new Point2D.Double(-1.0, 1.0 - previewDimension.getY() * i);

            Level level = Level.init(i, levels.get(i));

            // comptute preview
            computePositions(level.getPreview(), level.getObjects(), previewLayout, previewDimension, previewPosition);

            // compute level
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

}
