package se.lnu.thesis.layout;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.paint.element.Container;
import se.lnu.thesis.paint.element.LevelElement;
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

//        LevelPreviewLayout levelPreviewLayout = new LevelPreviewLayout(graph);
        LevelbarLayout levelPreviewLayout = new LevelbarLayout(graph);

        for (int i = 0; i < levelCount; i++) {
            /*          Point2D.Double position = new Point2D.Double(-1 + border, 1 - dimension.getY() * i - border * (i + 1));

                        LevelElement levelElement = LevelElement.init(i, levels.get(i));
                        levelElement.getPreview().setPosition(position);
                        levelElement.getPreview().setDimension(dimension);

                        levelPreviewLayout.setRoot(levelElement.getPreview());
                        levelPreviewLayout.setObjects(levelElement.getObjects());
                        levelPreviewLayout.setDimension(levelElement.getPreview().getDimension());
                        levelPreviewLayout.setStart(levelElement.getPreview().getPosition());
                        levelPreviewLayout.compute();
            */
            Point2D.Double position = new Point2D.Double(-1.0, 1.0 - dimension.getY() * i);

            LevelElement levelElement = LevelElement.init(i, levels.get(i));
            levelElement.getPreview().setPosition(position);
            levelElement.getPreview().setDimension(dimension);

            levelPreviewLayout.setRoot(levelElement.getPreview());
            levelPreviewLayout.setObjects(levelElement.getObjects());
            levelPreviewLayout.setDimension(levelElement.getPreview().getDimension());
            levelPreviewLayout.setStart(levelElement.getPreview().getPosition());
            levelPreviewLayout.compute();


            root.addElement(levelElement);
        }

    }

}
