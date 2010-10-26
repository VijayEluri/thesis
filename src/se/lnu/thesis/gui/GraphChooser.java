package se.lnu.thesis.gui;

import org.apache.log4j.Logger;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.io.IOFacade;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.10.2010
 * Time: 14:48:12
 */
public class GraphChooser extends JFileChooser {

    public static final Logger LOGGER = Logger.getLogger(GraphChooser.class);

    private IOFacade ioFacade;

    public GraphChooser() {
        init();
    }

    private void init() {
        setAcceptAllFileFilterUsed(false);

        addChoosableFileFilter(new YedGraphmlFileFilter());
        addChoosableFileFilter(new YedGMLFileFilter());
        addChoosableFileFilter(new GMLFileFilter());

        ioFacade = new IOFacade();
    }

    public MyGraph open() {
        if (this.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            LOGGER.info("File selected.. ");
            LOGGER.info("File filter '" + this.getFileFilter().getDescription() + "'");
            LOGGER.info("File '" + this.getSelectedFile().getName() + "'");

            if (this.getFileFilter().getDescription().equals(YedGraphmlFileFilter.DESCRIPTION)) {
                return ioFacade.loadFromYedGraphml(this.getSelectedFile());
            }

            if (this.getFileFilter().getDescription().equals(YedGMLFileFilter.DESCRIPTION)) {
                return ioFacade.loadFromYedGml(this.getSelectedFile());
            }

            if (this.getFileFilter().getDescription().equals(GMLFileFilter.DESCRIPTION)) {
                return ioFacade.loadFromGml(this.getSelectedFile());
            }
        }

        return null;
    }

    private class YedGraphmlFileFilter extends FileFilter {

        public static final String DESCRIPTION = "yEd graphml file format";

        @Override
        public boolean accept(File file) {
            return file.isFile() && file.getName().endsWith(".graphml");
        }

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

    }

    private class GMLFileFilter extends FileFilter {

        public static final String DESCRIPTION = "gml file format";

        @Override
        public boolean accept(File file) {
            return file.isFile() && file.getName().endsWith(".gml");
        }

        @Override
        public String getDescription() {
            return GMLFileFilter.DESCRIPTION;
        }

    }

    private class YedGMLFileFilter extends GMLFileFilter {

        public static final String DESCRIPTION = "yEd gml file format";

        @Override
        public String getDescription() {
            return YedGMLFileFilter.DESCRIPTION;
        }

    }

    public static void main(String[] args) {
        GraphChooser fileChooser = new GraphChooser();

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println(fileChooser.getFileFilter().getDescription());
            System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }


}
