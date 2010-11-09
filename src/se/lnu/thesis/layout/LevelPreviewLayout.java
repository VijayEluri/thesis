package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 22:15:20
 */
public class LevelPreviewLayout extends UniformDistributionLayout {

    public LevelPreviewLayout() {

    }

    public LevelPreviewLayout(Graph graph) {
        super(graph);
    }


    @Override
    protected void setElementPosition(Object o) {

        if (GraphUtils.isLeaf(graph, o)) {
            root.addElement(VertexElement.init(o, p.getX(), p.getY(), ElementVisualizerFactory.getInstance().getGOLeafPointVisualizer()));
        } else {
            root.addElement(VertexElement.init(o, p.getX(), p.getY(), ElementVisualizerFactory.getInstance().getGONodePointVisualizer()));
        }

    }

}