package layout;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import graph.GraphUtils;
import utils.Utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PolarDendrogramLayout<V, E> extends CircleLayout<V, E> {

    protected Map<V, Double> node_angle;
    protected Map<V, Integer> node_level;

    protected List<V> leafs;

    protected int graphHeight;

    protected float xCenter;
    protected float yCenter;


    public PolarDendrogramLayout(Graph graph) {
        super(graph);

        node_angle = new HashMap<V, Double>();
        node_level = new HashMap<V, Integer>();
    }


    public void initialize() {
        //main work here

        node_angle.clear();
        node_level.clear();

        V root = (V) GraphUtils.getInstance().findRoot(getGraph());
        if (root == null) {
            throw new IllegalArgumentException("No root node! Cannt draw unrooted graph..");
        }

        computeLeafAngles(root);

        graphHeight = GraphUtils.getInstance().computeLevels(getGraph(), node_level); // TODO delete it when use Tree insteed of Graph

        computeAngles(root);

        computePositions();

        setRootCoordinate(root);

    }

    private void computeLeafAngles(V root) {

        if (leafs == null) {
            leafs = new LinkedList<V>();
        } else {
            leafs.clear();
        }

        /*
            TODO maybe use this way without dfs?
            for (V node : getGraph().getVertices()) {
         */

        List<V> result = GraphUtils.getInstance().dfsNodes(getGraph(), root);
        for (V node : result) {
            if (getGraph().outDegree(node) == 0) { // is it leaf?
                leafs.add(node);
            } else {
                transform(node).setLocation(0, 0);
            }
        }


        double step = 360.0 / leafs.size();

        for (int i = 0; i < leafs.size(); i++) {
            V leaf = leafs.get(i);

            double angle = step * i;

            node_angle.put(leaf, angle);
        }


    }

    protected double computeAngles(V node) {
        if (node_angle.containsKey(node)) {
            return node_angle.get(node);
        } else {

            double min = 360.0;
            double max = 0.0;

            double angle;
            for (V child : getGraph().getSuccessors(node)) {
                angle = computeAngles(child);

                if (angle > max) {
                    max = angle;
                }

                if (angle < min) {
                    min = angle;
                }
            }

            angle = (max + min) / 2;
            node_angle.put(node, angle);

            return angle;
        }

    }


    protected void computePositions() {
        for (V node : getGraph().getVertices()) {
            setNodeCoordinate(node);
        }

    }


    private void setNodeCoordinate(V node) {

        double angle = node_angle.get(node);
        double radius = getNodeRadius(node);

        double xCenter = getSize().getWidth() / 2;
        double yCenter = getSize().getHeight() / 2;

        double x = Math.cos(Utils.inRadians(angle)) * radius + xCenter;
        double y = Math.sin(Utils.inRadians(angle)) * radius + yCenter;

        transform(node).setLocation(x, y);

        System.out.println(node + " " + angle + "; " + x + ", " + y); // TODO add logger
    }

    private void setRootCoordinate(V root) {
        transform(root).setLocation(xCenter, yCenter);
    }

    protected double getNodeRadius(V node) {
        if (getGraph().outDegree(node) == 0) {
            return getRadius();
        } else {
            return getRadius() / graphHeight * node_level.get(node);
        }
    }

    public int getGraphHeight() {
        return graphHeight;
    }

    public Map<V, Double> getNode_angle() {
        return node_angle;
    }

    public void setNode_angle(Map<V, Double> node_angle) {
        this.node_angle = node_angle;
    }

    public Map<V, Integer> getNode_level() {
        return node_level;
    }

    public void setNode_level(Map<V, Integer> node_level) {
        this.node_level = node_level;
    }

    public List<V> getLeafs() {
        return leafs;
    }

    public void setLeafs(List<V> leafs) {
        this.leafs = leafs;
    }

    public float getXCenter() {
        return xCenter;
    }

    public void setXCenter(float xCenter) {
        this.xCenter = xCenter;
    }

    public float getYCenter() {
        return yCenter;
    }

    public void setYCenter(float yCenter) {
        this.yCenter = yCenter;
    }
}