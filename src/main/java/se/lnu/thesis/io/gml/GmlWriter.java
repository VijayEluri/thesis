package se.lnu.thesis.io.gml;

import edu.uci.ics.jung.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.08.2010
 * Time: 15:47:16
 * <p/>
 * Save Graph to GML file
 */
public class GmlWriter {

    public static final Logger LOGGER = LoggerFactory.getLogger(GmlWriter.class);

    public static final char OPEN_SQUARE_BRACKET = '[';
    public static final char CLOSE_SQUARE_BRACKET = ']';

    public void write(Graph<Integer, Object> graph, OutputStream outputStream) {

        String result = buildResultString(graph);

        Writer out = new BufferedWriter(new OutputStreamWriter(outputStream));
        try {
            out.write(result);
            out.flush();
        } catch (IOException e) {
            LOGGER.error("Error writing graph to file", e);
        }
    }

    protected String buildResultString(Graph graph) {
        StringBuffer result = new StringBuffer();

        tag(result, "graph"); // open graph tag

        processNodes(result, graph);

        processEdges(result, graph);

        close(result); // close graph tag

        return result.toString();
    }

    protected void graph(StringBuffer out) {

    }

    protected void processNodes(StringBuffer result, Graph graph) {
        for (Object o : graph.getVertices()) {
            tag(result, "node");

            result.append("id");
            space(result);
            result.append(o);
            newLine(result);
            close(result);
        }
    }

    protected void processEdges(StringBuffer out, Graph graph) {
        for (Object o : graph.getEdges()) {
            Object source = graph.getSource(o);
            Object target = graph.getDest(o);

            tag(out, "edge");

            out.append("source");
            space(out);
            out.append(source);
            newLine(out);
            out.append("target");
            space(out);
            out.append(target);
            newLine(out);
            close(out);
        }
    }

    protected void tag(StringBuffer result, String tag) {
        result.append(tag);
        space(result);
        result.append(OPEN_SQUARE_BRACKET);
        newLine(result);
    }

    protected void space(StringBuffer out) {
        out.append(' ');
    }

    protected void newLine(StringBuffer out) {
        out.append('\n');
    }

    protected void close(StringBuffer out) {
        out.append(CLOSE_SQUARE_BRACKET);
        newLine(out);
    }

}
