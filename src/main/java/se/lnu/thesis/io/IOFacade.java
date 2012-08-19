package se.lnu.thesis.io;

import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.gml.GmlReader;
import se.lnu.thesis.io.gml.GmlWriter;
import se.lnu.thesis.io.gml.MyGraphGmlReader;
import se.lnu.thesis.io.gml.MyGraphYedGmlReader;
import se.lnu.thesis.io.graphml.AbstractHandler;
import se.lnu.thesis.io.graphml.GraphMLParser;
import se.lnu.thesis.io.graphml.MyGraphYedHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 16.05.2010
 * Time: 17:21:29
 */
@Service
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

    public void writeGraphToGml(Graph graph, File file) {
        writeToGml(graph, new GmlWriter(), file);
    }

    protected void writeToGml(Graph graph, GmlWriter writer, File file) {
        try {
            writer.write(graph, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        }
    }

    protected Graph loadFromGml(GmlReader reader, File file) {
        Graph result = null;

        try {
            result = reader.read(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            LOGGER.error(e);
        }

        return result;
    }

    public MyGraph loadMyGraphFromGml(URL url) {
        try {
            return (MyGraph) loadFromGml(new MyGraphGmlReader(), new File(url.toURI()));
        } catch (URISyntaxException e) {
            LOGGER.error(e);
        }

        return null;
    }

    public MyGraph loadMyGraphFromGml(String file) {
        return (MyGraph) loadFromGml(new MyGraphGmlReader(), new File(file));
    }

    public MyGraph loadMyGraphFromGml(File file) {
        return (MyGraph) loadFromGml(new MyGraphGmlReader(), file);
    }

    public MyGraph loadMyGraphFromYedGml(String file) {
        return (MyGraph) loadFromGml(new MyGraphYedGmlReader(), new File(file));
    }

    public MyGraph loadMyGraphFromYedGml(File file) {
        return (MyGraph) loadFromGml(new MyGraphYedGmlReader(), file);
    }


    public Graph loadGraphFromGml(URL url) {
        try {
            return loadFromGml(new GmlReader(), new File(url.toURI()));
        } catch (URISyntaxException e) {
            LOGGER.error(e);
        }

        return null;
    }
}
