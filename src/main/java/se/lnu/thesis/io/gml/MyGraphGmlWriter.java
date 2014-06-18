package se.lnu.thesis.io.gml;

import edu.uci.ics.jung.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.lnu.thesis.core.MyGraph;

import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.08.2010
 * Time: 15:47:16
 * <p/>
 * Save Graph to GML file
 */
public class MyGraphGmlWriter extends GmlWriter {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyGraphGmlWriter.class);

    public void write(MyGraph<Integer, Object> graph, OutputStream outputStream) {
        super.write(graph, outputStream);
    }

    @Override
    protected void processNodes(StringBuffer result, Graph graph) {
        MyGraph myGraph = (MyGraph) graph;

        for (Object o : graph.getVertices()) {
            tag(result, "node");

            result.append("id");
            space(result);
            result.append(o);
            newLine(result);

            result.append("label");
            space(result);
            result.append("\"");
            result.append(myGraph.getLabel(o));
            result.append("\"");
            newLine(result);

            close(result);
        }

    }
}