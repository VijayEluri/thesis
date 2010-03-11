package io;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Map;
import java.util.HashMap;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * User: vady
 * Date: 28.01.2010
 * Time: 0:01:49
 */
public class JungYedHandler extends AbstractHandler {

    private static final Logger LOGGER = Logger.getLogger(JungYedHandler.class);

    private static final String TAG_YED_NODE_LABEL = "y:NodeLabel";

    private Map<String,String> idLabel;
    private String currentNodeIs;
    private Boolean labelTag;

    public JungYedHandler() {
        initGraphsList();
        idLabel = new HashMap<String, String>();
    }

    // Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        LOGGER.debug("TAG OPENS. Start processing..");

        if (qName.equalsIgnoreCase(TAG_GRAPH)) {
            LOGGER.debug("Processing tag.." + TAG_GRAPH);

            graph = new DirectedSparseGraph();
        }


        if (qName.equalsIgnoreCase(TAG_NODE)) {
            LOGGER.debug("Processing tag.." + TAG_NODE);

            String id = attributes.getValue(ATTRIBUTE_ID);
            createNode(id);
            currentNodeIs = id;
        }

        if (qName.equalsIgnoreCase(TAG_YED_NODE_LABEL)) {
            LOGGER.debug("Processing tag.." + TAG_YED_NODE_LABEL);
            labelTag = true;
        }

        if (qName.equalsIgnoreCase(TAG_EDGE)) {
            LOGGER.debug("Processing tag.." + TAG_EDGE);

            String id = attributes.getValue(ATTRIBUTE_ID);

            String source = attributes.getValue(ATTRIBUTE_EDGE_SOURCE);

            String target = attributes.getValue(ATTRIBUTE_EDGE_TARGET);

            if (id != null) {
                ((edu.uci.ics.jung.graph.Graph) graph).addEdge(id, source, target, EdgeType.DIRECTED);
            } else {
                ((edu.uci.ics.jung.graph.Graph) graph).addEdge(source + "-->" + target, source, target, EdgeType.DIRECTED);
            }
        }

        LOGGER.debug("TAG OPENS. Processing done.");
    }



    public void endElement(String uri, String localName, String qName) throws SAXException {

        LOGGER.debug("TAG CLOSES");

        if (qName.equalsIgnoreCase(TAG_GRAPH)) {
            // add it to the list
            graphs.add(graph);
            graph = null;
        }

        if (qName.equalsIgnoreCase(TAG_YED_NODE_LABEL)) {
            labelTag = false;
        }


        LOGGER.debug("TAG CLOSES. Processing done.");
    }

    @Override
    public void characters(char[] chars, int i, int length) throws SAXException {
        if (labelTag != null && labelTag) {
            String nodeLabel = new String(chars, i, length);
            idLabel.put(currentNodeIs, nodeLabel);
        }
    }

    private void createNode(Object key) {
        if (key != null) {
            LOGGER.debug("Creating node with key: '" + key + "'");

            ((edu.uci.ics.jung.graph.Graph) graph).addVertex(key);

            LOGGER.debug("Done.");
        } else {
            LOGGER.error("Cannt create node! No node 'id' attribute detected!!");
        }
    }

    public Map<String, String> getIdLabel() {
        return idLabel;
    }
}
