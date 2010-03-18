package se.lnu.thesis.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import se.lnu.thesis.utils.Utils;


public class TestUtils {

    @Test
    public void testMin() {
        assertEquals(5.0f, Utils.min(9.0d, 5.0d));
    }

    @Test
    public void testMax() {
        assertEquals(9.0f, Utils.max(9.0d, 5.0d));
    }

}
