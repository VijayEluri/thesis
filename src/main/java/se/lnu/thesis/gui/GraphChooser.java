package se.lnu.thesis.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lnu.thesis.core.MyGraph;
import se.lnu.thesis.gui.filter.GMLFileFilter;
import se.lnu.thesis.gui.filter.YedGMLFileFilter;
import se.lnu.thesis.gui.filter.YedGraphmlFileFilter;
import se.lnu.thesis.io.IOFacade;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 26.10.2010
 * Time: 14:48:12
 */
@Service
public class GraphChooser extends JFileChooser {

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphChooser.class);

    @Autowired
    private IOFacade ioFacade;

    @PostConstruct
    public void init() {
        setAcceptAllFileFilterUsed(false);

        addChoosableFileFilter(new YedGraphmlFileFilter());
        addChoosableFileFilter(new YedGMLFileFilter());

        GMLFileFilter gmlFileFilter = new GMLFileFilter();
        addChoosableFileFilter(gmlFileFilter);
        setFileFilter(gmlFileFilter);
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
                return ioFacade.loadMyGraphFromYedGml(this.getSelectedFile());
            }

            if (this.getFileFilter().getDescription().equals(GMLFileFilter.DESCRIPTION)) {
                return ioFacade.loadMyGraphFromGml(this.getSelectedFile());
            }
        }

        return null;
    }

    public static void main(String[] args) {
        GraphChooser fileChooser = new GraphChooser();
        fileChooser.init();

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println(fileChooser.getFileFilter().getDescription());
            System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }


}
