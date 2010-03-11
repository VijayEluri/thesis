package io;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 28.01.2010
 */
public abstract class AbstractHandler extends DefaultHandler {

    protected static final String TAG_GRAPH = "graph";
    protected static final String TAG_NODE = "node";
    protected static final String TAG_EDGE = "edge";

    protected static final String ATTRIBUTE_ID = "id";

    protected static final String ATTRIBUTE_EDGE_SOURCE = "source";
    protected static final String ATTRIBUTE_EDGE_TARGET = "target";

    protected Object graph;
    protected List graphs;

    public abstract void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException;

    public abstract void endElement(String uri, String localName, String qName) throws SAXException;

    public void initGraphsList() {
        graphs = new LinkedList();
    }

    public List getGraphs() {
        return graphs;
    }

}
