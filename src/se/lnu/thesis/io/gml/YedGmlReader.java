package se.lnu.thesis.io.gml;

import se.lnu.thesis.core.MyGraph;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.10.2010
 * Time: 1:35:24
 */
public class YedGmlReader extends GmlReader {

    @Override
    protected void init() {
        tagGraph = "graph";
        tagNode = "node";
        tagEdge = "edge";

        tagID = "id ";
        tagLabel = "label ";
        tagSource = "source ";
        tagTarget = "target ";


        result = null;
        node = false;
        edge = false;
        id = null;
        source = null;
    }

    @Override
    protected boolean tagGraph(String s) {
        if (s.compareTo("graph") == 0) {
            result = new MyGraph<Integer, String>();

            return true;
        }

        return false;

    }
}
