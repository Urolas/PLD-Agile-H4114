package filecontrol;
import model.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RoadMapGenerator {

    public RoadMapGenerator(CityMap citymap){
    }

    public static void generateRoadmap(CityMap cityMap) {
        System.out.println("Generating Roadmap");

        JFrame saveFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file path and name");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT File","txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML File","xml"));
        fileChooser.setAcceptAllFileFilterUsed(false);


        int filePath = fileChooser.showSaveDialog(saveFrame);


        if (filePath == JFileChooser.APPROVE_OPTION) {
            File newFile = fileChooser.getSelectedFile();

            if(fileChooser.getFileFilter().getDescription()=="TXT File"){
                if(!newFile.getAbsolutePath().endsWith(".txt")){
                    newFile = new File(newFile.getAbsolutePath()+".txt");
                }else{
                    newFile = new File(newFile.getAbsolutePath());
                }

            }else{
                if(!newFile.getAbsolutePath().endsWith(".xml")){
                    newFile = new File(newFile.getAbsolutePath()+".xml");
                }else{
                    newFile = new File(newFile.getAbsolutePath());
                }

            }

            if(newFile.exists()){
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to override existing file?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
                    filePath = JFileChooser.CANCEL_OPTION;
                }
            }
            if(filePath == JFileChooser.APPROVE_OPTION) {
                System.out.println("Save as file: " + newFile.getAbsolutePath());
                try {

                    if(newFile.getName().endsWith(".txt")){
                        FileWriter fw = new FileWriter(newFile);
                        writeRoadmapTxt(fw,cityMap);
                        fw.close();
                    }else{
                        writeRoadmapXml(cityMap, newFile);
                    }

                } catch (IOException e) {
                    System.out.println(e);
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }

        }else if(filePath == JFileChooser.CANCEL_OPTION){
            System.out.println("Operation cancelled");
        }else{
            System.out.println("Error");
        }

    }


    public static void writeRoadmapTxt( FileWriter fw, CityMap cityMap) throws IOException {

        TXTSerializer.save(fw, cityMap);

    }


    public static void writeRoadmapXml(CityMap cityMap, File file) throws XMLException, ParserConfigurationException, TransformerException {
        XMLSerializer.save(cityMap, file);
    }

}
