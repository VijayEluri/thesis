package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.layout.UniformDistributionLayout;
import se.lnu.thesis.paint.element.GroupingElement;
import se.lnu.thesis.utils.Utils;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:31:16
 */
public class Level implements Drawable {

    public static final Logger LOGGER = Logger.getLogger(Level.class);

    protected Color levelBackgroud = Color.WHITE;

    private GroupingElement root = null;

    private Graph goGraph;

    protected final Point2D levelPosition = new Point2D.Double(-1, 0.8);
    protected final Point2D levelDimension = new Point2D.Double(2, 2);

    protected final Point2D layoutPosition = new Point2D.Double(-0.8, 0.8);
    protected final Point2D layoutDimension = new Point2D.Double(1, 1);


    public Level() {

    }

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glColor3d(Utils.colorAsDouble(levelBackgroud.getRed()), Utils.colorAsDouble(levelBackgroud.getGreen()), Utils.colorAsDouble(levelBackgroud.getBlue()));
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2d(levelPosition.getX(), levelPosition.getY());
        gl.glVertex2d(levelPosition.getX() + levelDimension.getX(), levelPosition.getY());
        gl.glVertex2d(levelPosition.getX() + levelDimension.getX(), levelPosition.getY() - levelDimension.getY());
        gl.glVertex2d(levelPosition.getX(), levelPosition.getY() - levelDimension.getY());
        gl.glEnd();


        root.drawContent(drawable);
    }

    public void setRoot(GroupingElement root) {
        this.root = root;

        if (!root.isLayoutComputed()) {
            UniformDistributionLayout layout = new UniformDistributionLayout(goGraph);
            layout.setStart(layoutPosition);
            layout.setDimension(layoutDimension);

            layout.setObjects(root.getObjects());
            layout.setRoot(root);

            LOGGER.info("Computing level layout..");
            layout.compute();
            LOGGER.info("Done.");

            root.setLayoutComputed(true);
        }
    }

    public void setGraph(Graph graph) {
        goGraph = graph;
    }

    public Color getLevelBackgroud() {
        return levelBackgroud;
    }

    public void setLevelBackgroud(Color levelBackgroud) {
        this.levelBackgroud = levelBackgroud;
    }
}
