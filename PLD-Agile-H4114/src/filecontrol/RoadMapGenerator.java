/**
 * RoadMapGenerator
 *
 * @author 4IF-4114
 */

package filecontrol;

import model.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * Generate the roadmap (.txt or .xml) and save the file in the folder selected by the user
 * */
public class RoadMapGenerator {

    /**
     * Constructor of RoadMapGenerator
     */
    public RoadMapGenerator() {
    }

    /**
     * Open a JFileChooser to allow the user to pick the path,the file's name and the file's extension (.txt or .xml)
     * and create a new file
     * @param cityMap the current CityMap with a computed tour
     */
    public static void generateRoadmap(CityMap cityMap) {

        //Make a pop-up-panel JFileChooser to allow the user to choose where to save the file
        JFrame saveFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Generate Roadmap");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML File", "xml"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int filePath = fileChooser.showSaveDialog(saveFrame);


        if (filePath == JFileChooser.APPROVE_OPTION) { //If the user click on the save button

            //Get the path where to save the file
            File newFile = fileChooser.getSelectedFile();

            //If the user choose to save the file as a TXT file (Filter)
            if (Objects.equals(fileChooser.getFileFilter().getDescription(), "TXT File")) {

                //Add the extension .txt to the file name
                if (!newFile.getAbsolutePath().endsWith(".txt")) {
                    newFile = new File(newFile.getAbsolutePath() + ".txt");
                } else {
                    //if the user try to overwrite a .txt file, do not add .txt to the file name
                    newFile = new File(newFile.getAbsolutePath());
                }

            } else { //If the user choose to save the file as a XML file (Filter)

                //Add the extension .xml to the file name
                if (!newFile.getAbsolutePath().endsWith(".xml")) {
                    newFile = new File(newFile.getAbsolutePath() + ".xml");
                } else {
                    //if the user try to overwrite a .xml file, do not add .txt to the file name
                    newFile = new File(newFile.getAbsolutePath());
                }

            }

            //If the file already exist, ask if the user wishes the overwrite the file
            if (newFile.exists()) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to override existing file?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
                    filePath = JFileChooser.CANCEL_OPTION;
                }
            }

            //If the user choose to overwrite the file
            if (filePath == JFileChooser.APPROVE_OPTION) {
                try {

                    //If the overwritten file is a txt
                    if (newFile.getName().endsWith(".txt")) {

                        //Write the file
                        FileWriter fw = new FileWriter(newFile);
                        writeRoadmapTxt(fw, cityMap);
                        fw.close();

                    } else { //if the file is a xml
                        writeRoadmapXml(cityMap, newFile);
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }


        } else if (filePath == JFileChooser.CANCEL_OPTION) { // If the user click on the cancel button
        } else {
            System.out.println("Error");
        }
    }

    /**
     * Write the roadmap into a .txt file
     * @param fw a FileWriter connected to the created .txt file
     * @param cityMap the current Citymap with a computed tour
     * @throws IOException when path is not found
     */
    public static void writeRoadmapTxt(FileWriter fw, CityMap cityMap) throws IOException {

        TXTSerializer.save(fw, cityMap);

    }

    /**
     * Write the roadmap into a .xml file
     * @param cityMap the current Citymap with a computed tour
     * @param file the created .xml file
     * @throws XMLException when root element is missing
     * @throws ParserConfigurationException when the documentBuilder can not parse the following element
     * @throws TransformerException when the created file can not be processed
     */
    public static void writeRoadmapXml(CityMap cityMap, File file) throws XMLException,
            ParserConfigurationException, TransformerException {
        XMLSerializer.save(cityMap, file);
    }

}
