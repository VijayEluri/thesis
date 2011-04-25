package se.lnu.thesis.layout;

import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.AbstractElement;
import se.lnu.thesis.element.VertexElement;
import se.lnu.thesis.paint.visualizer.AbstractElementVisualizer;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 22:15:20
 */
public class LevelLayout extends UniformDistributionLayout {

    protected AbstractElementVisualizer leafVisualizer;

    public LevelLayout() {
        setVertexVisualizer(ElementVisualizerFactory.getInstance().getGONodeCircleVisualizer());
        setLeafVisualizer(ElementVisualizerFactory.getInstance().getGOLeafCircleVisualizer());
    }

    public LevelLayout(Graph graph) {
        super(graph);

        setVertexVisualizer(ElementVisualizerFactory.getInstance().getGONodeCircleVisualizer());
        setLeafVisualizer(ElementVisualizerFactory.getInstance().getGOLeafCircleVisualizer());
    }


    @Override
    protected void setElementPosition(Object o) {
        VertexElement vertexElement = VertexElement.init(o, p.getX(), p.getY());
        vertexElement.setTooltip(((MyGraph) getGraph()).getLabel(o));

        if (GraphUtils.isLeaf(graph, o)) {
            vertexElement.setVisualizer(getLeafVisualizer());
        } else {
            vertexElement.setVisualizer(getVertexVisualizer());
        }

        root.addElement(vertexElement);
    }

    public AbstractElementVisualizer getLeafVisualizer() {
        return leafVisualizer;
    }

    public void setLeafVisualizer(AbstractElementVisualizer leafVisualizer) {
        this.leafVisualizer = leafVisualizer;
    }
}