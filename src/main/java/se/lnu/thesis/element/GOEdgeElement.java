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

        result.setHighlighted(false); // do not draw and highlight in default state

        return result;
    }

    /**
     *
     *  Gene Ontology edge should be drawn only when highlighted.
     *
     * @param b Set to highlighting to <code>true</code> or <code>false</code> and drawing at same time to <code>true</code> or <code>false</code>
     */
    @Override
    public void setHighlighted(boolean b) {
        super.setHighlighted(b);
        setDrawn(b);
    }


}
