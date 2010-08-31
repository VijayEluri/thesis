package se.lnu.thesis.paint;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import se.lnu.thesis.layout.LevelPreviewLayout;
import se.lnu.thesis.layout.UniformDistributionLayout;
import se.lnu.thesis.paint.element.AbstractGraphElement;
import se.lnu.thesis.paint.element.GraphElementType;
import se.lnu.thesis.paint.element.GroupElement;
import se.lnu.thesis.paint.element.LevelElement;
import se.lnu.thesis.utils.GraphUtils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 23:43:07
 */
public class GOVisualizer extends GraphVisualizer {

    public static final double border = 0.02;

    protected Color background = Color.BLACK;
    protected Color levelColor = Color.WHITE;


    private Level level;


    public void init() {
        LOGGER.info("Initializing..");

        root = new GroupElement();
        root.setObject("Gene Ontology");

        Multimap levels = TreeMultimap.create();
        int levelCount = GraphUtils.computeLevels(graph, levels);


        double d = 2.0 - (border * 2);
        Point2D.Double dimension = new Point2D.Double(d, d / levelCount);

        UniformDistributionLayout levelPreviewLayout = new LevelPreviewLayout(graph);

        for (int i = 0; i < levelCount; i++) {
            Point2D.Double position = new Point2D.Double(-1 + border, 1 - border - (dimension.getY() * i));

            LevelElement level = LevelElement.init(i, position, dimension, levels.get(i));

            levelPreviewLayout.setStart(level.getPosition());
            levelPreviewLayout.setDimension(level.getDimension());

            levelPreviewLayout.setRoot(level.getPreview());
            levelPreviewLayout.setNodes(level.getNodes());
            levelPreviewLayout.compute();

            level.getPreview().setIsLayoutComputed(true);


            root.addElement(level);
        }


        level = new Level();
        level.setGraph(graph);

        LOGGER.info("Done.");
    }

    @Override
    protected void select(AbstractGraphElement element) {
        super.select(element);

        if (element != null && selectedElement.getType() == GraphElementType.GROUP) {
            GroupElement groupElement = (GroupElement) selectedElement;
            level.setRoot(groupElement);

            if (subGraph != null) {
                groupElement.setSubgraphHighlighting(subGraph.getVertices());
            }
        }
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1.0f); // background color

        if (vertexState == State.SELECTING) {
            selectElement(drawable);
        }

        root.drawElements(drawable);

        if (vertexState == State.SELECTED && selectedElement.getType() == GraphElementType.GROUP) {
            level.draw(drawable);
        }

        drawable.swapBuffers();
    }

}
