package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import se.lnu.thesis.Scene;
import se.lnu.thesis.algorithm.Extractor;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.04.2010
 * Time: 15:59:56
 * <p/>
 * Gene Ontology node selectable list
 */
@org.springframework.stereotype.Component
public class GeneListDialog extends JFrame implements Subject {

    public static final Logger LOGGER = Logger.getLogger(GeneListDialog.class);

    private MyGraph graph;

    private JList list;
    private DefaultListModel labels;

    private Set<Observer> observers;

    @Autowired
    private Extractor extractor;

    @Autowired
    private Scene scene;

    public GeneListDialog() {
        observers = new HashSet<Observer>();
    }

    @PostConstruct
    protected void initGUI() {
        this.setSize(150, 300);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.add(createSearchField(), BorderLayout.NORTH);

        this.add(createGeneList(), BorderLayout.CENTER);
    }

    protected JComponent createGeneList() {
        labels = new DefaultListModel();

        list = new JList();
        list.setModel(labels);
        list.setSelectionModel(new DefaultListSelectionModel() {

            public void setSelectionInterval(int index0, int index1) {
                if (isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.setSelectionInterval(index0, index1);
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (list.getSelectedIndex() > -1) {
                    LOGGER.info("User selected label: " + getSelectedLabel());

                    LOGGER.info("Extracting subgraph..");

                    list.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    extractor.extract(scene.getGoGraph(), scene.getClusterGraph(), getSelectedNode());

                    list.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                    LOGGER.info("Done.");

                } else {
                    LOGGER.info("User unselected node.");

                    extractor.reset();
                }

                notifyObservers();
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    protected JTextField createSearchField() {
        JTextField search = new JTextField();
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                String match = ((JTextField) keyEvent.getSource()).getText();

                LOGGER.debug("User typed in the search field: " + match);

                filterLabels(match);
            }
        });
        return search;
    }

    /**
     * Show only labels that contain matching substring
     *
     * @param match String to filter by
     */
    public void filterLabels(String match) {
        clearContent();

        if (graph != null) {
            for (Iterator<String> iterator = graph.getLabelsIterator(); iterator.hasNext(); ) {
                String label = iterator.next();
                if (label.contains(match)) {
                    labels.addElement(label);
                }
            }
        }
    }

    /**
     * Fill labels list with all labels from graph
     */
    public void fillContent() {
        clearContent();

        if (graph != null) {
            for (Iterator<String> iterator = graph.getLabelsIterator(); iterator.hasNext(); ) {
                String label = iterator.next();
                labels.addElement(label);
            }
        }
    }

    /**
     * Remove all labels from the list
     */
    public void clearContent() {
        labels.clear();
    }

    public void setGraph(MyGraph graph) {
        this.graph = graph;
        fillContent();
    }

    public boolean registerObserver(se.lnu.thesis.myobserver.Observer observer) {
        return observers.add(observer);
    }

    public boolean unregisterObserver(se.lnu.thesis.myobserver.Observer observer) {
        return observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.notifyObserver(this, null);
        }
    }


    /**
     * @return Get selected vertex id object if selected otherwise null
     */
    public Object getSelectedNode() {
        String selectedLabel = getSelectedLabel();

        if (selectedLabel != null) {
            return graph.getNodeByLabel(selectedLabel);
        } else {
            return null;
        }
    }

    /**
     * @return Get current selected labelm if not selected then null.
     */
    public String getSelectedLabel() {
        return (String) list.getSelectedValue();
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

        GeneListDialog geneListDialog = new GeneListDialog();
        geneListDialog.initGUI();
        geneListDialog.setGraph(graph);
        geneListDialog.setVisible(true);
    }

}
