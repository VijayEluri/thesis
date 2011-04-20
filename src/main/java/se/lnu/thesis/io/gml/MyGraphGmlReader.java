package se.lnu.thesis.io.gml;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 25.10.2010
 * Time: 15:36:31
 */
public class MyGraphGmlReader extends GmlReader {

    public static final Logger LOGGER = Logger.getLogger(MyGraphGmlReader.class);

    protected String tagLabel;

    public MyGraph<Integer, String> read(InputStream inputStream) {
        init();

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {

            while (in.ready()) {
                String s = in.readLine();
                s = prepareString(s);

                if (tagGraph(s)) continue;

                if (tagNode(s)) continue;

                if (tagId(s)) continue;

                if (tagLabel(s)) continue;

                if (tagEdge(s)) continue;

                if (tagEdgeSource(s)) continue;

                tagEdgeTarget(s);
            }

        } catch (IOException e) {
            LOGGER.error(e);
        }


        return (MyGraph<Integer, String>) result;
    }

    @Override
    protected boolean tagGraph(String s) {
        if (s.startsWith(tagGraph)) {
            result = new MyGraph<Integer, String>();

            return true;
        }

        return false;
    }

    protected void init() {
        super.init();

        tagLabel = "label ";
    }



    protected boolean tagLabel(String s) {
        if (s.startsWith(tagLabel) && node && id != null) { // node label
            String label = Utils.extractStringValue(s, tagLabel);

            ((MyGraph) result).addLabel(id, label);

            node = false;
            id = null;

            return true;
        }

        return false;
    }

}
