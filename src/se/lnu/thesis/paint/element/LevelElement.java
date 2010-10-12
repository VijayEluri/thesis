package se.lnu.thesis.paint.element;

import com.google.common.collect.ImmutableSet;
import se.lnu.thesis.paint.visualizer.LevelVisualizer;
import se.lnu.thesis.utils.IdUtils;

import javax.media.opengl.GLAutoDrawable;
import java.awt.geom.Point2D;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 0:12:17
 */
public class LevelElement extends AbstractCompositeElement {


    public static LevelElement init(Object o, Collection<Object> objects) {
        LevelElement result = new LevelElement();

        result.setObject(o);

        int i = IdUtils.next();
        result.setId(i);

        result.preview.setId(i);
        result.preview.setVisualizer(new LevelVisualizer());


        result.content.setId(i);
        result.content.setVisualizer(new LevelVisualizer());

        result.objects = ImmutableSet.copyOf(objects);

        return result;
    }


    private Point2D previewDimension;
    private Point2D contentDimension;

    private CompositeElement preview = new GroupingElement();
    private CompositeElement content = new GroupingElement();


    public LevelElement() {
    }


    @Override
    public void draw(GLAutoDrawable drawable) {
        preview.drawContent(drawable);
    }

    @Override
    public void drawContent(GLAutoDrawable drawable) {
        content.drawContent(drawable);
    }

    public Point2D getPreviewDimension() {
        return previewDimension;
    }

    public void setPreviewDimension(double width, double height) {
        if (previewDimension == null) {
            previewDimension = new Point2D.Double();
        }

        previewDimension.setLocation(width, height);
    }

    public Point2D getContentDimension() {
        return contentDimension;
    }

    public void setContentDimension(double width, double height) {
        if (contentDimension == null) {
            contentDimension = new Point2D.Double();
        }

        contentDimension.setLocation(width, height);
    }

    public CompositeElement getPreview() {
        return preview;
    }

    public CompositeElement getContent() {
        return content;
    }

    @Override
    public void setHighlighted(Collection objects) {
        preview.setHighlighted(objects);
        content.setHighlighted(objects);
    }

    @Override
    public void resetHighlighting() {
        preview.resetHighlighting();
        content.resetHighlighting();
    }

}
