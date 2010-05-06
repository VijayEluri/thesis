package se.lnu.thesis.gui;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import org.apache.log4j.Logger;
import processing.core.PApplet;
import se.lnu.thesis.Thesis;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.paint.GOGraphVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;
import se.lnu.thesis.paint.edge.LineEdgeVisualizer;
import se.lnu.thesis.paint.vertex.CircleVertexVisualizer;
import se.lnu.thesis.utils.myobserver.Observer;
import se.lnu.thesis.utils.myobserver.Subject;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GOApplet extends PApplet implements Observer {

    public static final Logger LOGGER = Logger.getLogger(GOApplet.class);

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
        DAGLayout result = new DAGLayout(Thesis.getInstance().getGOGraph());

        result.setSize(getSize());

        return result;
    }

    protected GraphWithSubgraphVisualizer initVisualizer() {
        GraphWithSubgraphVisualizer visualizer = new GOGraphVisualizer(this);

        visualizer.setGraph(Thesis.getInstance().getGOGraph());
        visualizer.setLayout(initLayout());

        visualizer.setEdgeVisualizer(new LineEdgeVisualizer(visualizer, Color.WHITE));
        visualizer.setSubGraphEdgeVizualizer(new LineEdgeVisualizer(visualizer, Color.YELLOW));

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
            LOGGER.debug(keyCode);

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
            LOGGER.error("No key code!");
        }
    }

    public void notifyObserver(Subject subject, Object params) {
        visualizer.setSubGraph(((Extractor) params).getGoSubGraph());
        visualizer.drawSubgraph();

        redraw();
    }
}