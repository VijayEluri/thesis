package se.lnu.thesis.test;

import static org.junit.Assert.assertEquals;
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
    public void testMin() {
        assertEquals(5.0, Utils.min(9.0d, 5.0d));
    }

    @Test
    public void testMax() {
        assertEquals(9.0, Utils.max(9.0d, 5.0d));
    }


}
