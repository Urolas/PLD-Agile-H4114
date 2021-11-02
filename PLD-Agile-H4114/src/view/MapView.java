package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

import model.*;

import observer.Observable;
import observer.Observer;


/**
 * @author 4IF-4114
 */
public class MapView extends JPanel implements Observer {
    private Graphics g;
    private Graphics2D g2 ;
    private CityMap cityMap;
    private double scaleWidth;
    private double scaleHeight;
    private double originLat;
    private double originLong;
    private double originLatClicked;
    private double originLongClicked;
    private final int VIEW_HEIGHT = 800;
    private final int VIEW_WIDTH = 800;
    private final int POINT_SIZE = 15;
    private double scaleZoom = 1;
    private double mapWidth;
    private double mapHeight;
    private double smallRoadThickness;
    private double greatRoadThickness;
    private int mouseClickedX;
    private int mouseClickedY;
    /**
     * Default constructor
     */
    public MapView(CityMap cityMap, Window window) {
        super();
        this.cityMap = cityMap;
        cityMap.addObserver(this); // this observes cityMap
        cityMap.getDistribution().addObserver(this); // this observes distribution
        cityMap.getTour().addObserver(this);
        mapWidth = cityMap.getWidth();
        mapHeight = cityMap.getHeight();
        scaleWidth = 1;
        scaleHeight = 1;
        originLong = 0;
        originLat = 0;
        smallRoadThickness = 1;
        greatRoadThickness = 2;
        setLayout(null);
        setBackground(new Color(180,180,180));
        setSize(VIEW_WIDTH,VIEW_HEIGHT);
        window.getContentPane().add(this);
    }

    public void modifyZoom(double zoom, int centerX, int centerY){
        if (zoom == 1 || (scaleZoom * zoom < 1)){
            scaleZoom = 1;
            smallRoadThickness = 1;
            greatRoadThickness = 2;
            mapWidth = cityMap.getWidth();
            mapHeight = cityMap.getHeight();
            originLong = cityMap.getWestPoint();
            originLat = cityMap.getNordPoint();
        }else if (!(zoom < 1 && scaleZoom <= 1) && !(zoom > 1 && scaleZoom >= 16)){
            scaleZoom = scaleZoom * zoom;
            smallRoadThickness = smallRoadThickness*zoom;
            greatRoadThickness = greatRoadThickness*zoom;
            mapWidth = mapWidth/zoom;
            mapHeight = mapHeight/zoom;
            if (originLong - mapWidth/2 + centerX/scaleWidth < cityMap.getWestPoint()){
                originLong = cityMap.getWestPoint();
            }else if(originLong - mapWidth/2 + centerX/scaleWidth + mapWidth> cityMap.getWestPoint() + cityMap.getWidth()){
                originLong = cityMap.getWestPoint() + cityMap.getWidth() - mapWidth;
            }else{
                originLong = originLong - mapWidth/2 + centerX/scaleWidth;
            }
            if (originLat + mapHeight/2 - centerY/scaleHeight > cityMap.getNordPoint()){
                originLat = cityMap.getNordPoint();
            }else if (originLat + mapHeight/2 - centerY/scaleHeight - mapHeight < cityMap.getNordPoint() - cityMap.getHeight()){
                originLat = cityMap.getNordPoint() - cityMap.getHeight() + mapHeight;
            }else{
                originLat = originLat + mapHeight/2 - centerY/scaleHeight;
            }
        }
        scaleWidth = VIEW_WIDTH/cityMap.getWidth()*scaleZoom;
        scaleHeight = VIEW_HEIGHT/cityMap.getHeight()*scaleZoom;
        repaint();
    }



    public void dragMap(int mouseX, int mouseY){
        System.out.println((mouseX - mouseClickedX)/scaleWidth);
        if  ((cityMap.getWestPoint() <= originLongClicked - (mouseX - mouseClickedX)/scaleWidth) &&
                (cityMap.getWestPoint() + cityMap.getWidth()>= originLongClicked - (mouseX - mouseClickedX)/scaleWidth  + mapWidth ))  {
            originLong = originLongClicked - (mouseX - mouseClickedX)/scaleWidth;
        }
        if  ((cityMap.getNordPoint() >= originLatClicked + (mouseY - mouseClickedY)/scaleHeight) &&
                (cityMap.getNordPoint() - cityMap.getHeight()<= originLatClicked + (mouseY - mouseClickedY)/scaleHeight - mapHeight )) {
            originLat = originLatClicked + (mouseY - mouseClickedY) / scaleHeight;
        }
        repaint();
    }

    /**
     *  Method called when the users uses directional keys to move in a zoomed map
     * @param keyCode the code of the pressed key
     */
    public void moveMapView(int keyCode){
        int horizontal = 0;
        int vertical = 0;
        if (keyCode == 37){
            horizontal = -1;
        }
        if (keyCode == 38){
            vertical = -1;
        }
        if (keyCode == 39){
            horizontal = 1;
        }
        if (keyCode == 40){
            vertical = 1;
        }
        int speed = 3;
        originLong = originLong + horizontal*speed/scaleWidth;
        if (originLong < cityMap.getWestPoint()){
            originLong = cityMap.getWestPoint();
        } else if( originLong + mapWidth > cityMap.getWestPoint() + cityMap.getWidth()){
            originLong = cityMap.getWestPoint() + cityMap.getWidth() - mapWidth;
        }
        originLat = originLat - vertical*speed/ scaleHeight;
        if (originLat > cityMap.getNordPoint()) { //TODO change "Nord" to "North"
            originLat = cityMap.getNordPoint();
        } else if(originLat - mapHeight < cityMap.getNordPoint() - cityMap.getHeight()){
            originLat = cityMap.getNordPoint() - cityMap.getHeight() + mapHeight;
        }
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        mapWidth = cityMap.getWidth();
        mapHeight = cityMap.getHeight();
        scaleZoom = 1;
        smallRoadThickness = 1;
        greatRoadThickness = 2;
        scaleWidth = VIEW_WIDTH/mapWidth*scaleZoom;
        scaleHeight = VIEW_HEIGHT/mapHeight*scaleZoom;
        originLong = cityMap.getWestPoint();
        originLat = cityMap.getNordPoint();

        repaint();
    }

    public int getViewHeight() {
        return VIEW_HEIGHT;
    }

    public int getViewWidth() {
        return VIEW_WIDTH;
    }

    public void setMouseClickedX(int mouseClickedX) {
        this.mouseClickedX = mouseClickedX;
    }

    public void setMouseClickedY(int mouseClickedY) {
        this.mouseClickedY = mouseClickedY;
    }

    public void fixOrigin(){
        this.originLatClicked = originLat;
        this.originLongClicked = originLong;
    }

    public int getVIEW_HEIGHT() {
        return VIEW_HEIGHT;
    }

    public int getVIEW_WIDTH() {
        return VIEW_WIDTH;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.g = g;
        this.g2 = (Graphics2D) g;
        for(Map.Entry<AbstractMap.SimpleEntry<String,String>,Road> road : cityMap.getRoads().entrySet()){
            displayRoad(road.getValue(),Color.white, 1,false);
        }
        Color outline = Color.BLACK;
        Tour t = cityMap.getTour();
        if (t!=null) {
            if (t.getPaths().size() != 0){
                outline = Color.RED;
            }
            for (Path p : t.getPaths()){
                displayPath(p);
            }

        }
        Distribution d = cityMap.getDistribution();
        if (d!=null) {
            System.out.println();
            displayDepot();
            for (Request q : d.getRequests()){
                displayRequest(q,outline);
            }
        }
    }

    /**
     *  Method called by
     * @param r
     */
    public void displayRoad(Road r, Color c , double thickness, boolean displayTour){
        int x1 = (int)((r.getOrigin().getLongitude() - originLong) * scaleWidth);
        int y1 = -(int)((r.getOrigin().getLatitude() - originLat) * scaleHeight); /* Le repère de latitude est inversé */
        int x2 = (int)((r.getDestination().getLongitude() - originLong) * scaleWidth);
        int y2 = -(int)((r.getDestination().getLatitude() - originLat) * scaleHeight);
        if (!displayTour){
            if (r.getName().contains("Boulevard") || r.getName().contains("Avenue") || r.getName().contains("Cours") ){
                thickness=greatRoadThickness;
                c=Color.decode("#FFF889");
            } else if (r.getName().contains("Impasse") ){
                thickness=smallRoadThickness;
                c=Color.WHITE;
            }else {
                thickness=smallRoadThickness;
                c=Color.WHITE;
            }
        }
        g.setColor(c);
        g2.setStroke(new BasicStroke((float)thickness));
        g2.draw(new Line2D.Float(x1, y1, x2, y2));

    }
    public void displayPath(Path p){
        for (Road r : p.getRoads()){
            displayRoad(r,Color.red,3,true);
        }
    }
    public void displayRequest(Request q,Color outline){
        int x1 = (int)((q.getPickup().getIntersection().getLongitude()- originLong) * scaleWidth);
        int y1 = -(int)((q.getPickup().getIntersection().getLatitude()- originLat) * scaleHeight);
        int x2 = (int)((q.getDelivery().getIntersection().getLongitude()- originLong) * scaleWidth);
        int y2 = -(int)((q.getDelivery().getIntersection().getLatitude()- originLat) * scaleHeight);

        g.setColor(q.color);
        g.fillOval(x1-POINT_SIZE/2, y1-POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        g.setColor(outline);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x1-POINT_SIZE/2, y1-POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        g.setColor(q.color);
        g.fillPolygon(new int[] {x2, x2+POINT_SIZE, x2+POINT_SIZE/2}, new int[] {y2, y2, y2+POINT_SIZE}, 3);

        g.setColor(outline);
        g2.setStroke(new BasicStroke(2));
        g.drawPolygon(new int[] {x2, x2+POINT_SIZE, x2+POINT_SIZE/2}, new int[] {y2, y2, y2+POINT_SIZE}, 3);

    }

    public void displayDepot(){
        if (cityMap.getDistribution().getDepot().getIntersection() != null) {
            int x = (int) ((cityMap.getDistribution().getDepot().getIntersection().getLongitude() - originLong) * scaleWidth);
            int y = -(int) ((cityMap.getDistribution().getDepot().getIntersection().getLatitude() - originLat) * scaleHeight);
            g.setColor(Color.black);
            g.fillRect(x-POINT_SIZE/2, y-POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        }
    }
}