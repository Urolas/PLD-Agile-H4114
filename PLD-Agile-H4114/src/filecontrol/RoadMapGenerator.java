package filecontrol;
import model.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML File","xml"));
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
                    FileWriter fw = new FileWriter(newFile);
                    writeRoadmapTxt(fw,cityMap);
                    fw.close();
                } catch (IOException e) {
                    System.out.println(e);
                }

            }
        }else if(filePath == JFileChooser.CANCEL_OPTION){
            System.out.println("Operation cancelled");
        }else{
            System.out.println("Error");
        }

    }

    public static void writeRoadmapTxt( FileWriter fw, CityMap cityMap) throws IOException {

        boolean start = true;
        List<PointOfInterest> pointList = cityMap.getTour().getPointOfInterests();
        List<Path> pathList = cityMap.getTour().getPaths();
        int arrivalTime = cityMap.getDistribution().getDepot().getDepartureTime().toSecondOfDay();

        for (int poiNum = 0; poiNum < pointList.size(); poiNum++) {
            PointOfInterest poi = pointList.get(poiNum);

            if (poi.getIdPointOfInterest() == 0) { // Depot
                if(start) {
                    fw.write("Start point" + System.lineSeparator());

                }else{
                    fw.write("to End point"+System.lineSeparator());
                }
            }else {

                if (poi instanceof DeliveryAddress) {
                    fw.write("to Delivery Point #" + poi.getIdPointOfInterest()+System.lineSeparator());
                } else if (poi instanceof PickupAddress) {
                    fw.write("to Pickup Point #" + poi.getIdPointOfInterest()+System.lineSeparator());
                }
            }

            int duration=0;

            if(poiNum<pointList.size() && !start) {
                double length = 0;
                String name;
                int nbIntersection = 0;
                int durationRoad = 0;

                Path path = (Path) (pathList.get(poiNum-1));
                for (int j = 0; j < path.getRoads().size(); ++j) {
                    duration += (int) (path.getRoads().get(j).getLength() / 15000. * 3600.);
                    durationRoad += (int) (path.getRoads().get(j).getLength() / 15000. * 3600.);
                    length += path.getRoads().get(j).getLength();
                    name = path.getRoads().get(j).getName();
                    nbIntersection += 1;

                    if (j+1 < path.getRoads().size() && name.equals(path.getRoads().get(j+1).getName())) {
                        continue;
                    }

                    fw.write(" via " + name);
                    int minutes = (duration / 60);
                    int seconds = (duration % 60);
                    if (minutes > 0) {
                        fw.write(" for " + minutes + "min" + seconds + "s (" + String.format("%,.0f", length)+ " m) " + nbIntersection + " intersections"+System.lineSeparator());
                    }else {
                        fw.write(" for " + seconds + "s (" + String.format("%,.0f", length)+ " m) " + nbIntersection + " intersections"+System.lineSeparator());
                    }

                    durationRoad = 0;
                    length = 0;
                    nbIntersection = 0;
                }
            }

            arrivalTime += poi.getDuration()+duration;
            int hours = arrivalTime / 3600;
            int minutes = (arrivalTime % 3600) / 60;
            int seconds = arrivalTime % 60;


            fw.write("    Latitude: " + poi.getIntersection().getLatitude()+System.lineSeparator());
            fw.write("    Longitude: " + poi.getIntersection().getLongitude()+System.lineSeparator());
            fw.write("    Duration: " + poi.getDuration() + " seconds"+System.lineSeparator());
            if(start) {
                fw.write("    Departure Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds) + System.lineSeparator());
                start=false;
            }else{
                fw.write("    Arrival Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds) + System.lineSeparator());
            }

            fw.write(System.lineSeparator()+System.lineSeparator());

        }


    }

}
