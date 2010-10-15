package se.lnu.thesis.paint.element;

import com.google.common.collect.ImmutableSet;
import se.lnu.thesis.utils.IdUtils;

import javax.media.opengl.GLAutoDrawable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:12:17
 */
public class LevelElement extends DimensionalCompositeElement {


    public static LevelElement init(Object o, Collection<Object> objects) {
        LevelElement result = new LevelElement();

        result.setObject(o);

        int i = IdUtils.next();
        result.setId(i);

/*
        LevelVisualizer visualizer = new LevelVisualizer();  // TODO think about it
        result.setVisualizer(visualizer);
*/


        result.preview = new DimensionalCompositeElement();
        result.preview.setId(i);
//        result.preview.setVisualizer(visualizer);

        result.objects = ImmutableSet.copyOf(objects);

        return result;
    }

    private DimensionalCompositeElement preview;


    public LevelElement() {
    }


    @Override
    public void draw(GLAutoDrawable drawable) {
        preview.drawContent(drawable);
    }

    @Override
    public void drawContent(GLAutoDrawable drawable) {
        super.draw(drawable);
    }

    public DimensionalCompositeElement getPreview() {
        return preview;
    }

    @Override
    public void setHighlighted(Collection objects) {
        preview.setHighlighted(objects);
        super.setHighlighted(objects);
    }

    @Override
    public void resetHighlighting() {
        preview.resetHighlighting();
        super.resetHighlighting();
    }
}
