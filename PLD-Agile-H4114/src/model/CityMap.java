/**
 * CityMap
 * @author 4IF-4114
 */
package model;

import observer.Observable;
import tsp.TSP;
import tsp.TSPDoubleInsertion;
import view.MapView;
import java.util.*;



/**
 * The citymap of the application, stocking most of the application's data (map and requests)
 */
public class CityMap extends Observable {
    public Intersection i1Selected;
    public Intersection i2Selected;
    public PointOfInterest primaryHighlight;
    public PointOfInterest secondaryHighlight;
    public Distribution distribution;
    public Tour tour;
    private Double width, height, northPoint, westPoint;
    private HashMap<String, List<AbstractMap.Entry<String, Double>>> adjacencyList;
    private Intersection poiToAdd;
    private final HashMap<AbstractMap.SimpleEntry<String, String>, Road> ROADS;
    private final HashMap<String, Intersection> INTERSECTIONS;

    /**
     * Constructor of CityMap
     */
    public CityMap() {
        this.INTERSECTIONS = new HashMap<>();
        this.ROADS = new HashMap<>();
        this.distribution = new Distribution();
        this.width = 0.;
        this.height = 0.;
        this.northPoint = 0.;
        this.westPoint = 0.;
        this.tour = new Tour();
        this.adjacencyList = new HashMap<>();


    }

    /**
     * Compute the best path between each point of interest, call the TSP to find the best tour, and create a tour
     */
    public void computeTour() {
        //Get every points of interest from the Distribution
        List<PointOfInterest> points = this.distribution.GetAllPoints();
        HashMap<Integer, Integer> constraints = this.distribution.GetConstraints();
        HashMap<PointOfInterest, HashMap<PointOfInterest, AbstractMap.SimpleEntry<Double, List<String>>>> ResultsDijkstra = new HashMap<>();
        //Call dijkstra from each point of interest to every other points of interest
        for (PointOfInterest source : points) {
            HashMap<PointOfInterest, AbstractMap.SimpleEntry<Double, List<String>>> distanceToOtherPoints = new HashMap<>();
            for (PointOfInterest target : points) {
                if (!Objects.equals(target, source)) {
                    distanceToOtherPoints.put(target, this.computePath(source, target));
                }
            }
            ResultsDijkstra.put(source, distanceToOtherPoints);
        }
        //create a graph for the TSP
        GraphPointToPoint TSPgraph = new GraphPointToPoint(ResultsDijkstra, constraints);
        HashMap<Integer, PointOfInterest> matchingTable = new HashMap<>();
        for (PointOfInterest point : points) {
            matchingTable.put(point.idPointOfInterest, point);
        }
        //call the TSP
        TSP tsp = new TSPDoubleInsertion();
        tsp.searchSolution(10000, TSPgraph);
        List<PointOfInterest> shortestTour = new LinkedList<>();
        shortestTour.add(points.get(0));
        //translate the result from the TSP into a usable data
        for (int i = 1; i < tsp.getBestSolLength(); i++) {
            shortestTour.add(matchingTable.get(tsp.getSolution(i)));
        }
        AbstractMap.SimpleEntry<String, String> pairIdIntersection;
        List<Path> paths = new LinkedList<>();


        // create paths: from a point of interest to a point of interest
        for (int i = 1; i < shortestTour.size(); i++) {
            List<Road> roadsEndToEnd = new ArrayList<>();
            List<String> intersectionsBetweenPoints = ResultsDijkstra.get(shortestTour.get(i - 1)).get(shortestTour.get(i)).getValue();
            for (int j = 1; j < intersectionsBetweenPoints.size(); j++) {
                pairIdIntersection = new AbstractMap.SimpleEntry<>(intersectionsBetweenPoints.get(j - 1), intersectionsBetweenPoints.get(j));
                roadsEndToEnd.add(this.ROADS.get(pairIdIntersection));

            }


            paths.add(new Path(roadsEndToEnd, ResultsDijkstra.get(shortestTour.get(i - 1)).get(shortestTour.get(i)).getKey()));
        }
        this.tour.setPaths(paths);
        this.tour.setTotalLength(tsp.getSolutionCost());
        this.tour.setPointOfInterests(shortestTour);
        notifyObservers(this.tour);


    }

    /**
     * Execute the dijktra algorithme and find the best path between two intersections
     * @param point1 the first point of interest
     * @param point2 the second point of interest we want to reach
     * @return the best path these two points and its length
     */
    public AbstractMap.SimpleEntry<Double, List<String>> computePath(PointOfInterest point1, PointOfInterest point2) {

        //Get the id of each intersection
        String idStart = point1.intersection.id;
        String idArrival = point2.intersection.id;

        //Every intersections we didn't visit yet
        HashSet<String> unvisited = new HashSet<>(this.INTERSECTIONS.keySet());

        //Find the optimal path from the first node
        HashMap<String, List<String>> chemin = new HashMap<>();
        for (String key : this.INTERSECTIONS.keySet()) {
            chemin.put(key, new LinkedList<>());
        }

        //Set the length of every intersections as infinity except for startId as 0
        HashMap<String, Double> distance = new HashMap<>();
        for (String key : this.INTERSECTIONS.keySet()) {
            distance.put(key, Double.POSITIVE_INFINITY);
        }
        distance.put(idStart, 0.0);

        /**
         * A class used as a comparator for the priority queue
         */
        class dijkstraDistComparator implements Comparator<String> {
            /**
             * Compare the length between two intersections
             * @param id1 first length
             * @param id2 second length
             * @return the difference between the two lengths
             */
            public int compare(String id1, String id2) {
                return (int) Math.signum(distance.get(id1) - distance.get(id2));
            }
        }

        //Declare the priority queue
        PriorityQueue<String> pq = new PriorityQueue<>(this.INTERSECTIONS.size(), new dijkstraDistComparator());

        //Initialise the algorithme
        pq.add(idStart);
        String currentNode;

        //While there's at least an unvisited node
        while (!pq.isEmpty()) {
            //Get a reachable node with the minimal length
            currentNode = pq.poll();

            //If the node is our destination, break the loop
            if (currentNode.equals(idArrival)) {
                break;
            }

            //Visit every neighbor of the currentNode
            for (Map.Entry<String, Double> e : this.adjacencyList.get(currentNode)) {
                //If the node isn't visited yet
                if (unvisited.contains(e.getKey())) {

                    //If the length improves
                    if (distance.get(currentNode) + e.getValue() < distance.get(e.getKey())) {

                        //Update the length and the path and add it to the priority queue
                        distance.put(e.getKey(), distance.get(currentNode) + e.getValue());
                        pq.add(e.getKey());
                        List<String> listTemp = new LinkedList<>(chemin.get(currentNode));
                        listTemp.add(currentNode);
                        chemin.put(e.getKey(), listTemp);

                    }
                }
            }
            //Mark the currentedNode as visited
            unvisited.remove(currentNode);
        }

        chemin.get(idArrival).add(idArrival);
        return new AbstractMap.SimpleEntry<>(distance.get(idArrival), chemin.get(idArrival));
    }

    /**
     * Reset the Citymap when the user import a new map
     */
    public void reset() {
        this.distribution.reset();
        this.tour.resetTour();
        this.INTERSECTIONS.clear();
        this.ROADS.clear();
        this.width = 0.0;
        this.height = 0.0;
        this.northPoint = 0.0;
        this.westPoint = 0.0;
        this.adjacencyList = new HashMap<>();
        notifyObservers();
    }

    /**
     * Add a new intersection to the CityMap
     * @param intersection the new intersection to be added
     */
    public void addIntersection(Intersection intersection) {
        this.INTERSECTIONS.put(intersection.id, intersection);
        notifyObservers(intersection);
    }

    /**
     * Add a new road to the CityMap
     * @param name the name of the road
     * @param length the length of the road
     * @param id1 the id of the origin (start point) of the road
     * @param id2 the id of the destination ( end point) of the road
     */
    public void addRoad(String name, Double length, String id1, String id2) {
        Intersection origin = this.INTERSECTIONS.get(id1);
        Intersection destination = this.INTERSECTIONS.get(id2);
        Road road = new Road(name, length);
        if (origin != null && destination != null) {
            road.addRoads(origin, destination);
            this.ROADS.put(new AbstractMap.SimpleEntry<>(origin.id, destination.id), road);
        }
        notifyObservers(road);
    }

    /**
     * Add a new request to the CityMap
     * @param poiP the new pickup adress
     * @param preP the previous pickup point of interest
     * @param poiD the new delivery adress
     * @param preD the previous delivery point of interest
     * @throws Exception if the delivery is added before a pickup
     */

    public void addRequest(PointOfInterest poiP, PointOfInterest preP, PointOfInterest poiD, PointOfInterest preD) throws Exception {
        List<PointOfInterest> newPoints = new ArrayList<>(tour.getPointOfInterests());
        List<Path> newPaths = new ArrayList<>(tour.getPaths());
        boolean pickupInserted = false;
        boolean deliveryInserted = false;

        for (int i = 0; (i < newPoints.size() && !deliveryInserted); i++) {
            if (newPoints.get(i) == preP) {
                newPoints.add(i + 1, poiP);

                AbstractMap.SimpleEntry<Double, List<String>> newPathLastToP = computePath(preP, poiP);
                AbstractMap.SimpleEntry<Double, List<String>> newPathPToNext = computePath(poiP, newPoints.get(i + 2));

                if(newPathLastToP.getKey()==Double.POSITIVE_INFINITY
                        || newPathPToNext.getKey()==Double.POSITIVE_INFINITY){
                    throw new Exception("Error: The point inserted is unreachable");

                }

                Path pathLastToP = new Path(dijkstraToRoads(newPathLastToP), newPathLastToP.getKey());
                Path pathPToNext = new Path(dijkstraToRoads(newPathPToNext), newPathPToNext.getKey());

                newPaths.remove(i);
                newPaths.add(i, pathLastToP);
                newPaths.add(i + 1, pathPToNext);
                pickupInserted = true;


                if (preD.equals(preP)) {
                    newPoints.add(i + 2, poiD);

                    AbstractMap.SimpleEntry<Double, List<String>> newPathLastToD = computePath(poiP, poiD);
                    AbstractMap.SimpleEntry<Double, List<String>> newPathDToNext = computePath(poiD, newPoints.get(i + 3));

                    if(newPathLastToD.getKey()==Double.POSITIVE_INFINITY
                            || newPathDToNext.getKey()==Double.POSITIVE_INFINITY){
                        throw new Exception("Error: The point inserted is unreachable");

                    }

                    Path pathLastToD = new Path(dijkstraToRoads(newPathLastToD), newPathLastToD.getKey());
                    Path pathDToNext = new Path(dijkstraToRoads(newPathDToNext), newPathDToNext.getKey());

                    newPaths.remove(i + 1);
                    newPaths.add(i + 1, pathLastToD);
                    newPaths.add(i + 2, pathDToNext);
                    deliveryInserted = true;
                }
            } else if (pickupInserted && newPoints.get(i) == preD) {
                newPoints.add(i + 1, poiD);

                AbstractMap.SimpleEntry<Double, List<String>> newPathLastToD = computePath(preD, poiD);
                AbstractMap.SimpleEntry<Double, List<String>> newPathDToNext = computePath(poiD, newPoints.get(i + 2));

                if(newPathLastToD.getKey()==Double.POSITIVE_INFINITY
                        || newPathDToNext.getKey()==Double.POSITIVE_INFINITY){
                    throw new Exception("Error: The point inserted is unreachable");

                }

                Path pathLastToD = new Path(dijkstraToRoads(newPathLastToD), newPathLastToD.getKey());
                Path pathDToNext = new Path(dijkstraToRoads(newPathDToNext), newPathDToNext.getKey());

                newPaths.remove(i);
                newPaths.add(i, pathLastToD);
                newPaths.add(i + 1, pathDToNext);
                deliveryInserted = true;
            }
        }
        if (!deliveryInserted) {
            throw new Exception("Error : The Delivery was put before the Pickup");
        }
        tour.setPointOfInterests(newPoints);
        tour.setPaths(newPaths);
        notifyObservers(tour);

    }

    /**
     * Remove a new request from the CityMap
     * @param paddress pickup address of the request
     * @param daddress delivery address of the request
     */
    public void removeRequest(PickupAddress paddress, DeliveryAddress daddress) {


        if (distribution.getDelivery(paddress) != daddress) {
            return;
        }


        this.distribution.removeRequest(paddress, daddress);

        List<PointOfInterest> newpoints = new ArrayList<>(tour.getPointOfInterests());
        List<Path> newpaths = new ArrayList<>(tour.getPaths());

        for (int i = newpoints.size() - 1; i >= 0; i--) {
            if (newpoints.get(i) == paddress || newpoints.get(i) == daddress) {
                AbstractMap.SimpleEntry<Double, List<String>> newpath;
                newpath = computePath(newpoints.get(i - 1), newpoints.get(i + 1));
                Path path = new Path(dijkstraToRoads(newpath), newpath.getKey());
                newpoints.remove(i);
                newpaths.remove(i - 1);
                newpaths.remove(i - 1);
                newpaths.add(i - 1, path);
            }
        }

        tour.setPointOfInterests(newpoints);
        tour.setPaths(newpaths);

        notifyObservers(tour);


    }

    /**
     * Change the position of a point on the path
     * @param poi the point of interest we want to relocate
     * @param i the new location of this point
     */
    public void changePosition(PointOfInterest poi, int i) {

        if(poi.getClass()==DeliveryAddress.class){
            int posPickup = this.tour.getPointOfInterests().indexOf(
                                this.distribution.getPickup((DeliveryAddress) poi));
            if(posPickup>=i){
                return;
            }
        }else{
            int posDelivery = this.tour.getPointOfInterests().indexOf(
                    this.distribution.getDelivery((PickupAddress) poi));
            if(posDelivery<=i){
                return;
            }
        }

        List<PointOfInterest> newpoints = new ArrayList<>(tour.getPointOfInterests());
        List<Path> newpaths = new ArrayList<>(tour.getPaths());
        newpoints.remove(poi);
        newpoints.add(i, poi);
        for (int j = newpoints.size() - 1; j >= 1; j--) {

            AbstractMap.SimpleEntry<Double, List<String>> newpathlasttop = computePath(newpoints.get(j - 1), newpoints.get(j));

            Path pathlasttop = new Path(dijkstraToRoads(newpathlasttop), newpathlasttop.getKey());

            newpaths.remove(j - 1);
            newpaths.add(j - 1, pathlasttop);

        }
        tour.setPointOfInterests(newpoints);
        tour.setPaths(newpaths);

        notifyObservers(tour);

    }

    /**
     * Convert the result of the Dijktra method into a list of road
     * @param path the optimal path for the computed tour
     * @return the list of road of the path
     */
    public List<Road> dijkstraToRoads(AbstractMap.SimpleEntry<Double, List<String>> path) {
        List<String> intersectionsBetweenPoints = path.getValue();
        List<Road> roadsEndToEnd = new ArrayList<>();
        AbstractMap.SimpleEntry<String, String> pairIdIntersection;
        for (int j = 1; j < intersectionsBetweenPoints.size(); j++) {
            pairIdIntersection = new AbstractMap.SimpleEntry<>(intersectionsBetweenPoints.get(j - 1), intersectionsBetweenPoints.get(j));
            roadsEndToEnd.add(this.ROADS.get(pairIdIntersection));

        }
        return roadsEndToEnd;
    }

    /**
     * Update the adjacency list with the new roads
     * @param id1 the id of the intersection indicating the start of the road
     * @param id2 the id of the intersection indicating the end of the road
     * @param length the length of the road
     */
    public void completeAdjacencyList(String id1, String id2, Double length) {

        this.adjacencyList.get(id1).add(new AbstractMap.SimpleEntry<>(id2, length));

    }

    /**
     * Empty the adjacency list
     * @param id1 the id of the intersection indicating the start of the path
     */
    public void initializeAdjacencyList(String id1) {

        List<AbstractMap.Entry<String, Double>> targets = new ArrayList<>();
        this.adjacencyList.put(id1, targets);

    }

    public Tour getTour() {
        return tour;
    }

    public HashMap<String, Intersection> getINTERSECTIONS() {
        return this.INTERSECTIONS;
    }

    public void addObserver(MapView mapView) {
        super.addObserver(mapView);
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public HashMap<AbstractMap.SimpleEntry<String, String>, Road> getROADS() {
        return ROADS;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setNorthPoint(Double northPoint) {
        this.northPoint = northPoint;
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

    public Double getNorthPoint() {
        return northPoint;
    }

    public Double getWestPoint() {
        return westPoint;
    }

    public HashMap<String, List<AbstractMap.Entry<String, Double>>> getAdjacencyList() {
        return adjacencyList;
    }


    /**
     * Set a pair of points to be highlight
     * @param highlightpoint the pickup point to be highlight
     * @param secondaryPoint the delivery point to be highlight
     */
    public void setHighlighted(PointOfInterest highlightpoint, PointOfInterest secondaryPoint) {
        this.primaryHighlight = highlightpoint;
        this.secondaryHighlight = secondaryPoint;
        notifyObservers();
    }
    
    public void resetSelected() {
        this.i1Selected=null;
        this.i2Selected=null;
        notifyObservers();
    }

    public void setSelected1(Intersection i) {
        this.i1Selected=i;
        notifyObservers();

    }

    public void setSelected2(Intersection i) {
        this.i2Selected=i;
        notifyObservers();


    }
    /**
     * set the intersection to be added on the map (this.poiToAdd)
     * @param poiToAdd Intersection of the new point
     */
    public void setPOIToAdd(Intersection poiToAdd) {
        this.poiToAdd = poiToAdd;
        notifyObservers();
    }

    public Intersection getPoiToAdd() {
        return poiToAdd;
    }

    @Override
    public String toString() {
        return "CityMap{" +
                "roads=" + ROADS +
                '}';
    }

}

