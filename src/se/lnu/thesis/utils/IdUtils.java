package se.lnu.thesis.utils;

/**
 * Created by IntelliJ IDEA.
 * User: vlad
 * Date: 06.08.2010
 * Time: 14:43:05
 * <p/>
 * Use this class to get unique id for graph elements
 */
public class IdUtils {

    private static int CURRENT_ID = Integer.MIN_VALUE;

    public static int next() {
        return CURRENT_ID++;
    }
}
