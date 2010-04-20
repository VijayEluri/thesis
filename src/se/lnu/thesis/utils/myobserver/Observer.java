package se.lnu.thesis.utils.myobserver;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.04.2010
 * Time: 20:19:43
 */
public interface Observer {

    public void notifyObserver(Subject subject, Object params);

}
