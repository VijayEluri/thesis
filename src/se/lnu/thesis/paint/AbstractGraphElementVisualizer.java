package se.lnu.thesis.paint;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 */
public abstract class AbstractGraphElementVisualizer implements GraphElementVisualizer {

    private Visualizer visualizer;

    public AbstractGraphElementVisualizer() {

    }

    public AbstractGraphElementVisualizer(Visualizer visualizer) {
        setVisualizer(visualizer);
    }

    //public abstract void draw(Object edge);


    public Visualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(Visualizer visualizer) {
        this.visualizer = visualizer;
    }


}
