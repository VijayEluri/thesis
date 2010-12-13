package se.lnu.thesis.utils;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 09.12.2010
 * Time: 18:48:28
 *
 *      Adapter class
 *
 */
public class MyColor {

    public static final float COLOR_COMPONENT_MAX_VALUE = 256.0f;

    private float red = 0.0f;
    private float green = 0.0f;
    private float blue = 0.0f;
    private float alfa = 0.0f;

    public MyColor() {

    }
    
    public MyColor(int r, int g, int b) {
        setRed(colorAsFloat(r));
        setGreen(colorAsFloat(g));
        setBlue(colorAsFloat(b));
    }

    public MyColor(int r, int g, int b, int alfa) {
        setRed(colorAsFloat(r));
        setGreen(colorAsFloat(g));
        setBlue(colorAsFloat(b));
        setAlfa(colorAsFloat(alfa));
    }

    public MyColor(Color color) {
        setColor(color);
    }

    public MyColor(float r, float g, float b) {
        setRed(r);
        setGreen(g);
        setBlue(b);
    }

    public MyColor(float r, float g, float b, float alfa) {
        setRed(r);
        setGreen(g);
        setBlue(b);
        setAlfa(alfa);
    }

    public void setColor(int r, int g, int b) {
        setRed(colorAsFloat(r));
        setGreen(colorAsFloat(g));
        setBlue(colorAsFloat(b));
    }

    public void setColor(Color color) {
        setRed(colorAsFloat(color.getRed()));
        setGreen(colorAsFloat(color.getGreen()));
        setBlue(colorAsFloat(color.getBlue()));
        setAlfa(colorAsFloat(color.getAlpha()));
    }

    public void setColor(float r, float g, float b, float alfa) {
        setRed(r);
        setGreen(g);
        setBlue(b);
        setAlfa(alfa);
    }

    public Color asAWTColor() {
        return new Color(getRed(), getGreen(), getBlue(), getAlfa());
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getAlfa() {
        return alfa;
    }

    public void setAlfa(float alfa) {
        this.alfa = alfa;
    }

    public static float colorAsFloat(int colorComponent) {
        return colorComponent / COLOR_COMPONENT_MAX_VALUE;
    }
}
