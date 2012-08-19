package se.lnu.thesis.launcher;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 19.08.12
 * Time: 01:30
 */
@Parameters(separators = "=")
public class LoadCommand {

    public static final String NAME = "load";

    @Parameter(names = {"-t", "--type"}, description = "Graph file format type", required = true)
    private String type;

    @Parameter(names = {"-g", "--go"}, description = "Absolute path to GO graph file", required = true)
    private String go;

    @Parameter(names = {"-c", "--cluster"}, description = "Absolute path to Cluster graph file", required = true)
    private String cluster;

    public String getType() {
        return type;
    }

    public String getGo() {
        return go;
    }

    public String getCluster() {
        return cluster;
    }

    @Override
    public String toString() {
        return "Type: " + type + " GO graph location: " + go + " Cluster graph location: " + cluster;
    }
}
