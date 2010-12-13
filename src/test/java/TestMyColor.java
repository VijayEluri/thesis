import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.log4j.Logger;
import se.lnu.thesis.properties.ColorSchema;
import se.lnu.thesis.utils.MyColor;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 13.12.2010
 * Time: 17:35:29
 */
public class TestMyColor {

    public static final Logger LOGGER = Logger.getLogger(TestMyColor.class);

    @Test
    public void showAndCompare() {
        MyColor color1 = new MyColor(1.0f, 0.0f, 0.0f);
        MyColor color2 = new MyColor(Color.RED);
        MyColor color3 = new MyColor(Color.RED.getRGB());
        MyColor color4 = new MyColor(255, 0, 0);

        LOGGER.info(color1);
        LOGGER.info(color2);
        LOGGER.info(color3);
        LOGGER.info(color4);

        assertEquals(color1, color2);
        assertEquals(color2, color3);
        assertEquals(color3, color4);
        assertEquals(color4, color1);

        assertFalse(color1 == color2);
    }

    @Test
    public void alfaPart() {
        MyColor color1 = new MyColor(-65536);

        MyColor color2 = new MyColor(Color.RED);
        color2.setAlfa(0.5f);

        assertNotSame(color1, color2);
    }

    @Test
    public void printDefaultBlackSchema() {
        ColorSchema colorSchema = new ColorSchema();
        colorSchema.useDefaultBlackSchema();

        LOGGER.info("background = " + colorSchema.getBackground().asAWTColor().getRGB());
        LOGGER.info("selection = " + colorSchema.getSelection().asAWTColor().getRGB());
        LOGGER.info("focusing = " + colorSchema.getFocusing().asAWTColor().getRGB());
        LOGGER.info("subgraph = " + colorSchema.getSubgraph().asAWTColor().getRGB());

        LOGGER.info("GO level numbers = " + colorSchema.getGoLevelNumbers().asAWTColor().getRGB());
        LOGGER.info("GO level lines = " + colorSchema.getGoLevelLines().asAWTColor().getRGB());
        LOGGER.info("GO leaves = " + colorSchema.getGoLeaves().asAWTColor().getRGB());
        LOGGER.info("GO nodes = " + colorSchema.getGoNodes().asAWTColor().getRGB());

        LOGGER.info("Cluster leaves = " + colorSchema.getClusterLeaves().asAWTColor().getRGB());
        LOGGER.info("Cluster nodes = " + colorSchema.getClusterNodes().asAWTColor().getRGB());
        LOGGER.info("Cluster edges = " + colorSchema.getClusterEdges().asAWTColor().getRGB());

        LOGGER.info("Lens color = " + colorSchema.getLens().asAWTColor().getRGB());
        LOGGER.info("Lens alfa component = " + colorSchema.getLens().getAlfa());
    }

}
