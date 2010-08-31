package se.lnu.thesis.paint;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.08.2010
 * Time: 2:34:38
 */
public class MyDimension extends Dimension {


    private double height;
    private double width;

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setSize(double height, double width) {
        this.height = height;
        this.width = width;
    }
}
