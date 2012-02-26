package se.lnu.thesis.event;

import org.springframework.context.ApplicationEvent;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.12
 * Time: 01:42
 */
public class BackgroundChangedEvent extends ApplicationEvent {

    private Color color;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public BackgroundChangedEvent(Object source) {
        super(source);
    }

    public BackgroundChangedEvent(Object source, Color color) {
        super(source);

        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
