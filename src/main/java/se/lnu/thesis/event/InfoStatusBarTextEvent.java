package se.lnu.thesis.event;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 17.07.11
 * Time: 01:30
 */
public class InfoStatusBarTextEvent extends StatusBarTextEvent {

    public InfoStatusBarTextEvent(Object source) {
        super(source);
    }

    public InfoStatusBarTextEvent(Object source, String text) {
        super(source, text);
    }
}
