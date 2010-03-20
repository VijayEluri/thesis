package se.lnu.thesis.paint.node;

import se.lnu.thesis.paint.AbstractGraphElementVisualizer;
import se.lnu.thesis.paint.Visualizer;
import se.lnu.thesis.utils.GraphUtils;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 1:20:56
 */
public abstract class AbstractNodeVisualizer extends AbstractGraphElementVisualizer {


    public AbstractNodeVisualizer(Visualizer visualizer) {
        super(visualizer);
    }

    public void draw(Object node) {

        if (GraphUtils.getInstance().isLeaf(getVisualizer().getGraph(), node)) {
            drawLeaf(node);
        } else {

            if (GraphUtils.getInstance().isRoot(getVisualizer().getGraph(), node)) {
                drawRoot(node);
            } else {
                drawNode(node);
            }
        }

    }

    public abstract void drawNode(Object node);

    public abstract void drawRoot(Object root);

    public abstract void drawLeaf(Object leaf);

}