package se.lnu.thesis.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 17.07.11
 * Time: 01:30
 * TODO add javadoc
 */
public class SetStatusBarText extends ApplicationEvent {

    private String text;

    public SetStatusBarText(Object source, String text) {
        super(source);

        this.text = text;
    }

    public String getText() {
        return text;
    }
}
