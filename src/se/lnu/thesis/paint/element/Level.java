package se.lnu.thesis.paint.element;

import com.google.common.collect.ImmutableSet;
import se.lnu.thesis.paint.visualizer.ElementVisualizer;
import se.lnu.thesis.paint.visualizer.ElementVisualizerFactory;
import se.lnu.thesis.utils.IdUtils;

import javax.media.opengl.GLAutoDrawable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:12:17
 */
public class Level extends DimensionalContainer implements Visualizable {

    public static Level init(Object o, Collection<Object> objects) {
        Level result = new Level();

        result.setObject(o);
        result.setId(IdUtils.next());

        result.objects = ImmutableSet.copyOf(objects);

        result.visualizer = ElementVisualizerFactory.getInstance().getLevelVisualizer();

        result.preview = new LevelPreview();
        result.preview.setObject(o);
        result.preview.setId(result.getId());
        result.preview.setVisualizer(ElementVisualizerFactory.getInstance().getLevelPreviewVisualizer());


        return result;
    }

    private ElementVisualizer visualizer;
    private LevelPreview preview;

    protected Level() {
        super();
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        preview.draw(drawable);
    }

    @Override
    public void drawContent(GLAutoDrawable drawable) {
        if (visualizer != null) {
            visualizer.draw(drawable, this);
        }
    }


    public DimensionalContainer getPreview() {
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

    @Override
    public void setFocused(boolean focused) {
        preview.setFocused(focused);
        super.setFocused(focused);
    }

    public ElementVisualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(ElementVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    @Override
    public String toString() {
        return getObject().toString();
    }
}
