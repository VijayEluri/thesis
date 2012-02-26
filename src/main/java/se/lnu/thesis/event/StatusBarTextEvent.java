package se.lnu.thesis.event;

import com.google.common.base.Strings;
import org.springframework.context.ApplicationEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 17.07.11
 * Time: 01:30
 */
public class StatusBarTextEvent extends ApplicationEvent {

    private String text;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public StatusBarTextEvent(Object source) {
        super(source);
    }

    public StatusBarTextEvent(Object source, String text) {
        super(source);

        this.text = text;
    }

    public String getText() {
        return Strings.nullToEmpty(text);
    }
}
