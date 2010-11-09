package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.vertex.CircleVertexVisualizer;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 22:15:20
 */
public class LevelLayout extends UniformDistributionLayout {

    public LevelLayout() {

    }

    public LevelLayout(Graph graph) {
        super(graph);
    }


    @Override
    protected void setElementPosition(Object o) {

        if (GraphUtils.isLeaf(graph, o)) {
            root.addElement(VertexElement.init(o, p.getX(), p.getY(), new CircleVertexVisualizer(Color.RED)));
        } else {
            root.addElement(VertexElement.init(o, p.getX(), p.getY(), new CircleVertexVisualizer(Color.GREEN)));
        }

    }

}