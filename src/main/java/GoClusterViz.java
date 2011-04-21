import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;

import javax.swing.*;

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

    public static final Logger LOGGER = Logger.getLogger(GoClusterViz.class);

    /*
     * Program entry point.
     */
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");  // for MacOS X only

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            LOGGER.warn(e);
        } catch (InstantiationException e) {
            LOGGER.warn(e);
        } catch (IllegalAccessException e) {
            LOGGER.warn(e);
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.warn(e);
        }

        Scene.getInstance().initUI();
    }


}
