package se.lnu.thesis.io;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.gml.GmlWriter;
import se.lnu.thesis.io.gml.GraphYedGmlWriter;
import se.lnu.thesis.io.graphml.AbstractHandler;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.MyGraphYedHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.05.2010
 * Time: 17:21:29
 */
public class IOFacade {

    public static final Logger LOGGER = Logger.getLogger(IOFacade.class);

    public MyGraph loadFromYedGraphml(File file) {
        return (MyGraph) loadFromXml(file, new MyGraphYedHandler());
    }

    public MyGraph loadFromYedGraphml(String path) {
        return (MyGraph) loadFromXml(new File(path), new MyGraphYedHandler());
    }

    protected Graph loadFromXml(File file, AbstractHandler handler) {
        Graph result = null;

        long start = System.currentTimeMillis();
        result = (Graph) new GraphMLParser(handler).load(file).get(0);
        long end = System.currentTimeMillis();

        LOGGER.info("Loading graph from file '" + file.getAbsolutePath() + "'.. Done in " + TimeUnit.SECONDS.convert(end - start, TimeUnit.MILLISECONDS) + "s");

        return result;
    }


    public void writeToYedGmlFile(Graph graph, File file) {
        writeToGmlFile(graph, new GraphYedGmlWriter(), file);
    }

/*
    public void writeToYedGmlFile(MyGraph graph, File file) {
        writeToGmlFile(graph, new MyGraphYedGmlWriter(), file);
    }
*/


    protected void writeToGmlFile(Graph graph, GmlWriter writer, File file) {
        try {
            writer.write(graph, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        }
    }


}
