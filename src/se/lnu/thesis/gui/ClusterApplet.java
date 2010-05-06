package se.lnu.thesis.gui;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import se.lnu.thesis.Thesis;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.layout.AbstractPolarDendrogramLayout;
import se.lnu.thesis.layout.PolarDendrogramLayout;
import se.lnu.thesis.paint.ClusterGraphWithBundlingVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;
import se.lnu.thesis.paint.edge.PolarDendrogramBundledEdgeVisualizer;
import se.lnu.thesis.paint.edge.PolarDendrogramEdgeVisualizer;
import se.lnu.thesis.paint.vertex.CircleVertexVisualizer;
import se.lnu.thesis.utils.myobserver.Subject;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ClusterApplet extends GOApplet {


    @Override
    protected AbstractLayout initLayout() {
        AbstractPolarDendrogramLayout result = new PolarDendrogramLayout(Thesis.getInstance().getClusterGraph());

        double height = getSize().getHeight();
        double width = getSize().getWidth();

        result.setRadius(0.45 * (height < width ? height : width));

        result.setXCenter((float) (height / 2));
        result.setYCenter((float) (width / 2));

        result.setSize(getSize());

        long start = System.currentTimeMillis();
        result.initialize(); // compute nodes positions
        long end = System.currentTimeMillis();

        System.out.println("Layout computing done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s"); // TODO use logger

        return result;
    }

    @Override
    protected GraphWithSubgraphVisualizer initVisualizer() {
        ClusterGraphWithBundlingVisualizer visualizer = new ClusterGraphWithBundlingVisualizer(this);

        visualizer.setGraph(Thesis.getInstance().getClusterGraph());
        visualizer.setLayout(initLayout());

        visualizer.setEdgeVisualizer(new PolarDendrogramBundledEdgeVisualizer(visualizer));
        visualizer.setSubGraphEdgeVizualizer(new PolarDendrogramEdgeVisualizer(visualizer));

//        visualizer.setEdgeVisualizer(new PolarDendrogramEdgeVisualizer(visualizer, Color.WHITE));
        //      visualizer.setSubGraphEdgeVizualizer(new PolarDendrogramEdgeVisualizer(visualizer, Color.YELLOW));


        visualizer.setVertexVisualizer(new CircleVertexVisualizer(visualizer, Color.RED));
        visualizer.setSubGraphVertexVizualizer(new CircleVertexVisualizer(visualizer, Color.YELLOW));


        return visualizer;
    }

    @Override
    public void notifyObserver(Subject subject, Object params) {
        visualizer.setSubGraph(((Extractor) params).getClusterSubGraph());
        visualizer.drawSubgraph();

        redraw();
    }
}