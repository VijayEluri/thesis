package se.lnu.thesis.io.gml;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
public class GmlReader {

    public static final Logger LOGGER = Logger.getLogger(GmlReader.class);

    protected String tagGraph;
    protected String tagNode;
    protected String tagEdge;

    protected String tagID;
    protected String tagSource;
    protected String tagTarget;

    protected Graph result = null;

    protected boolean node = false;
    protected boolean edge = false;

    protected Integer id = null;

    protected Integer source = null;


    public Graph<Integer, String> read(InputStream inputStream) {
        init();

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        try {

            while (in.ready()) {
                String s = in.readLine();
                s = prepareString(s);

                if (tagGraph(s)) continue;

                if (tagNode(s)) continue;

                if (tagId(s)) continue;

                if (tagEdge(s)) continue;

                if (tagEdgeSource(s)) continue;

                tagEdgeTarget(s);
            }

        } catch (IOException e) {
            LOGGER.error(e);
        }


        return result;
    }

    protected void init() {
        tagGraph = "graph [";
        tagNode = "node [";
        tagEdge = "edge [";

        tagID = "id ";
        tagSource = "source ";
        tagTarget = "target ";


        result = null;
        node = false;
        edge = false;
        id = null;
        source = null;
    }

    protected String prepareString(String s) {
        s = StringUtils.replaceEach(s, new String[]{"\t", "\n"}, new String[]{" ", " "}); // delete all tabs
        return StringUtils.strip(s, " ");
    }

    protected boolean tagGraph(String s) {
        if (s.startsWith(tagGraph)) {
            result = new DirectedSparseGraph<Integer, String>();

            return true;
        }

        return false;
    }

    protected boolean tagNode(String s) {
        if (s.startsWith(tagNode)) {
            node = true;

            return true;
        }
        return false;
    }

    protected boolean tagId(String s) {
        if (s.startsWith(tagID) && node) { // read vertex id
            id = Utils.extractIntegerValue(s, tagID);

            if (result.addVertex(id)) {
                LOGGER.debug("Vertex '" + id + "' added successfully");
            } else {
                LOGGER.error("Cannot add vertex '" + id + "'");
            }

            return true;
        }
        return false;
    }

    protected boolean tagEdge(String s) {
        if (s.startsWith(tagEdge)) {
            edge = true;
            node = false;

            return true;
        }
        return false;
    }

    protected boolean tagEdgeSource(String s) {
        if (s.startsWith(tagSource) && edge) { // edge
            source = Utils.extractIntegerValue(s, tagSource);

            return true;
        }
        return false;
    }

    protected void tagEdgeTarget(String s) {
        if (s.startsWith(tagTarget) && edge && source != null) {
            Integer target = Utils.extractIntegerValue(s, tagTarget);

            if (result.containsVertex(source) && result.containsVertex(target)) {
                String edge_id = source + "->" + target;
                if (result.addEdge(edge_id, source, target, EdgeType.DIRECTED)) {
                    LOGGER.debug("Edge '" + edge_id + "' added successfully!");
                } else {
                    LOGGER.error("Cannt add edge! '" + edge_id + "'");
                }
            }

            edge = false;
            source = null;
        }
    }


}
