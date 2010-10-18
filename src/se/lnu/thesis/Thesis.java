package se.lnu.thesis;

import org.apache.log4j.Logger;

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

    public static final Logger LOGGER = Logger.getLogger(Thesis.class);

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");  // for MacOS X only

        Scene.getInstance().initUI();
    }
}
