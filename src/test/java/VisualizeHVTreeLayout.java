import edu.uci.ics.jung.graph.Graph;
import se.lnu.thesis.element.DimensionalContainer;
import se.lnu.thesis.layout.HVTreeLayout;
import se.lnu.thesis.utils.GraphMaker;
import se.lnu.thesis.utils.GraphUtils;

import javax.swing.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 26.03.11
 * Time: 21:58
 * <p/>
 * <p/>
 * Run some test methods to test how HVTreeLayout works on different sample graphs.
 */
public class VisualizeHVTreeLayout {

    public static void main(String[] args) {
        Graph tree = GraphMaker.createWideBinaryTree();
        Object root = GraphUtils.getRoot(tree);

        DimensionalContainer container = DimensionalContainer.init(root);


        HVTreeLayout layout = new HVTreeLayout(tree, container);
        layout.setStart(new Point2D.Double(100, 100));
        layout.setDistance(new Point2D.Double(20, -20));
        layout.compute();

        LayoutVisualizerFrame frame = new LayoutVisualizerFrame();
        frame.setContainer(container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

    }

}
