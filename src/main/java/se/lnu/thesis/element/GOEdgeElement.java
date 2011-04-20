package se.lnu.thesis.element;


import se.lnu.thesis.paint.visualizer.ElementVisualizer;

import java.awt.geom.Point2D;

public class GOEdgeElement extends EdgeElement {

    public static GOEdgeElement init(Object o, Object source, Object target, Point2D start, Point2D end, ElementVisualizer visualizer) {
        GOEdgeElement result = new GOEdgeElement();

        result.setObject(o);

        result.setFrom(source);
        result.setTo(target);

        result.setStartPosition(start);
        result.setEndPosition(end);

        result.setVisualizer(visualizer);

        result.setHighlighted(false); // do not highlight in default state
        result.setShowHighlightedEdge(true); // should ne drawn if highlighted

        return result;
    }

    protected boolean showHighlightedEdge = true;

    /**
     *
     *  Gene Ontology edge should be drawn only when highlighted.
     *
     * @param b Set to highlighting to <code>true</code> or <code>false</code> and drawing at same time to <code>true</code> or <code>false</code>
     */
    @Override
    public void setHighlighted(boolean b) {
        super.setHighlighted(b);
        if (isShowHighlightedEdge()) {
            setDrawn(b);
        }
    }

    /**
     *      Check if edge should be drawn in highlighted mode or never drawn
     * @return <code>True</code> or <code>False</code>
     */
    public boolean isShowHighlightedEdge() {
        return showHighlightedEdge;
    }

    /**
     *      Set if edge should be drawn if in highlighted mode.
     * @param showHighlightedEdge <code>True</code> or <code>False</code>
     */
    public void setShowHighlightedEdge(boolean showHighlightedEdge) {
        this.showHighlightedEdge = showHighlightedEdge;

        if (!isShowHighlightedEdge()) {
            setDrawn(false);
        }

        if (isShowHighlightedEdge() && isHighlighted()) {
            setDrawn(true);
        }
    }
}
