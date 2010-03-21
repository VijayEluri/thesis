package se.lnu.thesis.utils;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class Utils {

    public static final Logger LOGGER = Logger.getLogger(Utils.class);

    public static final double PIdev180 = 0.017444;

    public static double inRadians(double angle) {
        return angle * PIdev180;
    }

    public static void computePosition(Point2D point, double angle, double radius, double xCenter, double yCenter) {
        double x = Math.cos(Utils.inRadians(angle)) * radius + xCenter;
        double y = Math.sin(Utils.inRadians(angle)) * radius + yCenter;

        point.setLocation(x, y);

        LOGGER.debug(angle + "; " + x + ", " + y);
    }

    public static double min(double value1, double value2) {
        return value1 < value2 ? value1 : value2;
    }

    public static double max(double value1, double value2) {
        return value1 > value2 ? value1 : value2;
    }

    public static void loadSubtreeLabels(Set<String> subgraphLabels) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File selectedFile = fileChooser.getSelectedFile();

        if (selectedFile == null) {
            LOGGER.warn("No file selected to load labels from!");

            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));

            if (subgraphLabels == null) {
                subgraphLabels = new HashSet<String>();
            } else {
                subgraphLabels.clear();
            }

            while (reader.ready()) {
                subgraphLabels.add(reader.readLine());
            }

        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }

    }
}
