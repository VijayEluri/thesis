package se.lnu.thesis.paint.visualizer;


import se.lnu.thesis.element.AbstractGraphElement;
import se.lnu.thesis.element.GraphElementType;
import se.lnu.thesis.element.GroupElement;
import se.lnu.thesis.layout.RectangularSpiralLayout;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


public class ClusterVisualizer extends GraphVisualizer {

    private Lens lens;

    public void init() {
        LOGGER.info("Initializing..");

        root = new GroupElement();

        RectangularSpiralLayout layout = new RectangularSpiralLayout(graph, root);
        layout.compute();

        lens = new Lens();
        lens.setGraph(graph);

        LOGGER.info("Done.");
    }

    @Override
    protected void select(AbstractGraphElement element) {
        super.select(element);

        if (element != null && selectedElement.getType() == GraphElementType.GROUP) {
            GroupElement groupElement = (GroupElement) selectedElement;
            lens.setRoot(groupElement);

            groupElement.setSubgraphHighlighting(subGraph.getVertices());
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
            lens.draw(drawable);
        }

        drawable.swapBuffers();
    }
}
