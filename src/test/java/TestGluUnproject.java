import org.junit.Assert;
import org.junit.Test;

import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.gl2.GLUgl2;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 01.05.11
 * Time: 18:17
 */
public class TestGluUnproject {

    @Test
    public void convert() {
        Point point = new Point(100, 200);

        int viewport[] = {0, 0, 960, 1020};
        double mvmatrix[] = {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        double projmatrix[] = {1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0};

        double wcoord[] = new double[4]; // wx, wy, wz


        double x = point.getX();
        double y = viewport[3] - point.getY(); /* note viewport[3] is height of window in pixels */

        GLU glu2 = new GLUgl2();
        glu2.gluUnProject(x, y, 0.0, mvmatrix, 0, projmatrix, 0, viewport, 0, wcoord, 0);

        System.out.println("[" + wcoord[0] + "," + wcoord[1] + "]");
        Assert.assertTrue(wcoord[0] != 0);
        Assert.assertTrue(wcoord[1] != 0);

    }

}
