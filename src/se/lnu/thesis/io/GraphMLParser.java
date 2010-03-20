package se.lnu.thesis.io;

import org.apache.log4j.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

public class GraphMLParser {

    private static final Logger LOGGER = Logger.getLogger(GraphMLParser.class);


    private AbstractHandler handler;

    protected GraphMLParser() {

    }

    public GraphMLParser(AbstractHandler handler) {
        setHandler(handler);
    }

    public List load(File file) {

        List graphs = null;

        if (file != null) {
            LOGGER.info("Parsing graphml file " + file.getAbsolutePath());

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            try {
                SAXParser saxParser = saxParserFactory.newSAXParser();

                // parse the graphml file and also register this class for call backs
                saxParser.parse(file, handler);

            } catch (Exception e) {
                LOGGER.error(e);
            }

            graphs = handler.getGraphs();

            LOGGER.info("Done.");
            LOGGER.info("Loaded " + graphs.size() + " graphs.");
        } else {
            LOGGER.error("No graph file selected!");
        }

        return graphs;
    }

    public AbstractHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractHandler handler) {
        this.handler = handler;
    }


}