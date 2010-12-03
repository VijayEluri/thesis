package se.lnu.thesis.gui.filter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: vady
 * Date: 03.12.2010
 * Time: 21:55:03
 *
 *      Show only directories and .gml files in the FileChooser
 *
 */
public class GMLFileFilter extends FileFilter {

    public static final String DESCRIPTION = "gml file format";

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getName().endsWith(".gml");
    }

    @Override
    public String getDescription() {
        return GMLFileFilter.DESCRIPTION;
    }

}
