package se.lnu.thesis.event;

/**
 * Created by IntelliJ IDEA.
 * User: Vlad
 * Date: 17.07.11
 * Time: 01:30
 */
public class ClusterStatusBarTextEvent extends StatusBarTextEvent {

    public ClusterStatusBarTextEvent(Object source) {
        super(source);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ClusterStatusBarTextEvent(Object source, String text) {
        super(source, text);
    }
}
