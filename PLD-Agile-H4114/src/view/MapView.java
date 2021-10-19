package view;

import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import model.CityMap;
import model.Road;

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
    private final int VIEW_HEIGHT = 700;
    private final int VIEW_WIDTH = 700;

    /**
     * Default constructor
     */
    public MapView(CityMap cityMap, Window window) {
        super();
        this.cityMap = cityMap;
        cityMap.addObserver(this); // this observes plan
        scaleWidth = 1;
        scaleHeight = 1;
        originLong = 0;
        originLat = 0;
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setSize(VIEW_WIDTH,VIEW_HEIGHT);
        window.getContentPane().add(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        scaleWidth = VIEW_WIDTH/cityMap.getWidth();
        scaleHeight = VIEW_HEIGHT/cityMap.getHeight();
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
        g.setColor(Color.BLACK);
        for (Road r : cityMap.getRoads()){
            displayRoad(r);
        }
    }

    /**
     *  Method called by
     * @param r
     */
    public void displayRoad(Road r){
        int x1 = (int)((r.getOrigin().getLongitude()- originLong) * scaleWidth);
        int y1 = -(int)((r.getOrigin().getLatitude()- originLat) * scaleHeight); /* Le repère de latitude est inversé */
        int x2 = (int)((r.getDestination().getLongitude()- originLong) * scaleWidth);
        int y2 = -(int)((r.getDestination().getLatitude()- originLat) * scaleHeight);
        g.setColor(Color.white);
        g2.setStroke(new BasicStroke(1));
        g2.draw(new Line2D.Float(x1, y1, x2, y2));

    }
}