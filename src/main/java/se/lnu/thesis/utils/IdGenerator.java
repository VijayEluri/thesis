package se.lnu.thesis.utils;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 06.08.2010
 * Time: 14:43:05
 * <p/>
 * <p/>
 * Use this class to get unique id for drawing elements
 */
public class IdGenerator {

    public static final int START_ID = 1;

    private static int CURRENT_ID = START_ID;

    public static int next() {
        return CURRENT_ID++; // TODO what about Integer.MAX_VALUE ?
    }
}
