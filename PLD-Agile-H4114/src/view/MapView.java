package view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
    private final int HIGHLIGHT_POINT_SIZE = 20;
    private final int SECONDARY_POINT_SIZE = 17;
    private double scaleZoom = 1;
    private double mapWidth;
    private double mapHeight;
    private double smallRoadThickness;
    private double greatRoadThickness;
    private int mouseClickedX;
    private int mouseClickedY;
    private int counterInter;

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
        counterInter = 0;
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
        }
        else if (!(zoom < 1 && scaleZoom <= 1) && !(zoom > 1 && scaleZoom >= 16)){
            scaleZoom = scaleZoom * zoom;
            smallRoadThickness = smallRoadThickness*zoom;
            greatRoadThickness = greatRoadThickness*zoom;
            mapWidth = mapWidth/zoom;
            mapHeight = mapHeight/zoom;
            if (originLong - mapWidth*((double)centerX/VIEW_WIDTH) + centerX/scaleWidth < cityMap.getWestPoint()){
                originLong = cityMap.getWestPoint();
            }else if(originLong - mapWidth*((double)centerX/VIEW_WIDTH) + centerX/scaleWidth + mapWidth> cityMap.getWestPoint() + cityMap.getWidth()){
                originLong = cityMap.getWestPoint() + cityMap.getWidth() - mapWidth;
            }else{
                originLong = originLong - mapWidth*((double)centerX/VIEW_WIDTH) +
                        centerX/scaleWidth;

            }
            if (originLat + mapHeight*((double)centerY/VIEW_HEIGHT) - centerY/scaleHeight > cityMap.getNordPoint()){
                originLat = cityMap.getNordPoint();
            }else if (originLat + mapHeight*((double)centerY/VIEW_HEIGHT) - centerY/scaleHeight - mapHeight < cityMap.getNordPoint() - cityMap.getHeight()){
                originLat = cityMap.getNordPoint() - cityMap.getHeight() + mapHeight;
            }else{
                originLat = originLat + mapHeight*((double)centerY/VIEW_HEIGHT) - centerY/scaleHeight;
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

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        counterInter = 0;
        this.g = g;
        this.g2 = (Graphics2D) g;
        for(Map.Entry<AbstractMap.SimpleEntry<String,String>,Road> road : cityMap.getRoads().entrySet()){
            displayRoad(road.getValue(),Color.white, 1,false);
        }
        Color outline = Color.BLACK;
        Tour t = cityMap.getTour();
        if (t!=null) {
            if (t.getPaths().size() != 0){
                outline = new Color(200,0,0);
            }
            for (Path p : t.getPaths()){
                displayPath(p,outline);
            }

        }
        Distribution d = cityMap.getDistribution();
        if (d.getRequests().size()!=0) {

            displayDepot();
            for (Request q : d.getRequests()){
                displayRequest(q,outline);
            }
        }
        if(cityMap.secondaryHighlight!=null & cityMap.primaryHighlight!=null){
            displayHighlights(cityMap.primaryHighlight,cityMap.secondaryHighlight);

        }
        GradientPaint grad = new GradientPaint(0,0,new Color(0,0,0,50),40,0,new Color(0,0,0,0),false);
        g2.setPaint(grad);
        g2.fillRect(0,0,50,800);
        grad = new GradientPaint( VIEW_WIDTH,0,new Color(0,0,0,50),VIEW_WIDTH-40,0,new Color(0,0,0,0),false);
        g2.setPaint(grad);
        g2.fillRect(760,0,50,800);


    }

    private void displayHighlights(PointOfInterest p1, PointOfInterest p2) {

        int x1 = convertLongitudeToPixel(p1.getIntersection().getLongitude());
        int y1 = convertLatitudeToPixel(p1.getIntersection().getLatitude());
        Color Color1 = p1.getColor();
        int x2 = convertLongitudeToPixel(p2.getIntersection().getLongitude());
        int y2 = convertLatitudeToPixel(p2.getIntersection().getLatitude());
        Color Color2 = p2.getColor();
        if (p1 instanceof PickupAddress) {
            g.setColor(Color1);
            g.fillOval(x1 - HIGHLIGHT_POINT_SIZE/2, y1 - HIGHLIGHT_POINT_SIZE/2, HIGHLIGHT_POINT_SIZE , HIGHLIGHT_POINT_SIZE );
            g.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x1 - HIGHLIGHT_POINT_SIZE/2, y1 - HIGHLIGHT_POINT_SIZE/2, HIGHLIGHT_POINT_SIZE , HIGHLIGHT_POINT_SIZE);
            g.setColor(Color2);
            g.fillPolygon(new int[]{x2-SECONDARY_POINT_SIZE/2, x2 + SECONDARY_POINT_SIZE/2, x2 }, new int[]{y2-SECONDARY_POINT_SIZE/2, y2-SECONDARY_POINT_SIZE/2, y2 + SECONDARY_POINT_SIZE/2}, 3);
            g.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            g.drawPolygon(new int[]{x2-SECONDARY_POINT_SIZE/2, x2 + SECONDARY_POINT_SIZE/2, x2 }, new int[]{y2-SECONDARY_POINT_SIZE/2, y2-SECONDARY_POINT_SIZE/2, y2 + SECONDARY_POINT_SIZE/2}, 3);
        } else {
            g.setColor(Color2);
            g.fillOval(x2 - SECONDARY_POINT_SIZE/2, y2 - SECONDARY_POINT_SIZE/2, SECONDARY_POINT_SIZE , SECONDARY_POINT_SIZE );
            g.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x2 - SECONDARY_POINT_SIZE/2, y2 - SECONDARY_POINT_SIZE/2, SECONDARY_POINT_SIZE , SECONDARY_POINT_SIZE );
            g.setColor(Color1);
            g.fillPolygon(new int[]{x1-HIGHLIGHT_POINT_SIZE/2, x1 + HIGHLIGHT_POINT_SIZE/2, x1}, new int[]{y1-HIGHLIGHT_POINT_SIZE/2, y1-HIGHLIGHT_POINT_SIZE/2, y1 + HIGHLIGHT_POINT_SIZE/2}, 3);
            g.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            g.drawPolygon(new int[]{x1-HIGHLIGHT_POINT_SIZE/2, x1 + HIGHLIGHT_POINT_SIZE/2, x1}, new int[]{y1-HIGHLIGHT_POINT_SIZE/2, y1-HIGHLIGHT_POINT_SIZE/2, y1 + HIGHLIGHT_POINT_SIZE/2}, 3);
        }

    }

    /**
     *  Method called by
     * @param r
     */
    public int convertLongitudeToPixel(double longitude) {
        return (int)((longitude - originLong) * scaleWidth);
    }

    public int convertLatitudeToPixel(double latitude) {
        return - (int) ((latitude - originLat) * scaleHeight);
    }

    public void displayRoad(Road r, Color c , double thickness, boolean displayTour){
        int x1 = convertLongitudeToPixel(r.getOrigin().getLongitude());
        int y1 = convertLatitudeToPixel(r.getOrigin().getLatitude()); /* Le repère de latitude est inversé */
        int x2 = convertLongitudeToPixel(r.getDestination().getLongitude());
        int y2 = convertLatitudeToPixel(r.getDestination().getLatitude());
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
        }else if (counterInter++ == 10){
            counterInter = 0;
            double theta = Math.atan2(x1-x2,y1-y2);
            int middleRoadX = (int)((x1 + x2)/2);
            int middleRoadY = (int)((y1 + y2)/2);
            int fSize = 8;
            g2.fillPolygon(new int[] {middleRoadX,
                    (int)(middleRoadX+fSize*(Math.cos(-theta)-Math.sin(-theta))),
                    (int)(middleRoadX-fSize*(Math.cos(theta)-Math.sin(theta)))}, new int[] {middleRoadY,
                    (int)(middleRoadY+fSize*(Math.sin(-theta)+Math.cos(-theta))),
                    (int)(middleRoadY+fSize*(Math.sin(theta)+Math.cos(theta)))}, 3);
        }
        g.setColor(c);
        g2.setStroke(new BasicStroke((float)thickness));
        g2.draw(new Line2D.Float(x1, y1, x2, y2));

    }
    public void displayPath(Path p,Color c){
        for (Road r : p.getRoads()){
            displayRoad(r,c,3,true);
        }
    }

    public void displayRequest(Request q,Color outline){
        int x1 = convertLongitudeToPixel(q.getPickup().getIntersection().getLongitude());
        int y1 = convertLatitudeToPixel(q.getPickup().getIntersection().getLatitude());
        int x2 = convertLongitudeToPixel(q.getDelivery().getIntersection().getLongitude());
        int y2 = convertLatitudeToPixel(q.getDelivery().getIntersection().getLatitude());

        g.setColor(q.color);
        g.fillOval(x1-POINT_SIZE/2, y1-POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        g.setColor(outline);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x1-POINT_SIZE/2, y1-POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        g.setColor(q.color);
        g.fillPolygon(new int[] {x2-POINT_SIZE/2, x2+POINT_SIZE/2, x2}, new int[] {y2-POINT_SIZE/2, y2-POINT_SIZE/2, y2+POINT_SIZE/2}, 3);
        g.setColor(outline);
        g2.setStroke(new BasicStroke(3));
        g.drawPolygon(new int[] {x2-POINT_SIZE/2, x2+POINT_SIZE/2, x2}, new int[] {y2-POINT_SIZE/2, y2-POINT_SIZE/2, y2+POINT_SIZE/2}, 3);


    }

    public void displayDepot(){
        if (cityMap.getDistribution().getDepot().getIntersection() != null) {
            int x = convertLongitudeToPixel(cityMap.getDistribution().getDepot().getIntersection().getLongitude());
            int y = convertLatitudeToPixel(cityMap.getDistribution().getDepot().getIntersection().getLatitude());
            g.setColor(Color.black);
            g.fillRect(x-2, y-25, POINT_SIZE+1, POINT_SIZE);
            g2.setStroke(new BasicStroke(3));
            g2.draw(new Line2D.Float(x, y-15, x, y));
        }
    }

    public PointOfInterest getClosestPointOfInterest(int x, int y) {
        for (PointOfInterest poi : this.cityMap.getTour().getPointOfInterests()) {
            int xPoi = convertLongitudeToPixel(poi.getIntersection().getLongitude());
            int yPoi = convertLatitudeToPixel(poi.getIntersection().getLatitude());
            if (x <= xPoi + POINT_SIZE && x >= xPoi - POINT_SIZE && // check if point is inside the shape of the specific poi
                    y <= yPoi + POINT_SIZE && y >= yPoi - POINT_SIZE) { // for now, we just check if inside a square, to be modified...
                return poi;
            }
        }
        return null;
    }

    public Intersection getClosestIntersection(int x, int y) {
        for (Intersection i : this.cityMap.getIntersections().values()) {
            int xPoi = convertLongitudeToPixel(i.getLongitude());
            int yPoi = convertLatitudeToPixel(i.getLatitude());
            if (x <= xPoi + POINT_SIZE/2 && x >= xPoi - POINT_SIZE/2 &&
                    y <= yPoi + POINT_SIZE/2 && y >= yPoi - POINT_SIZE/2) {
                return i;
            }
        }
        return null;
    }


}