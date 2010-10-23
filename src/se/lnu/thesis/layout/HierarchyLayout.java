package se.lnu.thesis.layout;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.paint.element.Container;
import se.lnu.thesis.paint.element.Level;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;

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
        Multimap levels = TreeMultimap.create();
        int levelCount = GraphUtils.computeLevels(graph, levels);

        Point2D.Double dimension = new Point2D.Double(2.0, 2.0 / levelCount);

        LevelLayout levelPreviewLayout = new LevelLayout(graph);

        for (int i = 0; i < levelCount; i++) {
            Point2D.Double position = new Point2D.Double(-1.0, 1.0 - dimension.getY() * i);

            Level level = Level.init(i, levels.get(i));
            level.getPreview().setPosition(position);
            level.getPreview().setDimension(dimension);

            levelPreviewLayout.setRoot(level.getPreview());
            levelPreviewLayout.setObjects(level.getObjects());
            levelPreviewLayout.setDimension(level.getPreview().getDimension());
            levelPreviewLayout.setStart(level.getPreview().getPosition());
            levelPreviewLayout.compute();


            root.addElement(level);
        }

    }

}
