import processing.core.PApplet;
import edu.uci.ics.jung.graph.Graph;
import io.GraphMLParser;
import io.JungYedHandler;

import java.io.File;
import java.awt.geom.Point2D;

import layout.RadialLayout;

public class ProcessingSketch extends PApplet {


    public void setup() {
        size(800, 800);
        background(0);
        stroke(255);
    }


    public void draw() {


        fill(255);

        ellipse(400, 400, 200, 200);
    }
}
