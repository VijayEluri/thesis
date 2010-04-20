package se.lnu.thesis.gui;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 20.04.2010
 * Time: 17:44:22
 * <p/>
 * Factory class to create MainMenu class
 */
public class MainMenuFactory {

    private static MainMenuFactory instance = new MainMenuFactory();

    public static MainMenuFactory getInstance() {
        return instance;
    }

    public static String GO_APPLET_MENU_LAYOUT = "Layout";
    public static String GO_APPLET_MENU_LAYOUT_JUNG_DAG = "JUNG DAG layout";


    private MainMenuFactory() {

    }


    public JMenuBar createGOAppletMenu(ActionListener actionListener) {
        JMenuBar result = new JMenuBar();

        constructMenu(result, GO_APPLET_MENU_LAYOUT, new String[]{GO_APPLET_MENU_LAYOUT_JUNG_DAG}, actionListener);

        return result;
    }

    public void constructMenu(JMenuBar menuBar, String menuName, String[] elements, ActionListener actionListener) {
        JMenu menu = new JMenu(menuName);

        for (String name : elements) {
            if (name.equals("-")) {
                menu.addSeparator();
            } else {
                menu.add(menuItem(name, name, actionListener));
            }
        }

        menuBar.add(menu);
    }

    protected JMenuItem menuItem(String name, String text, ActionListener actionListener) {
        JMenuItem result = new JMenuItem();

        result.setName(name);
        result.setText(text);

        result.addActionListener(actionListener);

        return result;
    }


}
