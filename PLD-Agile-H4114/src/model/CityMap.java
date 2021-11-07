package model;

import tsp.TSP1;
import tsp.TSPDoubleInsertion;
import view.MapView;
import observer.Observable;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import tsp.TSP;
import tsp.TSPplaceholder;

/**
 * @author 4IF-4114
 */
public class CityMap extends Observable {
    private HashMap<AbstractMap.SimpleEntry<String,String>,Road> roads;
    private HashMap<String,Intersection> intersections;
    public Distribution distribution;
    public Tour tour;
    private Double width, height, nordPoint, westPoint;
    private HashMap<String, List<AbstractMap.Entry<String,Double>>> adjacencyList;

    @Override
    public String toString() {
        return "CityMap{" +
                "roads=" + roads+
                '}';
    }



    /**
     * Default constructor
     */
    public CityMap() {
        this.intersections= new HashMap<String,Intersection>();
        this.roads= new HashMap<>();
        this.distribution = new Distribution();
        this.width = 0.;
        this.height = 0.;
        this.nordPoint = 0.;
        this.westPoint = 0.;
        this.tour = new Tour();
        this.adjacencyList= new HashMap<>();


    }

    //compute les meilleurs chemins entre chaque points d'interet, appelle le TSP pour trouver le meilleurs tour, créé le tour
    public void computeTour() {
        //recuperaton des points d'interets
        List<PointOfInterest> points = this.distribution.GetAllPoints();
        HashMap<Integer,Integer> constraints = this.distribution.GetConstraints();
        HashMap<PointOfInterest,HashMap<PointOfInterest,AbstractMap.SimpleEntry<Double,List<String>>>> ResultsDijkstra = new HashMap<>();
        //appel du dijkstra pour chaque point d'intert vers chaque point d'interet
        for (PointOfInterest source : points){
            HashMap<PointOfInterest, AbstractMap.SimpleEntry<Double,List<String>>> distanceToOtherPoints = new HashMap<>();
            for (PointOfInterest target : points){
                if (!Objects.equals(target, source)) {
                    distanceToOtherPoints.put(target,this.computePath(source,target));
                }
            }
            ResultsDijkstra.put(source,distanceToOtherPoints);
        }
        //creation du graph pour le TSP
        GraphPointToPoint graph =new GraphPointToPoint(ResultsDijkstra,constraints);
        HashMap<Integer,PointOfInterest> correspondanceTable = new HashMap<>();
        for (PointOfInterest point : points){
            correspondanceTable.put(point.idPointOfInterest,point);
        }
        //appel du TSP
        TSP tsp = new TSPDoubleInsertion();
        tsp.searchSolution(10000,graph);
        List<PointOfInterest> shortestTour = new LinkedList<>();
        shortestTour.add(points.get(0));
        List<String> shortestPath = new LinkedList<>();
        //traduction du resultat du TSP en données utilisables
        for (int i=1;i<tsp.getBestSolLength();i++) {
            shortestTour.add(correspondanceTable.get(tsp.getSolution(i)));
            shortestPath.addAll(ResultsDijkstra.get(shortestTour.get(i-1)).get(shortestTour.get(i)).getValue());
        }
        AbstractMap.SimpleEntry<String,String> pairIdIntersection;
        List<Path> paths = new LinkedList<>();


         // creation des paths : allant de de point d'interet a point d'interet
        for (int i=1;i< shortestTour.size();i++) {
            List<Road> roadsEndToEnd = new ArrayList<>();
            List<String> intersectionsBetweenPoints = ResultsDijkstra.get(shortestTour.get(i-1)).get(shortestTour.get(i)).getValue();
            for (int j=1;j<intersectionsBetweenPoints.size();j++){
                pairIdIntersection= new AbstractMap.SimpleEntry<>(intersectionsBetweenPoints.get(j-1),intersectionsBetweenPoints.get(j));
                roadsEndToEnd.add(this.roads.get(pairIdIntersection));

            }


            paths.add(new Path(roadsEndToEnd,ResultsDijkstra.get(shortestTour.get(i-1)).get(shortestTour.get(i)).getKey()));
        }
        this.tour.setPaths(paths);
        this.tour.setTotalLength(tsp.getSolutionCost());
        this.tour.setPointOfInterests(shortestTour);
        notifyObservers(this.tour);


    }
    //computePath execute l'algorithme de dijkstra et cherche le meilleur chemin entre deux intersections, il renvoit le chemin et sa longueur
    public AbstractMap.SimpleEntry<Double,List<String>> computePath(PointOfInterest point1, PointOfInterest point2) {

        //Recuperation des id des intersections
        String idDepart = point1.intersection.id;
        String idArrivee = point2.intersection.id;

        //Toutes les intersections non visitee
        HashSet<String> unvisited = new HashSet<>(this.intersections.keySet());

        //Permet d'avoir le chemin obtimal depuis le noeud de depart
        HashMap<String , List<String>> chemin = new HashMap<>();
        for(String key : this.intersections.keySet()){
            chemin.put(key,new LinkedList<>());
        }

        //distance de toutes les intersections mises à inf sauf idDepart qui est à 0
        HashMap<String, Double> distance = new HashMap<>();
        for(String key : this.intersections.keySet()){
            distance.put(key,Double.POSITIVE_INFINITY);
        }
        distance.put(idDepart, 0.0 );

        //Comparator permettant la comparaison de la longeur entre deux intersections
        class dijkstraDistComparator implements Comparator<String>{
            public int compare(String id1, String id2){
                return (int) Math.signum(distance.get(id1)-distance.get(id2));
            }
        }

        //Declaration de la priority queue
        PriorityQueue<String> pq = new PriorityQueue<>(this.intersections.size(),new dijkstraDistComparator());

        //Initialisation de l'algorithme
        pq.add(idDepart);
        String currentNode;

        //Tant qu'il reste des noeuds a parcourir
        while(!pq.isEmpty()){
            //On recupere le noeud avec la distance minimal atteignable
            currentNode = pq.poll();

            //Si ce noeud est notre destination, on break
            if(currentNode.equals(idArrivee)){
                break;
            }

            //On parcours tous les noeuds adjacents de currentNode
            for(Map.Entry<String,Double> e : this.adjacencyList.get(currentNode)){
                //Si il ne sont pas visitees
                if(unvisited.contains(e.getKey())){

                    //Si la distance s'ameliore
                    if(distance.get(currentNode)+e.getValue()<distance.get(e.getKey())){

                        //On update la distance, le chemin et on l'ajoute a la PQ
                        distance.put(e.getKey(),distance.get(currentNode)+e.getValue());
                        pq.add(e.getKey());
                        List<String> listTemp = new LinkedList<>(chemin.get(currentNode));
                        listTemp.add(currentNode);
                        chemin.put(e.getKey(),listTemp);

                    }

                }
            }
            //On marque currentNode visitee
            unvisited.remove(currentNode);
        }

        chemin.get(idArrivee).add(idArrivee);
        return new AbstractMap.SimpleEntry<>(distance.get(idArrivee),chemin.get(idArrivee));
    }


    public void reset() {
        this.distribution.reset();
        this.tour.resetTour();
        this.intersections.clear();
        this.roads.clear();
        this.width=0.0;
        this.height=0.0;
        this.nordPoint=0.0;
        this.westPoint=0.0;
        this.adjacencyList = new HashMap<>();
        notifyObservers();
    }

    public void addIntersection(Intersection intersection) {
        this.intersections.put(intersection.id,intersection);
        notifyObservers(intersection);
    }

    public void addRoad(String name, Double length, String id1, String id2) {
        Intersection origin = this.intersections.get(id1);
        Intersection destination = this.intersections.get(id2);
        Road road = new Road(name,length);
        if(origin!=null && destination!=null){
            road.addRoads(origin,destination);
            this.roads.put(new AbstractMap.SimpleEntry<>(origin.id,destination.id),road);
        }
        notifyObservers(road);
    }

    public void generateRoadmap() {
        System.out.println("Generating Roadmap");

        JFrame saveFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select file path and name");
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("txt File","txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("xml File","xml"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int filePath = fileChooser.showSaveDialog(saveFrame);


        if (filePath == JFileChooser.APPROVE_OPTION) {
            File newFile = fileChooser.getSelectedFile();
            if(newFile.exists()){
                System.out.println("File already exist");
            }else {
                System.out.println("Save as file: " + newFile.getAbsolutePath());
                try {
                    FileWriter fw = new FileWriter(newFile);
                    writeFile(fw, newFile.getName());
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

    public void writeFile( FileWriter fw, String fileName) throws IOException {

        boolean start = true;
        List<PointOfInterest> pointList = this.tour.getPointOfInterests();
        List<Path> pathList = this.tour.getPaths();
        int arrivalTime = this.distribution.getDepot().getDepartureTime().toSecondOfDay(); ;

        for (int poiNum = 0; poiNum < pointList.size(); poiNum++) {
            PointOfInterest poi = pointList.get(poiNum);

            if (poi.getIdPointOfInterest() == 0) { // Depot
                if(start) {
                    fw.write("Start point"+System.lineSeparator());
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

            if(poiNum<pointList.size()-1 && !start) {
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

    public void completeAdjacencyList(String id1, String id2, Double length) {

        this.adjacencyList.get(id1).add(new AbstractMap.SimpleEntry<>(id2,length));

    }
    public void initializeAdjacencyList(String id1) {

        List<AbstractMap.Entry<String,Double>> targets= new ArrayList<>();
        this.adjacencyList.put(id1,targets);

    }

    public Tour getTour() {
        return tour;
    }

    public HashMap<String,Intersection>getIntersections(){
        return this.intersections;
    }

    public void addObserver(MapView mapView) {
        super.addObserver(mapView);
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public HashMap<AbstractMap.SimpleEntry<String, String>, Road> getRoads() {
        return roads;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setNordPoint(Double nordPoint) {
        this.nordPoint = nordPoint;
    }

    public void setWestPoint(Double westPoint) {
        this.westPoint = westPoint;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public Double getNordPoint() {
        return nordPoint;
    }

    public Double getWestPoint() {
        return westPoint;
    }

    public HashMap<String, List<AbstractMap.Entry<String, Double>>> getAdjacencyList() {
        return adjacencyList;
    }
}

