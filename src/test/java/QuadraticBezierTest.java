import se.lnu.thesis.utils.DrawingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * User: alevla (alevla@bossmedia.se)
 * Date: 2011-04-26
 * <p/>
 * Sample test for Bezier curve implementation
 */
public class QuadraticBezierTest extends JFrame {

    CubicCurve2D curve2D = null;

    Point2D a, b, c;


    public QuadraticBezierTest() {
        a = new Point2D.Double(200, 100);
        b = new Point2D.Double(100, 199);
        c = new Point2D.Double(250, 200);

        curve2D = new CubicCurve2D.Double();
        curve2D.setCurve(a, b, b, c);
    }


    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.GREEN);
        graphics2D.draw(new Line2D.Double(a, b));
        graphics2D.draw(new Line2D.Double(b, c));


        graphics2D.setColor(Color.RED);
        Point2D pOld = new Point2D.Double(a.getX(), a.getY());
        for (double t = 0.0; t <= 1.0; t += 0.01) {
            Point2D P = DrawingUtils.pointOnQuadranticBezierCurve(a, b, c, t);
            graphics2D.draw(new Line2D.Double(pOld, P));
            pOld = P;
        }


        graphics2D.setColor(Color.BLUE);
        graphics2D.draw(curve2D);
    }

    /**
     * For test purpose only
     */
    public static void main(String[] args) {
        QuadraticBezierTest bezierTest = new QuadraticBezierTest();
        bezierTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bezierTest.setSize(400, 400);
        bezierTest.setVisible(true);
    }

}
