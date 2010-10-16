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
public class LevelElement extends DimensionalContainer {


    public static LevelElement init(Object o, Collection<Object> objects) {
        LevelElement result = new LevelElement();

        result.setObject(o);

        result.setId(IdUtils.next());

        result.preview = new DimensionalContainer();

        result.objects = ImmutableSet.copyOf(objects);

        return result;
    }

    private DimensionalContainer preview;


    public LevelElement() {
    }


    @Override
    public void draw(GLAutoDrawable drawable) {
        if (isDrawed()) {
            preview.drawContent(drawable);
        }
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
