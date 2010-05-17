package se.lnu.thesis.paint.edge;

import se.lnu.thesis.paint.GraphVisualizer;
import se.lnu.thesis.paint.GraphWithSubgraphVisualizer;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.03.2010
 * Time: 0:46:41
 * <p/>
 * Simple implementation of the edge visualisation as line
 */
public class PolarDendrogramBundledEdgeVisualizer extends PolarDendrogramEdgeVisualizer {

    public PolarDendrogramBundledEdgeVisualizer(GraphVisualizer visualizer) {
        super(visualizer);
    }


    protected void drawShape(Object edge, Object o) {

        Object sourceNode = source(edge);
        Object destNode = dest(edge);

        if (GraphUtils.getInstance().isRoot(getVisualizer().getGraph(), sourceNode) || GraphUtils.getInstance().isLeaf(getVisualizer().getGraph(), destNode)) {
            //     drawArc(sourceNode, destNode);

            bundle(edge);
        } else {
            Point2D dummyNode = layout.getDummyNode(sourceNode, destNode);

            drawArc(sourceNode, destNode);
            drawLine(dummyNode, p(destNode));
        }
    }

    protected void bundle(Object edge) {

        //Point2D start = layout.getDummyNode(source(edge), dest(edge)); // start from dummy node
        Point2D start = p(source(edge)); // start draw spline directly from start node
        Point2D end = p(dest(edge));

        Point2D anchor1 = new Point2D.Float();
        Point2D anchor2 = new Point2D.Float();


        computeAnchores(dest(edge), anchor1, anchor2);

/*

        getVisualizer().getApplet().noFill();
        getVisualizer().getApplet().beginShape();

        getVisualizer().getApplet().vertex(new Float(start.getX()), new Float(start.getY()));

        getVisualizer().getApplet().bezierVertex(new Float(anchor1.getX()), new Float(anchor1.getY()),
                new Float(anchor2.getX()), new Float(anchor2.getY()),

                new Float(end.getX()), new Float(end.getY()));
        getVisualizer().getApplet().endShape();
*/

    }

    private void computeAnchores(Object leaf, Point2D anchor1, Point2D anchor2) {
        Object bundlingCenterLeaf = getBundlingCenterLeaf(leaf);

        anchor1.setLocation(p(getVisualizer().getGraph().getPredecessors(bundlingCenterLeaf).iterator().next()));
        anchor2.setLocation(p(bundlingCenterLeaf));
    }

    protected Object getBundlingCenterLeaf(Object leaf) {
        int currentLeafIndex = layout.getLeafs().indexOf(leaf);

        int bundleGroupStartIndex = currentLeafIndex;
        int bundleGroupEndIndex = currentLeafIndex;

        for (; bundleGroupStartIndex > 0; bundleGroupStartIndex--) {
            //  if (!((GraphWithSubgraphVisualizer)getVisualizer()).getSubGraph().containsVertex(getLeafByIndex(bundleGroupStartIndex))) {
            if (((GraphWithSubgraphVisualizer) getVisualizer()).getSubGraph().containsVertex(getLeafByIndex(bundleGroupStartIndex))) {
                break;
            }
        }

        for (; bundleGroupEndIndex < layout.getLeafs().size(); bundleGroupEndIndex++) {
            //  if (!((GraphWithSubgraphVisualizer)getVisualizer()).getSubGraph().containsVertex(getLeafByIndex(bundleGroupEndIndex))) {
            if (((GraphWithSubgraphVisualizer) getVisualizer()).getSubGraph().containsVertex(getLeafByIndex(bundleGroupEndIndex))) {
                break;
            }
        }

        int bundleCenterIndex = bundleGroupStartIndex + ((bundleGroupEndIndex - bundleGroupStartIndex) / 2);

        return getLeafByIndex(bundleCenterIndex);
    }

    private Object getLeafByIndex(int index) {
        if (index < 0) {
            index = 0;
        }
        if (index > layout.getLeafs().size()) {
            index = layout.getLeafs().size();
        }

        return layout.getLeafs().get(index);
    }

}