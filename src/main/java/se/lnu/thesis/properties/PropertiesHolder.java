package se.lnu.thesis.properties;

import org.apache.log4j.Logger;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 09.12.2010
 *
 *          This class stores all program properties.
 *      Property file stores in user directory .GoClusterViz/program.properties
 *
 *
 */
public class PropertiesHolder {

    public static final Logger LOGGER = Logger.getLogger(PropertiesHolder.class);

    /*public static final String PROPERTIES_FILE = "program.properties";*/

    private static final PropertiesHolder instance = new PropertiesHolder();

    public static PropertiesHolder getInstance() {
        return instance;
    }

    private PropertiesConfiguration properties;
    private ColorSchema colorSchema;

    private PropertiesHolder() {
/*
        try {
            LOGGER.info("Initializing properties holder");

            properties = new PropertiesConfiguration();

            LOGGER.info("Loading from file.. [" + PROPERTIES_FILE + "]");

            properties.load(PROPERTIES_FILE);

            LOGGER.info("Done.");
        } catch (ConfigurationException e) {
            LOGGER.error(e);
        }
*/
        colorSchema = new ColorSchema();
        colorSchema.useDefaultBlackSchema();
    }

    public ColorSchema getColorSchema() {
        return colorSchema;
    }

/*
    public void save() {
        try {
            properties.save(PROPERTIES_FILE);
        } catch (ConfigurationException e) {
            LOGGER.error(e);
        }
    }
*/


}
