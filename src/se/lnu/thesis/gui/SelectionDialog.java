package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.04.2010
 * Time: 15:59:56
 * <p/>
 * Gene Ontology node selectable list
 */
public class SelectionDialog extends JFrame implements ListSelectionListener, Subject {

    public static final Logger LOGGER = Logger.getLogger(SelectionDialog.class);

    private JList list;

    private MyGraph graph;
    private Map<Integer, Object> indexNode;

    private DefaultListModel labels;

    private Set<Observer> observers;

    private Extractor extractor;

    public SelectionDialog() {
        setSize(150, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        labels = new DefaultListModel();

        list = new JList();
        list.setModel(labels);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane);

        observers = new HashSet<Observer>();

        extractor = new Extractor();
    }

    public void initListContent(MyGraph graph) {
        this.graph = graph;
        labels.clear();

        if (indexNode == null) {
            indexNode = new HashMap<Integer, Object>();
        } else {
            indexNode.clear();
        }

        int i = 0;
        for (Object o : graph.getVertices()) {
            labels.add(i, graph.getLabel(o));
            indexNode.put(i, o);
            i++;
        }

    }

    public boolean registerObserver(se.lnu.thesis.myobserver.Observer observer) {
        return observers.add(observer);
    }

    public boolean unregisterObserver(se.lnu.thesis.myobserver.Observer observer) {
        return observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.notifyObserver(this, extractor);
        }
    }


    /**
     * Get selected node key object if selected otherwise null
     *
     * @return Node key object or null
     */
    public Object getSelectedNode() {
        int index = list.getSelectedIndex();

        if (index > -1) {
            return indexNode.get(index);
        } else {
            return null;
        }
    }

    public Object getSelectedLabel() {
        if (getSelectedNode() != null) {
            return graph.getLabel(getSelectedNode());
        } else {
            return null;
        }
    }

    public void showIt() {
        setVisible(true);
    }

    public void valueChanged(ListSelectionEvent event) {
        // TODO if same tag than unselect!

        if (!event.getValueIsAdjusting()) {

            if (list.getSelectedIndex() > -1) {

                LOGGER.debug(SelectionDialog.this.getSelectedNode() + " -> " + SelectionDialog.this.getSelectedLabel());

                LOGGER.debug("Extracting subgraph..");

                list.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                extractor.extractSubGraphs(Scene.getInstance().getGoGraph(), Scene.getInstance().getClusterGraph(), getSelectedNode());

                list.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                LOGGER.debug("Done.");
            }

            notifyObservers();
        }

    }


    /**
     * For testing purpose only!
     */
    @Deprecated
    public static void main(String[] args) {

        MyGraph graph = new MyGraph();

        for (int i = 0; i < 100; i++) {
            graph.addVertex(i);
            graph.addLabel(i, "-> " + i);
        }

        SelectionDialog selectionDialog = new SelectionDialog();
        selectionDialog.initListContent(graph);
        selectionDialog.setVisible(true);

    }

}
