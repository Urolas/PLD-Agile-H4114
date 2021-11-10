/**
 * XMLFileOpener
 * @author 4IF-4114
 */

package filecontrol;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Allow the user to open a .xml file from their local storage
 * */
public class XMLFileOpener extends FileFilter {

    private static XMLFileOpener instance = null;

    /**
     * Constructor of XMLfileOpener
     */
    private void XMLfileOpener(){}
    protected static XMLFileOpener getInstance(){
        if (instance == null) instance = new XMLFileOpener();
        return instance;
    }

    /**
     * Open a JFileChooser to allow the user to pick a .xml for the parsing
     * @param read indicates if the selected file has the right format
     * @return the selected File
     */
    public File open(boolean read) throws XMLException{
        int returnVal;
        JFileChooser jFileChooserXML = new JFileChooser();
        jFileChooserXML.setFileFilter(this);

        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (read)
            returnVal = jFileChooserXML.showOpenDialog(null);
        else
            returnVal = jFileChooserXML.showSaveDialog(null);
        if (returnVal == JFileChooser.CANCEL_OPTION)
            throw new XMLException("Problem when opening file");
        else if (returnVal == JFileChooser.ERROR_OPTION)
            throw new XMLException("Problem when opening file");
        return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
    }

    @Override
    /**
     * Indicates if the selected file can be read or not
     * @param File the selected File
     * @return boolean value indicating if the selected file works
     */
    public boolean accept(File f) {
        if (f == null) return false;
        if (f.isDirectory()) return true;
        String extension = getExtension(f);
        if (extension == null) return false;
        return extension.contentEquals("xml");
    }



    @Override
    /**
     * Show the description of an extension
     * @return the description of a .XML file extension
     */
    public String getDescription() {
        return "XML file";
    }

    /**
     * Send a boolean value indicating if the selected file works
     * @param File the selected File
     * @return the extension of the file
     */
    private String getExtension(File f) {
        String filename = f.getName();
        int i = filename.lastIndexOf('.');
        if (i>0 && i<filename.length()-1)
            return filename.substring(i+1).toLowerCase();
        return null;
    }
}