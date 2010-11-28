package se.lnu.thesis.paint.element;

import java.awt.geom.Point2D;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 27.11.2010
 * Time: 21:43:16
 * <p/>
 * Container for cluster graph
 */
public class ClusterGraphContainer extends GraphContainer {

    public static ClusterGraphContainer init() {
        ClusterGraphContainer result = new ClusterGraphContainer();

        result.setObject("Cluster graph");

        result.setPosition(new Point2D.Double(-1, 1));
        result.setDimension(new Point2D.Double(2, 2));

        result.objects = new HashSet<Object>();

        return result;
    }


}
