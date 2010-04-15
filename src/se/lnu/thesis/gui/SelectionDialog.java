package se.lnu.thesis.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 11.04.2010
 * Time: 15:59:56
 * <p/>
 * GO node selectable list
 */
public class SelectionDialog extends JFrame {

    private JList list;

    private Map<Object, String> nodeLabel;
    private Map<Integer, Object> indexNode;

    private Observer observer;

    private DefaultListModel labels;

    public SelectionDialog() {
        setSize(150, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        labels = new DefaultListModel();

        list = new JList();
        list.setModel(labels);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new SelectionListener());

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane);
    }

    public void initListContent(Map<Object, String> nodeLabel) {
        this.nodeLabel = nodeLabel;

        labels.clear();

        if (indexNode == null) {
            indexNode = new HashMap<Integer, Object>();
        } else {
            indexNode.clear();
        }

        int i = 0;
        for (Object node : nodeLabel.keySet()) {
            labels.add(i, nodeLabel.get(node));
            indexNode.put(i, node);
            i++;
        }

    }

    public void setObserver(Observer observer) {
        this.observer = observer;
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
            return nodeLabel.get(getSelectedNode());
        } else {
            return null;
        }
    }

    public void showIt() {
        setVisible(true);
    }

    private class SelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent event) {
            // TODO if same element than unselect!

            if (!event.getValueIsAdjusting()) {

                if (list.getSelectedIndex() > -1) {

                    System.out.println(SelectionDialog.this.getSelectedNode() + " -> " + SelectionDialog.this.getSelectedLabel()); // TODO use logger

                    if (SelectionDialog.this.observer != null) {
                        SelectionDialog.this.observer.update(null, null);
                    }
                }

            }

        }
    }


    /**
     * For testing purpose only!
     */
    @Deprecated
    public static void main(String[] args) {

        Map nodeLabel = new HashMap();

        for (int i = 0; i < 100; i++) {
            nodeLabel.put("id" + i, "Label" + i);
        }

        SelectionDialog selectionDialog = new SelectionDialog();
        selectionDialog.initListContent(nodeLabel);
        selectionDialog.setVisible(true);

    }

}
