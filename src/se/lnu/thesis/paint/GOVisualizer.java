package se.lnu.thesis.paint;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import se.lnu.thesis.layout.LevelPreviewLayout;
import se.lnu.thesis.layout.UniformDistributionLayout;
import se.lnu.thesis.paint.element.Element;
import se.lnu.thesis.paint.element.ElementType;
import se.lnu.thesis.paint.element.GroupingElement;
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

        root = new GroupingElement();
        root.setObject("Gene Ontology");

        Multimap levels = TreeMultimap.create();
        int levelCount = GraphUtils.computeLevels(graph, levels);


        Point2D.Double dimension = new Point2D.Double(2.0 - (border * 2), (2 - border - (border * levelCount)) / levelCount);

        UniformDistributionLayout levelPreviewLayout = new LevelPreviewLayout(graph);

        for (int i = 0; i < levelCount; i++) {
            Point2D.Double position;

            position = new Point2D.Double(-1 + border, 1 - dimension.getY() * i - border * (i + 1));

            LevelElement level = LevelElement.init(i, levels.get(i));
            level.getPreview().setPosition(position);
            level.setPreviewDimension(dimension.getX(), dimension.getY());

            levelPreviewLayout.setStart(level.getPreview().getPosition());
            levelPreviewLayout.setDimension(level.getPreviewDimension());
            levelPreviewLayout.setRoot(level.getPreview());
            levelPreviewLayout.setNodes(level.getObjects());
            levelPreviewLayout.compute();

            level.getPreview().setLayoutComputed(true);

            root.addElement(level);
        }


        level = new Level();
        level.setGraph(graph);

        LOGGER.info("Done.");
    }

    @Override
    protected void select(Element element) {
        super.select(element);

        if (element != null && selectedElement.getType() == ElementType.COMPOSITE) {
            GroupingElement groupingElement = (GroupingElement) selectedElement;
            level.setRoot(groupingElement);

            if (subGraph != null) {
                groupingElement.setHighlighted(subGraph.getVertices());
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

        root.drawContent(drawable);

        if (vertexState == State.SELECTED && selectedElement.getType() == ElementType.COMPOSITE) {
            level.draw(drawable);
        }

        drawable.swapBuffers();
    }

}
