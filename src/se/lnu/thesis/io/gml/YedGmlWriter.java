package se.lnu.thesis.io.gml;

import edu.uci.ics.jung.graph.Graph;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.08.2010
 * Time: 15:47:16
 * <p/>
 * Save Graph to GML file
 */
public class YedGmlWriter extends GmlWriter {

    @Override
    protected void node(StringBuffer out, Object node) {
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
        out.append(node);
        out.append("\"");
        newLine(out);

        close(out);
    }

    @Override
    protected void edge(StringBuffer out, Graph graph, Object edge) {
        Object source = graph.getSource(edge);
        Object target = graph.getDest(edge);

        element(out, "edge");

        out.append("source");
        space(out);
        out.append("\"");
        out.append(source);
        out.append("\"");
        newLine(out);
        out.append("target");
        space(out);
        out.append("\"");
        out.append(target);
        out.append("\"");
        newLine(out);
        close(out);
    }

}