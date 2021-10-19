package view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import javax.swing.JPanel;
import model.CityMap;
import model.Road;

/**
 * @author 4IF-4114
 */
public class MapView extends JPanel implements Observer {
    private Graphics g;
    private Graphics2D g2 =(Graphics2D) g;
    private CityMap cityMap;
    private double scaleWidth;
    private double scaleHeight;
    private double originLat;
    private double originLong;
    private final int viewHeight = 800;
    private final int viewWidth = 800;
    /**
     * Default constructor
     */
    public MapView(CityMap cityMap, Window window) {
        super();
        this.cityMap = cityMap;
        scaleWidth = viewWidth/cityMap.getWidth();
        scaleHeight = viewHeight/cityMap.getHeight();
        originLong = cityMap.getNordPoint();
        originLat = cityMap.getWestPoint();
        System.out.println(originLat + " " +originLong);
        setLayout(null);
        setBackground(Color.GRAY);
        setSize(viewWidth,viewHeight);
        window.getContentPane().add(this);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.g = g;
        g.setColor(Color.BLACK);

            for (Road r : cityMap.getRoads()){
//                System.out.println(r);
                displayRoad(r);
            }
            g.drawLine(0,0,50,50);
            //g2.setStroke(new BasicStroke(10));
            //g2.draw(new Line2D.Float(30, 20, 80, 90));



    }

    /**
     * @param observed 
     * @param object 
     * @return
     */
    public void update(Observable observed, Object object) {
        // TODO implement here
    }

    public void displayRoad(Road r){
        double x1 = (r.getOrigin().getLatitude()-originLat) * scaleWidth;
//       System.out.println("x1 = ("+ r.getOrigin().getLatitude()+" - "+originLong + ") *"+scaleWidth+" = "+ (int)x1);
        double y1 = (r.getOrigin().getLongitude()-originLong) * scaleHeight;
//            System.out.println("y1 = ("+ r.getOrigin().getLongitude()+" - "+originLat + ") *"+scaleHeight+" = "+ (int)y1);
        double x2 = (r.getDestination().getLatitude()-originLat) * scaleWidth;
        double y2 = (r.getDestination().getLongitude()-originLong) * scaleHeight;
//        System.out.println(x2+" "+y1+" "+y2);
//        System.out.println(y2);
        g.setColor(Color.RED);
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        //g2.setStroke(new BasicStroke(10));
        //g2.draw(new Line2D.Float(30, 20, 80, 90));
    }

}