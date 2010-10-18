package se.lnu.thesis.paint;


import se.lnu.thesis.Scene;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.layout.RectangularSpiralLayout;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;
import se.lnu.thesis.paint.element.DimensionalContainer;
import se.lnu.thesis.paint.element.Element;
import se.lnu.thesis.paint.element.ElementType;
import se.lnu.thesis.paint.element.GroupingElement;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class ClusterVisualizer extends GraphVisualizer implements Observer {

    private Lens lens;

    public void init() {
        LOGGER.info("Initializing..");

        root = DimensionalContainer.init("Cluster");

        RectangularSpiralLayout layout = new RectangularSpiralLayout(graph, root);
        layout.compute();

        lens = new Lens();
        lens.setGraph(graph);

        LOGGER.info("Done.");
    }

    @Override
    protected void select(Element element) {
        super.select(element);

        if (element != null && selectedElement.getType() == ElementType.COMPOSITE) {
            GroupingElement groupingElement = (GroupingElement) selectedElement;
            lens.setRoot(groupingElement);

//            Scene.getInstance().getMainWindow()

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

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1.0f); // background color

        if (root != null) {
            if (vertexState == State.SELECTING) {
                selectElement(drawable);
            }

            root.drawContent(drawable);

            if (vertexState == State.SELECTED && selectedElement.getType() == ElementType.COMPOSITE) {
                lens.draw(drawable);
            }
        }

        drawable.swapBuffers();
    }

    public void notifyObserver(Subject subject, Object params) {
        Extractor extractor = (Extractor) params;

        this.setSubGraph(extractor.getClusterSubGraph());

        Scene.getInstance().getMainWindow().repaint();
    }

}
