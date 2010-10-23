import se.lnu.thesis.Scene;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 23.10.2010
 * Time: 16:09:10
 * <p/>
 * <p/>
 * Program entry point
 */
public class GoClusterViz {

    /*
     * Program entry point.
     */
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");  // for MacOS X only

        Scene.getInstance().initUI();
    }


}
