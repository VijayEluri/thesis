package se.lnu.thesis.properties;

import org.apache.log4j.Logger;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;

import java.util.Iterator;

import se.lnu.thesis.utils.MyColor;

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

    public static final String PROPERTIES_FILE = "program.properties";

    private static final PropertiesHolder instance = new PropertiesHolder();

    public static PropertiesHolder getInstance() {
        return instance;
    }

    private String file = PROPERTIES_FILE;

    private PropertiesConfiguration properties;
    private ColorSchema colorSchema;

    protected PropertiesHolder() {
        load();
    }

    public void load() {
        if (file == null) {
            LOGGER.error("Properties file doesn't initialized");
            return;
        }

        try {
            LOGGER.info("Initializing properties holder");

            properties = new PropertiesConfiguration();

            LOGGER.info("Loading from file.. [" + file + "]");

            properties.load(file);

            LOGGER.info("Done.");
        } catch (ConfigurationException e) {
            LOGGER.error(e);
        }

        loadColorSchema();
    }

    private void loadColorSchema() {
        if (colorSchema == null) {
            colorSchema = new ColorSchema();
        }

        Iterator keys = properties.getKeys();
        while (keys.hasNext()) {
            String key = (String) keys.next();

            MyColor myColor = colorSchema.getColor(key);

            if (myColor != null) {
                myColor.setColor(properties.getInt(key));
            }
        }

        colorSchema.getLens().setAlfa(properties.getFloat("color.lens_alfa"));
    }

    public ColorSchema getColorSchema() {
        return colorSchema;
    }

    public void save() {

        LOGGER.info("Saving color properties to file..");

        Iterator keys = properties.getKeys();
        while (keys.hasNext()) {
            String key = (String) keys.next();

            MyColor myColor = colorSchema.getColor(key);

            if (myColor != null) {
                properties.setProperty(key, myColor.asAWTColor().getRGB());
            }
        }

        properties.setProperty("color.lens_alfa", colorSchema.getLens().getAlfa());

        try {
            properties.save(PROPERTIES_FILE);
        } catch (ConfigurationException e) {
            LOGGER.error(e);
        }

        LOGGER.info("Done.");
    }


}
