/**
 * TXTSerializer
 * @author 4IF-4114
 */
package filecontrol;

import model.*;

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
     * @param cityMap the current Citymap with a computed tour
     * @throws IOException when path is not found
     */
    public static void save(FileWriter fw, CityMap cityMap) throws IOException {

        //Get the information from the tour
        List<PointOfInterest> pointList = cityMap.getTour().getPointOfInterests();
        List<Path> pathList = cityMap.getTour().getPaths();
        int arrivalTime = cityMap.getDistribution().getDepot().getDepartureTime().toSecondOfDay();

        //Loop checking each point of interest from the list
        for (int poiNum = 0; poiNum < pointList.size(); poiNum++) {
            PointOfInterest poi = pointList.get(poiNum);

            //Write the header of each point of interest
            if (poi.getIdPointOfInterest() == 0) { // If the point is a depot
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

            //If the point of interest is not the start point
            if(poiNum<pointList.size() && !(poiNum==0)) {
                double length = 0;
                String name;
                int nbIntersection = 0;
                int durationRoad = 0;

                //Write the path the deliveryman must pass by to reach the next point
                fw.write(System.lineSeparator()+"    DRIVE:"+System.lineSeparator());
                Path path = (Path) (pathList.get(poiNum-1));

                //Loop checking each road of the path that reach the current point of interest
                for (int j = 0; j < path.getRoads().size(); ++j) {

                    //Merge roads if they are the same road separated by intersections
                    durationRoad += (int) (path.getRoads().get(j).getLength() / 15000. * 3600.);
                    length += path.getRoads().get(j).getLength();
                    name = path.getRoads().get(j).getName();
                    nbIntersection += 1;

                    //Check if they are the same road, if yet, skip the steps below
                    if (j+1 < path.getRoads().size() && name.equals(path.getRoads().get(j+1).getName())) {
                        continue;
                    }

                    //Write the information about the current road : name, duration, length, number of intersections
                    fw.write("    via " + name);

                    //Calculate the time staying on the road
                    int minutes = (durationRoad / 60);
                    int seconds = (durationRoad % 60);
                    if (minutes > 0) {
                        fw.write(" for " + minutes + "min" + seconds + "s (" + String.format("%,.0f", length)+ " m) "
                                + nbIntersection + " intersections"+System.lineSeparator());
                    }else {
                        fw.write(" for " + seconds + "s (" + String.format("%,.0f", length)+ " m) " + nbIntersection
                                + " intersections"+System.lineSeparator());
                    }

                    durationRoad = 0;
                    length = 0;
                    nbIntersection = 0;
                }

                //Calculate the time of arrival to the point of interest
                arrivalTime += (int)(path.getLength() / 15000. * 3600.);

            }

            //Convert the time in seconds into hours:minutes:seconds
            int hours = arrivalTime / 3600;
            int minutes = (arrivalTime % 3600) / 60;
            int seconds = arrivalTime % 60;

            //Calculate the time to depart from the point
            int departureTime = arrivalTime+poi.getDuration();
            int hours2 = departureTime / 3600;
            int minutes2 = (departureTime % 3600) / 60;
            int seconds2 = departureTime % 60;

            //Write the information about the point of interest : arrival time, departure time, latitude, longitude, duration
            int duration = poi.getDuration();
            if(poiNum!=0){
                fw.write(System.lineSeparator()+"    ARRIVAL:");
            }

            fw.write(System.lineSeparator()+"    Latitude: " + poi.getIntersection().getLatitude()
                        + System.lineSeparator());
            fw.write("    Longitude: " + poi.getIntersection().getLongitude()+System.lineSeparator());
            if(poiNum==0) {
                fw.write("    Departure time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)
                        + System.lineSeparator());
            }else if(poiNum!=pointList.size()-1){
                fw.write("    Arrival time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)
                        + System.lineSeparator());
                fw.write("    Duration: " + String.format("%02dmin%02dsec", (duration % 3600) / 60, duration % 60)
                        +System.lineSeparator());
                fw.write("    Departure time: " + String.format("%02d:%02d:%02d", hours2, minutes2, seconds2)
                        + System.lineSeparator());
            }else{
                fw.write("    Arrival time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)
                        + System.lineSeparator());
            }

            //Add the duration to the arrival time for the next point
            arrivalTime+=poi.getDuration();

            fw.write(System.lineSeparator()+"-----------------------------------------------------------------------"+System.lineSeparator());

        }
    }
}
