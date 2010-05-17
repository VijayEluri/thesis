package se.lnu.thesis.paint;


import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 12.04.2010
 * Time: 1:18:20
 * <p/>
 * This class handles all drawing routine.
 */
public abstract class Visualizer {

    public static final float DEFAULT_ZOOM_STEP = 0.05f;
    public static final float DEFAULT_MOVE_STEP = 5.0f;
    public static final Color DEFAULT_BACKGROUND = Color.BLACK;

    private float zoom = 1.0f;

    private float sceneCenterX = 0.0f;
    private float sceneCenterY = 0.0f;

    private float zoomStep = DEFAULT_ZOOM_STEP;
    private float moveStep = DEFAULT_MOVE_STEP;

    private Color background = DEFAULT_BACKGROUND;

    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_LINE_SMOOTH);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(background.getRed(), background.getGreen(), background.getBlue(), 1.0f); // background color

        gl.glPushMatrix();

        gl.glScalef(1.0f, 1.0f, zoom);
        gl.glTranslatef(sceneCenterX, sceneCenterY, 0.0f);

        drawScene(drawable);

        gl.glPopMatrix();

        drawable.swapBuffers();
    }

    protected abstract void drawScene(GLAutoDrawable drawable);

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void zoomOut() {
        zoom -= zoomStep;
    }

    public void zoomIn() {
        zoom += zoomStep;
    }

    public void up() {
        sceneCenterY += moveStep;
    }

    public void down() {
        sceneCenterY -= moveStep;
    }

    public void left() {
        sceneCenterX -= moveStep;
    }

    public void right() {
        sceneCenterX += moveStep;
    }

    public void move(float stepX, float stepY) {
        sceneCenterX += stepX;
        sceneCenterY += stepY;
    }

    public float getZoomStep() {
        return zoomStep;
    }

    public void setZoomStep(float zoomStep) {
        this.zoomStep = zoomStep;
    }

    public float getMoveStep() {
        return moveStep;
    }

    public void setMoveStep(float moveStep) {
        this.moveStep = moveStep;
    }
}
