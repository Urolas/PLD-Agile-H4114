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
    private final int VIEW_HEIGHT = 800;
    private final int VIEW_WIDTH = 800;
    private final int POINT_SIZE = 15;
    private double scaleZoom = 1;
    private double mapWidth;
    private double mapHeight;

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
        setLayout(null);
        setBackground(new Color(180,180,180));
        setSize(VIEW_WIDTH,VIEW_HEIGHT);
        window.getContentPane().add(this);
    }

    public void modifyZoom(double zoom){
        if (zoom == 1){
            scaleZoom = 1;
            mapWidth = cityMap.getWidth();
            mapHeight = cityMap.getHeight();
            originLong = cityMap.getWestPoint();
            originLat = cityMap.getNordPoint();
        }else if (!(zoom < 1 && scaleZoom <= 1) && !(zoom > 1 && scaleZoom >= 16)){// not ( si on dézoome et qu'on est déja au maximum de dezoom )
            scaleZoom = scaleZoom * zoom;
            Double lastMapWidth = mapWidth;
            Double lastMapHeight = mapHeight;
            mapWidth = mapWidth/zoom;
            mapHeight = mapHeight/zoom;

            if (zoom < 1){
                zoom = 1/zoom;
                originLong = originLong - mapWidth/2 + lastMapWidth/2;
                originLat = originLat + mapHeight/2 - lastMapHeight/2;
            } else if (zoom > 1) {
                originLong = originLong - mapWidth/2 + lastMapWidth/2;
                originLat = originLat + mapHeight/2 - lastMapHeight/2;
            }
        }
        scaleWidth = VIEW_WIDTH/cityMap.getWidth()*scaleZoom;
        scaleHeight = VIEW_HEIGHT/cityMap.getHeight()*scaleZoom;



        System.out.println(scaleWidth);
        repaint();

    }

    @Override
    public void update(Observable o, Object arg) {
        mapWidth = cityMap.getWidth();
        mapHeight = cityMap.getHeight();

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
    public void displayRoad(Road r, Color c , int thickness, boolean displayTour){
        int x1 = (int)((r.getOrigin().getLongitude() - originLong) * scaleWidth);
        int y1 = -(int)((r.getOrigin().getLatitude() - originLat) * scaleHeight); /* Le repère de latitude est inversé */
        int x2 = (int)((r.getDestination().getLongitude() - originLong) * scaleWidth);
        int y2 = -(int)((r.getDestination().getLatitude() - originLat) * scaleHeight);
        if (!displayTour){
            if (r.getName().contains("Boulevard") || r.getName().contains("Avenue") || r.getName().contains("Cours") ){
                thickness=2;
                c=Color.decode("#FFF889");
            } else if (r.getName().contains("Impasse") ){
                thickness=1;
                c=Color.WHITE;
            }else {
                thickness=1;
                c=Color.WHITE;
            }
        }
        g.setColor(c);
        g2.setStroke(new BasicStroke(thickness));
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