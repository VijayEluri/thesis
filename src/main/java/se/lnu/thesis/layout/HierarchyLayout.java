package se.lnu.thesis.layout;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.element.*;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.GraphUtils;

import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.10.2010
 * Time: 15:10:52
 */
public class HierarchyLayout extends AbstractLayout {

    protected int levelCount;
    protected Multimap levels;

    protected UniformDistributionLayout levelLayout;
    protected UniformDistributionLayout levelPreviewLayout;



    public HierarchyLayout(MyGraph graph, Container root) {
        super(graph, root);
    }

    public HierarchyLayout() {
        super();
    }

    public void compute() {
        checkArguments();

        initLevelLayout();
        initPreviewLevelLayout();

        computeGeneOntologyLevels();

        Point2D d = ((GOGraphContainer) root).getDimension(); // graph container dimension
        Point2D p = root.getPosition(); // graph container position

        Point2D levelDimension = new Point2D.Double(d.getX(), d.getY() / 3);
        Point2D levelPosition = new Point2D.Double(p.getX(), p.getY() - levelDimension.getY());


        Point2D previewDimension = new Point2D.Double(d.getX(), d.getY() / levelCount);

        for (int i = 0; i < levelCount; i++) {
            Point2D previewPosition = new Point2D.Double(p.getX(), p.getY() - previewDimension.getY() * i);

            Level level = Level.init(i, levels.get(i));

            // compute elements positions for level preview
            computePositions(level.getPreview(), level.getObjects(), levelPreviewLayout, previewDimension, previewPosition);

            // compute elements positions for level
            computePositions(level, level.getObjects(), levelLayout, levelDimension, levelPosition);

            root.addElement(level);
        }

        computeEdges();

        root.setLayoutComputed(true);
    }

    /**
     *      Compute all Gene Ontology edges
     */
    protected void computeEdges() {

        // computeInnerLevelEdges();

        computePreviewEdges();

    }

    /**
     *      Compute all edges for while graph based on preview element positions
     */
    protected void computePreviewEdges() {
        for (Object o : getGraph().getEdges()) {

            Object source = getGraph().getSource(o);
            Object dest = getGraph().getDest(o);

            Element sourceElement = findElementInLevelPreviw(source);
            Element destElement = findElementInLevelPreviw(dest);

            if (sourceElement != null && destElement != null) {
                // create edge which will be visible only during subgraph highlight
                EdgeElement edgeElement = GOEdgeElement.init(o, source, dest, sourceElement.getPosition(), destElement.getPosition(), ElementVisualizerFactory.getInstance().getThinLineEdgeVisializer());

                getRoot().addElement(edgeElement);
            }

        }

    }

    public Element findElementInLevelPreviw(Object o) {
        Element result = null;

        if (root != null) {
            for (Element element: getRoot()) {
                if (element instanceof Level) { // TODO provide a new element type
                    Level level = (Level) element;

                    result = level.getPreview().getElementByObject(o);

                    if (result != null) {
                        return result;
                    }

                }
            }

        }

        return null;
    }

    protected void initPreviewLevelLayout() {
        LevelLayout layout = new LevelLayout();
        layout.setVertexVisualizer(ElementVisualizerFactory.getInstance().getGONodePointVisualizer());
        layout.setLeafVisualizer(ElementVisualizerFactory.getInstance().getGOLeafPointVisualizer());

        levelPreviewLayout = new LevelBarLayout(graph, layout);
    }

    protected void initLevelLayout() {
        levelLayout = new LevelBarLayout(graph, new LevelLayout());
    }

    protected void computePositions(DimensionalContainer root, Collection objects, UniformDistributionLayout layout, Point2D dimension, Point2D position) {
        root.setPosition(position);
        root.setDimension(dimension);

        layout.setRoot(root);

        layout.setObjects(objects);

        layout.setStart(position);
        layout.setDimension(dimension);

        layout.compute();
    }

    protected void computeGeneOntologyLevels() {
        levels = TreeMultimap.create();
        levelCount = GraphUtils.computeLevels(graph, levels);
    }

    public int getLevelCount() {
        return levelCount;
    }
}
