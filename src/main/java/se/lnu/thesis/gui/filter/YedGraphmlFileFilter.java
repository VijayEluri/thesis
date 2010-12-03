package se.lnu.thesis.gui.filter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
* User: vady
* Date: 03.12.2010
* Time: 21:56:07
 *
 *      
 *
*/
public class YedGraphmlFileFilter extends FileFilter {

    public static final String DESCRIPTION = "yEd graphml file format";

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getName().endsWith(".graphml");
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

}
