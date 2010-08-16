package se.lnu.thesis.io.gml;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;

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

    public static final Logger LOGGER = Logger.getLogger(GmlWriter.class);

    public void write(Graph graph, OutputStream outputStream) {

        String result = result(graph);

        Writer out = new BufferedWriter(new OutputStreamWriter(outputStream));
        try {
            out.write(result);
            out.flush();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    protected String result(Graph graph) {
        StringBuffer result = new StringBuffer();

        graph(result);

        for (Object node : graph.getVertices()) {
            node(result, node);
        }

        for (Object edge : graph.getEdges()) {
            edge(result, graph, edge);
        }

        close(result);

        return result.toString();
    }

    protected void graph(StringBuffer out) {
        element(out, "graph");
    }

    protected void node(StringBuffer out, Object node) {
        element(out, "node");

        out.append("id");
        space(out);
        out.append(node);
        newLine(out);
        close(out);
    }

    protected void edge(StringBuffer out, Graph graph, Object edge) {
        Object source = graph.getSource(edge);
        Object target = graph.getDest(edge);

        element(out, "edge");

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

    protected void element(StringBuffer out, String element) {
        out.append(element);
        space(out);
        out.append('[');
        newLine(out);
    }

    protected void space(StringBuffer out) {
        out.append(' ');
    }

    protected void newLine(StringBuffer out) {
        out.append('\n');
    }

    protected void close(StringBuffer out) {
        out.append(']');
        newLine(out);
    }


}
