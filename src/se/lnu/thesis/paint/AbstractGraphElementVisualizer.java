package se.lnu.thesis.paint;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 */
public abstract class AbstractGraphElementVisualizer implements GraphElementVisualizer {

    private GraphVisualizer visualizer;

    public AbstractGraphElementVisualizer() {

    }

    public AbstractGraphElementVisualizer(GraphVisualizer visualizer) {
        setVisualizer(visualizer);
    }

    //public abstract void draw(Object edge);


    public GraphVisualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(GraphVisualizer visualizer) {
        this.visualizer = visualizer;
    }


}
