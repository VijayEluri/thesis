

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import se.lnu.thesis.utils.Utils;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 06.08.2010
 * Time: 14:42:00
 */
public class TestUtils {

    @Test
    public void testStringValueExtractor() {
        String s = "                name \"Super mega name\"            ";
        String value = Utils.extractStringValue(s, "name");

        assertTrue("Super mega name".compareTo(value) == 0);

        s = "       egz  \"123     123  \"";
        value = Utils.extractStringValue(s, "egz");
        assertTrue("123     123  ".compareTo(value) == 0);
    }


    @Test
    public void testIntegerValueExtractor() {
        String s = "id     123445   ";
        assertEquals(123445, Utils.extractIntegerValue(s, "id"));

        s = "       egz         123";
        assertEquals(123, Utils.extractIntegerValue(s, "egz"));
    }


}
