package se.lnu.thesis;

import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.07.2010
 * Time: 16:41:49
 */
public class Scene {

    public static final Logger LOGGER = Logger.getLogger(Scene.class);

    private static Scene instance = new Scene();

    public static Scene getInstance() {
        return instance;
    }

    private MyGraph go;
    private MyGraph cluster;

    private Scene() {

    }

}
