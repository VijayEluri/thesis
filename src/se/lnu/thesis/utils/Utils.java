package se.lnu.thesis.utils;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;


public class Utils {

    public static final Logger LOGGER = Logger.getLogger(Utils.class);

    public static final double PIdev180 = 0.017444;

    public static double inRadians(double angle) {
        return angle * PIdev180;
    }

    public static void computePosition(Point2D point, double angle, double radius, double xCenter, double yCenter) {
        double x = Math.cos(inRadians(angle)) * radius + xCenter;
        double y = Math.sin(inRadians(angle)) * radius + yCenter;

        point.setLocation(x, y);

        LOGGER.debug(angle + "; " + x + ", " + y);
    }

    public static double min(double value1, double value2) {
        return value1 < value2 ? value1 : value2;
    }

    public static double max(double value1, double value2) {
        return value1 > value2 ? value1 : value2;
    }
}
