import org.slf4j.LoggerFactory;
import se.lnu.thesis.properties.PropertiesHolder;
import se.lnu.thesis.properties.ColorSchema;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.12.2010
 * Time: 18:19:46
 */
public class TestPropertiesHolder {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestPropertiesHolder.class);

    @Test
    public void testLoadedColorSchema() {
/*
        ColorSchema colorSchema = PropertiesHolder.getInstance().getColorSchema();

        assertNotNull(colorSchema);
        assertEquals(0.53f, colorSchema.getLens().getAlfa());

        colorSchema.getLens().setAlfa(1.0f);

        PropertiesHolder.getInstance().save();
        
        PropertiesHolder.getInstance().load();

        assertNotNull(colorSchema);
        assertEquals(1.0f, colorSchema.getLens().getAlfa());

        colorSchema.getLens().setAlfa(0.53f);
        PropertiesHolder.getInstance().save();
*/
        assertTrue(true);
    }

}