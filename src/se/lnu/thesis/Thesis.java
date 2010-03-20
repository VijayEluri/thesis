package se.lnu.thesis;

import processing.core.PApplet;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 15.03.2010
 * Time: 18:22:20
 * <p/>
 * <p/>
 * Program entry point!
 * <p/>
 * java se.lnu.thesis.Thesis [processing params] start <<cluster_graphml_file_path>>
 */
public class Thesis {

    private static final String START = "start";
    private static final String MY_APPLET_CLASS = "se.lnu.thesis.ThesisApplet";

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            if (args[i].compareToIgnoreCase(START) == 0) {
                args[i] = MY_APPLET_CLASS;
            }
        }

        if (args.length == 0) {
            args = new String[]{MY_APPLET_CLASS};
        }

        PApplet.main(args);
    }
}
