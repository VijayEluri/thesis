package se.lnu.thesis.gui;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import processing.core.PApplet;
import se.lnu.thesis.Thesis;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.paint.GOGraphVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;
import se.lnu.thesis.paint.edge.LineEdgeVisualizer;
import se.lnu.thesis.paint.vertex.CircleVertexVisualizer;
import se.lnu.thesis.utils.myobserver.Observer;
import se.lnu.thesis.utils.myobserver.Subject;

import java.awt.event.KeyEvent;

public class GOApplet extends PApplet implements Observer {

    private static final int DEFAULT_APPLET_HEGIHT = 800;
    private static final int DEFAULT_APPLET_WIDTH = 800;

    protected GraphWithSubgraphVisualizer visualizer;

    @Override
    public void setup() {
        size(DEFAULT_APPLET_HEGIHT, DEFAULT_APPLET_WIDTH);
        Thesis.getInstance().getSelectionDialog().registerObserver(this);

        visualizer = initVisualizer();
    }

    protected AbstractLayout initLayout() {
        AbstractLayout result = new DAGLayout(Thesis.getInstance().getGOGraph());

        result.setSize(getSize());
        result.initialize();

        return result;
    }

    protected GraphWithSubgraphVisualizer initVisualizer() {
        GraphWithSubgraphVisualizer visualizer = new GOGraphVisualizer(this);

        visualizer.setGraph(Thesis.getInstance().getGOGraph());
        visualizer.setLayout(initLayout());

        LineEdgeVisualizer edgeVisualizer = new LineEdgeVisualizer(visualizer);
        visualizer.setEdgeVisualizer(edgeVisualizer);
        visualizer.setSubGraphEdgeVizualizer(edgeVisualizer);

        CircleVertexVisualizer vertexVisualizer = new CircleVertexVisualizer(visualizer);
        visualizer.setVertexVisualizer(vertexVisualizer);
        visualizer.setSubGraphVertexVizualizer(vertexVisualizer);

        return visualizer;
    }

    @Override
    public void draw() {
        visualizer.draw();
    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (key == CODED) {
            System.out.println(keyCode);// TODO use logger

            if (keyCode == ALT) {
                visualizer.zoomOut();
            }
            if (keyCode == SHIFT) {
                visualizer.zoomIn();
            }

            if (keyCode == UP) {
                visualizer.down();
            }
            if (keyCode == DOWN) {
                visualizer.up();
            }

            if (keyCode == LEFT) {
                visualizer.left();
            }
            if (keyCode == RIGHT) {
                visualizer.right();
            }

            redraw();
        } else {
            System.out.println("No key code!");// TODO use logger
        }
    }

    public void notifyObserver(Subject subject, Object params) {
        visualizer.setSubGraph(((Extractor) params).getGoSubGraph());
        redraw();
    }
}