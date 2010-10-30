package se.lnu.thesis.paint;

import se.lnu.thesis.Scene;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.layout.HierarchyLayout;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.element.*;
import se.lnu.thesis.paint.element.Level;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 19.08.2010
 * Time: 23:43:07
 */
public class GOController extends GraphController {


    private se.lnu.thesis.paint.Level level;


    public void init() {
        LOGGER.info("Initializing..");

        root = DimensionalContainer.init("Gene Ontology");

        HierarchyLayout layout = new HierarchyLayout(graph, root);
        layout.compute();

        level = new se.lnu.thesis.paint.Level();
        level.setGraph(graph);

        LOGGER.info("Done.");
    }

    @Override
    protected void select(Element element) {
        super.select(element);

        if (element != null && selectedElement.getType() == ElementType.CONTAINER) {
            GroupingElement groupingElement = (GroupingElement) selectedElement;
            level.setRoot(groupingElement);

            if (subGraph != null) {
                groupingElement.setHighlighted(subGraph.getVertices());
            }
        }
    }

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);


        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1.0f); // background color

        if (root != null) {
            if (vertexState == State.SELECTING) {
                select(drawable);
            }

            root.drawContent(drawable);

            if (vertexState == State.SELECTED && selectedElement.getType() == ElementType.CONTAINER) {
                level.draw(drawable);
            }
        }

        drawable.swapBuffers();
    }

    public void notifyObserver(Subject subject, Object params) {
        Extractor extractor = (Extractor) params;

        this.setSubGraph(extractor.getGoSubGraph());

        if (extractor.getSelectedNode() != null) {    // TODO move this shit to setSubGraph
            for (Iterator<Element> i = this.root.getElements(); i.hasNext();) {
                Level Level = (Level) i.next();
                Element element = Level.getPreview().getElementByObject(extractor.getSelectedNode());
                if (element != null) {
                    selectedElement = element;
                    selectedElement.setSelected(true);
                    break;
                }
            }
        } else {
            if (selectedElement != null) {
                selectedElement.setSelected(false);
                selectedElement = null;
            }
        }


        Scene.getInstance().getMainWindow().repaint();
    }

}
