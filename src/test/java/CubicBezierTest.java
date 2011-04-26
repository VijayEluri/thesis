import se.lnu.thesis.utils.DrawingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * User: alevla (alevla@bossmedia.se)
 * Date: 2011-04-26
 * <p/>
 * <p/>
 * Sample test for Bezier curve implementation
 */
public class CubicBezierTest extends JFrame {

    Point2D a, b, c, d;


    public CubicBezierTest() {
        a = new Point2D.Double(100, 100);
        b = new Point2D.Double(200, 170);
        c = new Point2D.Double(170, 200);
        d = new Point2D.Double(50, 185);
    }


    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.GREEN);
        graphics2D.draw(new Line2D.Double(a, b));
        graphics2D.draw(new Line2D.Double(b, c));
        graphics2D.draw(new Line2D.Double(c, d));


        graphics2D.setColor(Color.RED);

        Point2D pOld = new Point2D.Double(a.getX(), a.getY());
        for (double t = 0.0; t <= 1.0; t += 0.01) {
            Point2D P = DrawingUtils.pointOnCubicBezierCurve(a, b, c, d, t);
            graphics2D.draw(new Line2D.Double(pOld, P));
            pOld = P;
        }
    }

    /**
     * For test purpose only
     */
    public static void main(String[] args) {
        CubicBezierTest bezierTest = new CubicBezierTest();
        bezierTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bezierTest.setSize(400, 400);
        bezierTest.setVisible(true);
    }

}
