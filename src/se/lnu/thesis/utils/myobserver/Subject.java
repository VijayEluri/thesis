package se.lnu.thesis.utils.myobserver;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.04.2010
 * Time: 20:20:01
 */
public interface Subject {

    public boolean registerObserver(Observer observer);

    public boolean unregisterObserver(Observer observer);

    public void notifyObservers();

}
