package se.lnu.thesis.launcher;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 19.08.12
 * Time: 01:34
 */
public class LauncherTest {

    @Test
    public void allParams() {
        String[] argv = {"load", "-t=gml", "-g=/egz/egz/go.gml", "-c=/egz/egz.gml"};


        Launcher launcher = new Launcher();
        launcher.parse(Arrays.asList(argv));

        //Assert.assertEquals("gml");

    }

}
