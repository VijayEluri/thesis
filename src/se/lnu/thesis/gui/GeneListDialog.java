package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.Scene;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.myobserver.Observer;
import se.lnu.thesis.myobserver.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class GeneListDialog extends JFrame implements Subject {

    public static final Logger LOGGER = Logger.getLogger(GeneListDialog.class);

    private JList list;

    private MyGraph graph;
    private Map<Integer, Object> indexNode;

    private DefaultListModel labels;

    private Set<Observer> observers;

    public GeneListDialog() {
        initGUI();

        observers = new HashSet<Observer>();
    }

    private void initGUI() {
        this.setSize(150, 300);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.add(searchField(), BorderLayout.NORTH);

        this.add(geneList(), BorderLayout.CENTER);
    }

    protected JComponent geneList() {
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
                    LOGGER.info("User selected node.. " + list.getSelectedValue());

                    LOGGER.info("Extracting subgraph..");

                    list.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    Scene.getInstance().getExtractor().extractSubGraphs(Scene.getInstance().getGoGraph(), Scene.getInstance().getClusterGraph(), getSelectedNode());

                    list.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                    LOGGER.info("Done.");

                } else {
                    LOGGER.info("User unselected node.");

                    Scene.getInstance().getExtractor().extractSubGraphs(null, null, null);
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

    private JTextField searchField() {
        JTextField search = new JTextField();
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                String search = ((JTextField) keyEvent.getSource()).getText();

                LOGGER.debug("User typed in the search field: " + search);

                fillContent(search);
            }
        });
        return search;
    }

    protected void fillContent(String match) {
        if (graph != null) {
            labels.clear();

            if (indexNode == null) {
                indexNode = new HashMap<Integer, Object>();
            } else {
                indexNode.clear();
            }

            int i = 0;
            for (Object o : graph.getVertices()) { // TODO LabelsIterator after fixing dublicates in the Gene Ontology data file
                String label = graph.getLabel(o);
                if (match != null) {
                    if (label.contains(match)) {
                        labels.add(i, label);
                        indexNode.put(i, o);
                        i++;
                    }
                } else {
                    labels.add(i, label);
                    indexNode.put(i, o);
                    i++;
                }
            }
        }
    }

    protected void fillContent() {
        fillContent(null);
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
        geneListDialog.setGraph(graph);
        geneListDialog.setVisible(true);

    }

}
