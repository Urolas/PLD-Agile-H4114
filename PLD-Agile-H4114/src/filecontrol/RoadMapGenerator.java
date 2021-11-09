package filecontrol;
import model.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
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
                newFile = new File(newFile.getAbsolutePath()+".txt");
            }else{
                newFile = new File(newFile.getAbsolutePath()+".xml");
            }

            if(newFile.exists()){
                System.out.println("File already exist");
            }else {
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
