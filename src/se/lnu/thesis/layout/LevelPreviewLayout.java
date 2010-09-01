package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.paint.element.VertexElement;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;

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


    protected void addElement(Object o) {
        VertexElement element = VertexElement.init(o, p.getX(), p.getY(), ElementVisualizerFactory.getInstance().getPointVisualizer());
        element.setId(null); // this elements are not active for any interaction

        root.addElement(element);
    }

}