package se.lnu.thesis.paint;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.layout.UniformDistributionLayout;
import se.lnu.thesis.paint.element.GroupElement;
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

    private GroupElement root = null;

    private Graph goGraph;

    protected final Point2D position = new Point2D.Double(-0.8, 0.8);
    protected final Point2D dimension = new Point2D.Double(1.6, 1.6);
    protected final Point2D border = new Point2D.Double(0.05, 0.05);

    public Level() {

    }

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glColor3d(Utils.colorAsDouble(levelBackgroud.getRed()), Utils.colorAsDouble(levelBackgroud.getGreen()), Utils.colorAsDouble(levelBackgroud.getBlue()));
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2d(position.getX(), position.getY());
        gl.glVertex2d(position.getX() + dimension.getX(), position.getY());
        gl.glVertex2d(position.getX() + dimension.getX(), position.getY() - dimension.getY());
        gl.glVertex2d(position.getX(), position.getY() - dimension.getY());
        gl.glEnd();


        root.drawElements(drawable);
    }

    public void setRoot(GroupElement root) {
        this.root = root;

        if (!root.isLayoutComputed()) {
            UniformDistributionLayout layout = new UniformDistributionLayout(goGraph);
            layout.setStart(new Point2D.Double(position.getX() + border.getX(), position.getY() - border.getY()));
            layout.setDimension(dimension.getX() - border.getX() * 2, dimension.getY() - border.getY() * 2);

            layout.setNodes(root.getNodes());
            layout.setRoot(root);

            LOGGER.info("Computing level layout..");
            layout.compute();
            LOGGER.info("Done.");

            root.setIsLayoutComputed(true);
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
