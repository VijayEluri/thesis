package se.lnu.thesis.paint.element;

import javax.media.opengl.GLAutoDrawable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.10.2010
 * Time: 16:03:28
 */
public class PreviewableCompositeElement extends AbstractCompositeElement {

    protected DimensionalCompositeElement preview = new DimensionalCompositeElement();

    public DimensionalCompositeElement getPreview() {
        return preview;
    }

    public void setPreview(DimensionalCompositeElement preview) {
        this.preview = preview;
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        preview.draw(drawable);
    }

    @Override
    public void drawContent(GLAutoDrawable drawable) {
        super.draw(drawable);
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
