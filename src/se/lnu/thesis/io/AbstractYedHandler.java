package se.lnu.thesis.io;

import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 09.04.2010
 * Time: 0:42:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractYedHandler extends AbstractHandler {

    private static final Logger LOGGER = Logger.getLogger(JungYedHandler.class);

    private static final String TAG_YED_NODE_LABEL = "y:NodeLabel";

    protected Object currentNode;
    protected boolean labelTag = false;
    protected String labelValue = null;

    // Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        LOGGER.debug("TAG OPENS. Start processing..");

        if (qName.equalsIgnoreCase(TAG_GRAPH)) {
            LOGGER.debug("Processing tag.." + TAG_GRAPH);

            startTagGraph();
        }


        if (qName.equalsIgnoreCase(TAG_NODE)) {
            LOGGER.debug("Processing tag.." + TAG_NODE);

            startTagNode(attributes);
        }

        if (qName.equalsIgnoreCase(TAG_YED_NODE_LABEL)) {
            LOGGER.debug("processStarting tag.." + TAG_YED_NODE_LABEL);

            startTagYedNodeLabel();
        }

        if (qName.equalsIgnoreCase(TAG_EDGE)) {
            LOGGER.debug("processStarting tag.." + TAG_EDGE);

            startTagEdge(attributes);
        }

        LOGGER.debug("TAG OPENS. processStarting done.");
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        LOGGER.debug("TAG CLOSES");

        if (qName.equalsIgnoreCase(TAG_GRAPH)) {
            endTagGraph();
        }

        if (qName.equalsIgnoreCase(TAG_YED_NODE_LABEL)) {
            endTagYedNodeLabel();
        }


        LOGGER.debug("TAG CLOSES. Processing done.");
    }

    @Override
    public void characters(char[] chars, int i, int length) throws SAXException {
        if (labelTag) {
            if (labelValue == null) {
                labelValue = new String(chars, i, length);
            } else {
                labelValue = new String(labelValue + new String(chars, i, length));
            }
        }

    }

    protected abstract void labelTagValue(String label);

    protected void startTagEdge(Attributes attributes) {
        String id = attributes.getValue(ATTRIBUTE_ID);

        String source = attributes.getValue(ATTRIBUTE_EDGE_SOURCE);

        String target = attributes.getValue(ATTRIBUTE_EDGE_TARGET);

        if (id != null) {
            ((edu.uci.ics.jung.graph.Graph) graph).addEdge(id, source, target, EdgeType.DIRECTED);
        } else {
            ((edu.uci.ics.jung.graph.Graph) graph).addEdge(source + "-->" + target, source, target, EdgeType.DIRECTED);
        }
    }

    protected abstract void startTagGraph();

    protected void endTagGraph() {
        // add it to the list
        graphs.add(graph);
        graph = null;
    }

    protected void startTagNode(Attributes attributes) {
        String id = attributes.getValue(ATTRIBUTE_ID);
        createNode(id);
        currentNode = id;
    }

    protected void startTagYedNodeLabel() {
        labelTag = true;
    }

    protected void endTagYedNodeLabel() {
        labelTagValue(labelValue);
        labelTag = false;
        labelValue = null;
    }

    protected void createNode(Object key) {
        if (key != null) {
            LOGGER.debug("Creating node with key: '" + key + "'");

            ((edu.uci.ics.jung.graph.Graph) graph).addVertex(key);

            LOGGER.debug("Done.");
        } else {
            LOGGER.error("Cannt create node! No node 'id' attribute detected!!");
        }
    }
}
