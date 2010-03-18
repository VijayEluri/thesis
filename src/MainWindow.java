import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import io.GraphMLParser;
import io.JungYedHandler;
import layout.PolarDendrogramLayout;
import layout.RadialLayout;
import org.apache.commons.collections15.Transformer;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainWindow extends JFrame {

    public static Logger logger = Logger.getLogger(MainWindow.class);

    private static final String CAPTION = "Thesis";

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private static final String GRAPH_FILENAME = "RealClusterGraph.graphml";
    //private static final String GRAPH_FILENAME = "tree23.graphml";


    public MainWindow() {
        initGuiElements();
    }

    private void initGuiElements() {
        this.setTitle(CAPTION);

        this.setSize(WIDTH, HEIGHT);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // center

        this.getContentPane().add(creatJungVisualizer(createLayout()));

        this.setVisible(true);
    }

    private AbstractLayout createLayout() {
        return createPolarLayout();
    }

    private AbstractLayout createRadialLayout() {
        return new RadialLayout(loadGraph());
    }

    private PolarDendrogramLayout createPolarLayout() {
        PolarDendrogramLayout layout = new PolarDendrogramLayout(loadGraph());

        int mainWindowWidth = this.getWidth();
        int mainWindowHeight = this.getHeight();

        layout.setRadius(0.45 * (mainWindowHeight < mainWindowWidth ? mainWindowHeight : mainWindowWidth));

        layout.setXCenter(mainWindowWidth / 2);
        layout.setYCenter(mainWindowHeight / 2);

        layout.setSize(getSize());


        return layout;
    }

    private Graph loadGraph() {
        return (Graph) new GraphMLParser(new JungYedHandler()).load(new File(GRAPH_FILENAME)).get(0);
    }

    private JComponent creatJungVisualizer(AbstractLayout layout) {
        VisualizationViewer visualizer = new VisualizationViewer(layout);

        // Setup up a new vertex to paint transformer...
        visualizer.getRenderContext().setVertexFillPaintTransformer(new Transformer<String, Paint>() {
            public Paint transform(String id) {

                //String label = clusterIdLabels.get(id);
/*
                       if (subtreeLabels != null && subtreeLabels.contains(label)) {
                           return Color.GREEN;
                       } else {
                           return Color.RED;
                       }
*/
                //if (((Graph) getClusterGraph()).outDegree(id) == 0) {
                //   return Color.GREEN;
                //} else {
                return Color.RED;
                //}
            }
        });

        visualizer.getRenderContext().setVertexShapeTransformer(new Transformer<String, Shape>() {
            public Shape transform(String id) {
//                Graph clusterGraph = (Graph) Scene.instance.getClusterGraph();


                //              if (clusterGraph.outDegree(id) == 0) {
                //                return new Ellipse2D.Double(0,0,10,10);
                //          }

                return new Rectangle(0, 0, 5, 5);
            }
        });

        //       visualizer.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());

        // Create a graph mouse and add it to the visualization component
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        visualizer.setGraphMouse(gm);

        return new GraphZoomScrollPane(visualizer);
    }

}