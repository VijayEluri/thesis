package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.vertex.PointVertexVisualizer;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 22:15:20
 */
public class LevelPreviewLayout extends UniformDistributionLayout {

    public LevelPreviewLayout(Graph graph) {
        super(graph);
    }


    @Override
    protected void setElementPosition(Object o) {
/*
        VertexElement element = VertexElement.init(o, p.getX(), p.getY(), ElementVisualizerFactory.getInstance().getPointVisualizer());
        element.setId(null); // this elements are not active for any interaction
*/

        if (GraphUtils.isLeaf(graph, o)) {
            root.addElement(VertexElement.init(o, p.getX(), p.getY(), new PointVertexVisualizer(Color.RED)));
        } else {
            root.addElement(VertexElement.init(o, p.getX(), p.getY(), new PointVertexVisualizer(Color.GREEN)));
        }

        //root.addElement(element);
    }

}