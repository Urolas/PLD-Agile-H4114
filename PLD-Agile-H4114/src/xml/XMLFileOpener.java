package xml;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author 4IF-4114
 */
public class XMLFileOpener extends FileFilter {

    private static XMLFileOpener instance = null;
    private void XMLfileOpener(){}
    protected static XMLFileOpener getInstance(){
        if (instance == null) instance = new XMLFileOpener();
        return instance;
    }

    public File open(boolean read) throws XMLException{
        int returnVal;
        JFileChooser jFileChooserXML = new JFileChooser();
        jFileChooserXML.setFileFilter(this);
        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (read)
            returnVal = jFileChooserXML.showOpenDialog(null);
        else
            returnVal = jFileChooserXML.showSaveDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION)
            throw new XMLException("Problem when opening file");
        return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
    }

    @Override
    public boolean accept(File f) {
        if (f == null) return false;
        if (f.isDirectory()) return true;
        String extension = getExtension(f);
        if (extension == null) return false;
        return extension.contentEquals("xml");
    }



    @Override
    public String getDescription() {
        return "XML file";
    }

    private String getExtension(File f) {
        String filename = f.getName();
        int i = filename.lastIndexOf('.');
        if (i>0 && i<filename.length()-1)
            return filename.substring(i+1).toLowerCase();
        return null;
    }
}