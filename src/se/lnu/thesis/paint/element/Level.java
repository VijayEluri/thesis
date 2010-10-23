package se.lnu.thesis.paint.element;

import com.google.common.collect.ImmutableSet;
import se.lnu.thesis.paint.visualizer.vertex.LevelPreviewVisualizer;
import se.lnu.thesis.utils.IdUtils;

import javax.media.opengl.GLAutoDrawable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:12:17
 */
public class Level extends DimensionalContainer {

    public static Level init(Object o, Collection<Object> objects) {
        Level result = new Level();

        result.setObject(o);
        result.setId(IdUtils.next());

        result.objects = ImmutableSet.copyOf(objects);

        result.preview = new LevelPreview();
        result.preview.setObject(o);
        result.preview.setId(result.getId());
        result.preview.setVisualizer(new LevelPreviewVisualizer());

        return result;
    }

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
        super.draw(drawable);
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
}
