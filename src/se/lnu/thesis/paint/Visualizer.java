package se.lnu.thesis.paint;

import processing.core.PApplet;

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
    private PApplet applet;
    private float zoom = 1.0f;
    private float sceneCenterX = 0.0f;
    private float sceneCenterY = 0.0f;
    private float zoomStep = DEFAULT_ZOOM_STEP;
    private float moveStep = DEFAULT_MOVE_STEP;
    private Color background = DEFAULT_BACKGROUND;

    public void draw() {
        getApplet().pushMatrix();

        getApplet().scale(zoom);
        getApplet().translate(sceneCenterX, sceneCenterY);


        getApplet().smooth();
        getApplet().background(background.getRed(), background.getGreen(), background.getBlue());

        drawScene();

        getApplet().popMatrix();
    }

    protected abstract void drawScene();

    public PApplet getApplet() {
        return applet;
    }

    public void setApplet(PApplet applet) {
        this.applet = applet;
    }

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
