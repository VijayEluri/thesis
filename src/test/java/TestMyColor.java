import org.junit.Test;
import static org.junit.Assert.*;
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

    @Test
    public void showAndCompare() {
        MyColor color1 = new MyColor(1.0f, 0.0f, 0.0f);
        MyColor color2 = new MyColor(Color.RED);
        MyColor color3 = new MyColor(Color.RED.getRGB());
        MyColor color4 = new MyColor(255, 0, 0);

        System.out.println(color1);
        System.out.println(color2);
        System.out.println(color3);
        System.out.println(color4);

        assertEquals(color1, color2);
        assertEquals(color2, color3);
        assertEquals(color3, color4);
        assertEquals(color4, color1);

        assertFalse(color1 == color2);
    }

}
