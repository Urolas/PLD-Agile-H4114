/**
 * TXTSerializer
 * @author 4IF-4114
 */
package filecontrol;

import model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Write the roadmap into the created .txt file
 * */
public class TXTSerializer {

    /**
     * Constructor of TXTSerializer
     */
    public TXTSerializer(){
    }

    /**
     * Write the roadmap on the empty .txt file
     * @param fw the FileWriter connected to the created file
     * @param citymap the current Citymap with a computed tour
     * @throws IOException when path is not found
     */
    public static void save(FileWriter fw, CityMap cityMap) throws IOException {
        List<PointOfInterest> pointList = cityMap.getTour().getPointOfInterests();
        List<Path> pathList = cityMap.getTour().getPaths();
        int arrivalTime = cityMap.getDistribution().getDepot().getDepartureTime().toSecondOfDay();

        for (int poiNum = 0; poiNum < pointList.size(); poiNum++) {
            PointOfInterest poi = pointList.get(poiNum);

            if (poi.getIdPointOfInterest() == 0) { // Depot
                if(poiNum==0) {
                    fw.write("[ Start point ]" + System.lineSeparator());

                }else{
                    fw.write("[ to End point ]"+System.lineSeparator());
                }
            }else {

                if (poi instanceof DeliveryAddress) {
                    fw.write("[ to Delivery Point #" + ((poi.getIdPointOfInterest()-2)/2+1)+" ]"+System.lineSeparator());
                } else if (poi instanceof PickupAddress) {
                    fw.write("[ to Pickup Point #" + ((poi.getIdPointOfInterest()-1)/2+1)+" ]"+System.lineSeparator());
                }
            }

            if(poiNum<pointList.size() && !(poiNum==0)) {
                double length = 0;
                String name;
                int nbIntersection = 0;
                int durationRoad = 0;
                fw.write(System.lineSeparator()+"    DRIVE:"+System.lineSeparator());
                Path path = (Path) (pathList.get(poiNum-1));
                for (int j = 0; j < path.getRoads().size(); ++j) {
                    durationRoad += (int) (path.getRoads().get(j).getLength() / 15000. * 3600.);
                    length += path.getRoads().get(j).getLength();
                    name = path.getRoads().get(j).getName();
                    nbIntersection += 1;

                    if (j+1 < path.getRoads().size() && name.equals(path.getRoads().get(j+1).getName())) {
                        continue;
                    }

                    fw.write("    via " + name);
                    int minutes = (durationRoad / 60);
                    int seconds = (durationRoad % 60);
                    if (minutes > 0) {
                        fw.write(" for " + minutes + "min" + seconds + "s (" + String.format("%,.0f", length)+ " m) " + nbIntersection + " intersections"+System.lineSeparator());
                    }else {
                        fw.write(" for " + seconds + "s (" + String.format("%,.0f", length)+ " m) " + nbIntersection + " intersections"+System.lineSeparator());
                    }

                    durationRoad = 0;
                    length = 0;
                    nbIntersection = 0;
                }

                arrivalTime += (int)(path.getLength() / 15000. * 3600.);

            }

            int hours = arrivalTime / 3600;
            int minutes = (arrivalTime % 3600) / 60;
            int seconds = arrivalTime % 60;

            int departureTime = arrivalTime+poi.getDuration();
            int hours2 = departureTime / 3600;
            int minutes2 = (departureTime % 3600) / 60;
            int seconds2 = departureTime % 60;

            int duration = poi.getDuration();
            if(poiNum!=0){
                fw.write(System.lineSeparator()+"    ARRIVAL:");
            }

            fw.write(System.lineSeparator()+"    Latitude: " + poi.getIntersection().getLatitude()+System.lineSeparator());
            fw.write("    Longitude: " + poi.getIntersection().getLongitude()+System.lineSeparator());
            if(poiNum==0) {
                fw.write("    Departure time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds) + System.lineSeparator());
            }else if(poiNum!=pointList.size()-1){
                fw.write("    Arrival time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds) + System.lineSeparator());
                fw.write("    Duration: " + String.format("%02dmin%02dsec", (duration % 3600) / 60, duration % 60) +System.lineSeparator());
                fw.write("    Departure time: " + String.format("%02d:%02d:%02d", hours2, minutes2, seconds2) + System.lineSeparator());
            }else{
                fw.write("    Arrival time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds) + System.lineSeparator());
            }

            arrivalTime+=poi.getDuration();

            fw.write(System.lineSeparator()+"-----------------------------------------------------------------------"+System.lineSeparator());

        }
    }

}
