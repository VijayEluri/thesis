package se.lnu.thesis.event;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 17.07.11
 * Time: 01:30
 */
public class GOStatusBarTextEvent extends StatusBarTextEvent {

    public GOStatusBarTextEvent(Object source) {
        super(source);
    }

    public GOStatusBarTextEvent(Object source, String text) {
        super(source, text);
    }
}
