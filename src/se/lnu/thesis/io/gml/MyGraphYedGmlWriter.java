package se.lnu.thesis.io.gml;

import se.lnu.thesis.core.MyGraph;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.08.2010
 * Time: 15:47:16
 * <p/>
 * Save Graph to GML file
 */
public class MyGraphYedGmlWriter extends GmlWriter {


    public void write(MyGraph graph, OutputStream outputStream) {

        String result = result(graph);

        Writer out = new BufferedWriter(new OutputStreamWriter(outputStream));
        try {
            out.write(result);
            out.flush();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    protected String result(MyGraph graph) {
        StringBuffer result = new StringBuffer();

        graph(result);

        for (Object node : graph.getVertices()) {
            node(result, graph, node);
        }

        for (Object edge : graph.getEdges()) {
            edge(result, graph, edge);
        }

        close(result);

        return result.toString();
    }

    protected void node(StringBuffer out, MyGraph graph, Object node) {
        element(out, "node");

        out.append("id");
        space(out);
        out.append("\"");
        out.append(node);
        out.append("\"");
        newLine(out);
        out.append("label");
        space(out);
        out.append("\"");
        out.append(graph.getLabel(node));
        out.append("\"");
        newLine(out);
    }

}