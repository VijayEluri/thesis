package se.lnu.thesis.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.12
 * Time: 01:42
 */
public class RepaintWindowEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public RepaintWindowEvent(Object source) {
        super(source);
    }
}
