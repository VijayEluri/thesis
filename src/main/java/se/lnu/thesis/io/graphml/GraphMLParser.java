package se.lnu.thesis.io.graphml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

public class GraphMLParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphMLParser.class);


    private static final int READ_BUFFER = 4096;

    private AbstractHandler handler;

    protected GraphMLParser() {

    }

    public GraphMLParser(AbstractHandler handler) {
        setHandler(handler);
    }

    public List load(String path) {

        List graphs = null;

        if (path != null) {
            LOGGER.info("Parsing graphml file " + path);

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            try {
                SAXParser saxParser = saxParserFactory.newSAXParser();
                saxParser.setProperty("http://apache.org/xml/properties/input-buffer-size", READ_BUFFER);

                // parse the graphml file and also register this class for call backs
                saxParser.parse(path, handler);

            } catch (Exception e) {
                LOGGER.warn("Error parsing graph file", e);
            }

            graphs = handler.getGraphs();

            LOGGER.info("Done.");
            LOGGER.info("Loaded " + graphs.size() + " graphs.");
        } else {
            LOGGER.error("No graph file selected!");
        }

        return graphs;
    }

    public List load(File file) {
        return load(file.getAbsolutePath());
    }

    public AbstractHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractHandler handler) {
        this.handler = handler;
    }


}