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
    private static final String MY_APPLET_CLASS = "se.lnu.thesis.gui.ThesisApplet";

    private static boolean start = false;

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            if (args[i].compareToIgnoreCase(START) == 0) {
                args[i] = MY_APPLET_CLASS;
                start = true;
            }
        }

        if (start && args.length == 3) {
            PApplet.main(args);
        } else {
            System.out.println("Error starting program.");
            System.out.println("java se.lnu.thesis.Thesis [processing params] start <<gene_ontology_file_path>> <<cluster_file_path>>");
            System.out.println("[processing params] - optional");
            System.out.println("<<gene_ontology_file_path>> - path to graphml gene ontology file");
            System.out.println("<<cluster_file_path>> - path to graphml cluster file");
        }
    }
}
