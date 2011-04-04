import se.lnu.thesis.element.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 26.03.11
 * Time: 21:53
 * <p/>
 * <p/>
 * This is window class to visualize Container element. Draws nodes and edges.
 */
public class LayoutVisualizerFrame extends JFrame {

    se.lnu.thesis.element.Container container;

    @Override
    public void paint(Graphics graphics) {

        if (this.getContainer() != null) {

            for (Element element : this.getContainer()) {
                if (element.getType() == ElementType.VERTEX) {
                    int x = (int) element.getPosition().getX();
                    int y = (int) element.getPosition().getY();

                    graphics.setColor(Color.RED);
                    graphics.fillOval(x, y, 10, 10);
                }

                if (element.getType() == ElementType.EDGE) {
                    int x1 = (int) ((EdgeElement) element).getStartPosition().getX();
                    int y1 = (int) ((EdgeElement) element).getStartPosition().getY();

                    int x2 = (int) ((EdgeElement) element).getEndPosition().getX();
                    int y2 = (int) ((EdgeElement) element).getEndPosition().getY();

                    graphics.setColor(Color.RED);
                    graphics.drawLine(x1, y1, x2, y2);
                }
            }

        }
    }

    public se.lnu.thesis.element.Container getContainer() {
        return container;
    }

    public void setContainer(se.lnu.thesis.element.Container graphContainer) {
        this.container = graphContainer;
    }

}
