package model;

import java.util.*;

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
    private Double width,height,nordPoint,westPoint;
    private HashMap<String,List<AbstractMap.Entry<String,Double>>> adjacencyList;


    /**
     *
     * Default constructor
     */
    public CityMap() {
        this.intersections= new HashMap<>();
        this.roads=  new HashMap<>();
        this.adjacencyList= new HashMap<>();


    }

    //compute les meilleurs chemins entre chaque points d'interet, appelle le TSP pour trouver le meilleurs tour, créé le tour
    public void computeTour() {
        //recuperaton des points d'interets
        List<PointOfInterest> points = this.distribution.GetAllPoints();
        List<AbstractMap.SimpleEntry<String,String>> constraints = this.distribution.GetConstraints();
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
        TSP tsp = new TSPplaceholder();
        tsp.searchSolution(2000,graph);
        List<PointOfInterest> shortestTour = new LinkedList<>();
        shortestTour.add(points.get(0));
        List<String> shortestPath = new LinkedList<>();
        //traduction du resultat du TSP en données utilisables
        for (int i=1;i<tsp.getSolutionCost();i++) {
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
        /** TODO Impossible a faire sans un dijkstra censé, a debugé apres
         **/
        this.tour= new Tour(paths,shortestTour,tsp.getSolutionCost());



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


    public void reset(){
        this.distribution = new Distribution();
        this.tour = new Tour();
        this.intersections.clear();
        this.roads.clear();
    }

    public void addIntersection(Intersection intersection) {
        this.intersections.put(intersection.id,intersection);
    }

    public void addRoad(String name, Double length, String id1, String id2) {
        Intersection origin = this.intersections.get(id1);
        Intersection destination = this.intersections.get(id2);
        Road road = new Road(name,length);
        if(origin!=null && destination!=null){
            road.addRoads(origin,destination);
            this.roads.put(new AbstractMap.SimpleEntry<>(origin.id,destination.id),road);
        }
    }

    public void completeAdjacencyList(String id1, String id2, Double length) {

        this.adjacencyList.get(id1).add(new AbstractMap.SimpleEntry<>(id2,length));

    }
    public void initializeAdjacencyList(String id1) {

        List<AbstractMap.Entry<String,Double>> targets= new ArrayList<>();
        this.adjacencyList.put(id1,targets);

    }

    public HashMap<String,Intersection>getIntersections(){
        return this.intersections;
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

}

