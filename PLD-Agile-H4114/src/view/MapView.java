/**
 * MapView
 *
 * @author 4IF-4114
 */
package view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.AbstractMap;
import java.util.Map;
import javax.swing.JPanel;

import model.*;

import observer.Observable;
import observer.Observer;

/**
 * The map view of the window, where the imported map is shown
 */
public class MapView extends JPanel implements Observer {
    private Graphics g;
    private Graphics2D g2;
    private double scaleWidth;
    private double scaleHeight;
    private double originLat;
    private double originLong;
    private double originLatClicked;
    private double originLongClicked;
    private double scaleZoom = 1;
    private double mapWidth;
    private double mapHeight;
    private double smallRoadThickness;
    private double greatRoadThickness;
    private int mouseClickedX;
    private int mouseClickedY;
    private int counterInter;
    private final CityMap CITYMAP;
    private final int VIEW_HEIGHT = 800;
    private final int VIEW_WIDTH = 800;
    private final int POINT_SIZE = 15;
    private final int HIGHLIGHT_POINT_SIZE = 20;
    private final int SECONDARY_POINT_SIZE = 17;

    /**
     * Constructor of MapView
     *
     * @param cityMap the map and its element(points) to be shown
     * @param window  the current application window
     */
    public MapView(CityMap cityMap, Window window) {
        super();
        this.CITYMAP = cityMap;
        cityMap.addObserver(this); // this observes cityMap
        cityMap.getDistribution().addObserver(this); // this observes distribution
        cityMap.getTour().addObserver(this);
        this.mapWidth = cityMap.getWidth();
        this.mapHeight = cityMap.getHeight();
        this.scaleWidth = 1;
        this.scaleHeight = 1;
        this.originLong = 0;
        this.originLat = 0;
        this.smallRoadThickness = 1;
        this.greatRoadThickness = 3;
        this.counterInter = 0;
        setLayout(null);
        setBackground(new Color(180, 180, 180));
        setSize(this.VIEW_WIDTH, this.VIEW_HEIGHT);
        window.getContentPane().add(this);
    }

    /**
     * Manage the map during a zoom in or zoom out
     *
     * @param zoom    the value of the zoom
     * @param centerX the X position of the map's center
     * @param centerY the Y position of the map's center
     */
    public void modifyZoom(double zoom, int centerX, int centerY) {
        if (zoom == 1 || (scaleZoom * zoom < 0.95)) {
            scaleZoom = 1;
            smallRoadThickness = 1;
            greatRoadThickness = 3;
            mapWidth = CITYMAP.getWidth();
            mapHeight = CITYMAP.getHeight();
            originLong = CITYMAP.getWestPoint();
            originLat = CITYMAP.getNordPoint();
        } else if (!(zoom < 1 && scaleZoom <= 1) &&
                !(zoom > 1 && scaleZoom >= 16)) {
            scaleZoom = scaleZoom * zoom;
            smallRoadThickness = smallRoadThickness * zoom;
            greatRoadThickness = greatRoadThickness * zoom;
            mapWidth = mapWidth / zoom;
            mapHeight = mapHeight / zoom;
            if (originLong - mapWidth * ((double) centerX / VIEW_WIDTH)
                    + centerX / scaleWidth < CITYMAP.getWestPoint()) {
                originLong = CITYMAP.getWestPoint();
            } else if (originLong - mapWidth * ((double) centerX / VIEW_WIDTH)
                    + centerX / scaleWidth + mapWidth > CITYMAP.getWestPoint()
                    + CITYMAP.getWidth()) {
                originLong = CITYMAP.getWestPoint() + CITYMAP.getWidth() - mapWidth;
            } else {
                originLong = originLong - mapWidth * ((double) centerX / VIEW_WIDTH) +
                        centerX / scaleWidth;

            }
            if (originLat + mapHeight * ((double) centerY / VIEW_HEIGHT)
                    - centerY / scaleHeight > CITYMAP.getNordPoint()) {
                originLat = CITYMAP.getNordPoint();
            } else if (originLat + mapHeight * ((double) centerY / VIEW_HEIGHT)
                    - centerY / scaleHeight - mapHeight < CITYMAP.getNordPoint() - CITYMAP.getHeight()) {
                originLat = CITYMAP.getNordPoint() - CITYMAP.getHeight() + mapHeight;
            } else {
                originLat = originLat + mapHeight * ((double) centerY / VIEW_HEIGHT) - centerY / scaleHeight;
            }

        }
        scaleWidth = VIEW_WIDTH / CITYMAP.getWidth() * scaleZoom;
        scaleHeight = VIEW_HEIGHT / CITYMAP.getHeight() * scaleZoom;

        repaint();

    }

    /**
     * Move the map when the mouse is dragged ( when zoom-in)
     *
     * @param mouseX the end position X of the mouse
     * @param mouseY the end position Y of the mouse
     */
    public void dragMap(int mouseX, int mouseY) {
        if ((CITYMAP.getWestPoint() <= originLongClicked - (mouseX - mouseClickedX) / scaleWidth)
                && (CITYMAP.getWestPoint() + CITYMAP.getWidth() >= originLongClicked - (mouseX - mouseClickedX) / scaleWidth + mapWidth)) {
            originLong = originLongClicked - (mouseX - mouseClickedX) / scaleWidth;

        }
        if ((CITYMAP.getNordPoint() >= originLatClicked + (mouseY - mouseClickedY) / scaleHeight)
                && (CITYMAP.getNordPoint() - CITYMAP.getHeight() <= originLatClicked + (mouseY - mouseClickedY) / scaleHeight - mapHeight)) {
            originLat = originLatClicked + (mouseY - mouseClickedY) / scaleHeight;
        }
        repaint();
    }


    /**
     * Updates the map view (refresh the UI)
     *
     * @param o   the Observable to check if there's changes about the map/requests and update the related data
     * @param arg the modified object
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    /**
     * Reset the zoom to display a new loaded map properly
     */
    public void resetZoom(){
        mapWidth = CITYMAP.getWidth();
        mapHeight = CITYMAP.getHeight();
        scaleZoom = 1;
        smallRoadThickness = 1;
        greatRoadThickness = 3;
        scaleWidth = VIEW_WIDTH / mapWidth * scaleZoom;
        scaleHeight = VIEW_HEIGHT / mapHeight * scaleZoom;
        originLong = CITYMAP.getWestPoint();
        originLat = CITYMAP.getNordPoint();
    }

    public int convertLongitudeToPixel(double longitude) {
        return (int) ((longitude - originLong) * scaleWidth);
    }

    public int convertLatitudeToPixel(double latitude) {
        return -(int) ((latitude - originLat) * scaleHeight);
    }

    /**
     * Draw the map on the view
     *
     * @param g Graphics to paint elements on the map
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        counterInter = 0;
        this.g = g;
        this.g2 = (Graphics2D) g;

        //display roads
        for (Map.Entry<AbstractMap.SimpleEntry<String, String>, Road> road : CITYMAP.getRoads().entrySet()) {
            displayRoad(road.getValue(), Color.white, 1, false);
        }

        //display paths
        Color outline = Color.BLACK;
        Tour t = CITYMAP.getTour();
        if (t != null) {
            if (t.getPaths().size() != 0) {
                outline = new Color(200, 0, 0);
            }
            for (Path p : t.getPaths()) {
                displayPath(p, outline);
            }

        }

        //display requests
        Distribution d = CITYMAP.getDistribution();
        displayDepot();

        if (d.getRequests().size() != 0) {


            for (Request q : d.getRequests()) {
                if (t.getPaths().size() == 0) {
                    outline = q.color.darker().darker();
                }
                displayRequest(q, outline);
            }
            if (CITYMAP.secondaryHighlight != null & CITYMAP.primaryHighlight != null) {
                displayHighlights(CITYMAP.primaryHighlight, CITYMAP.secondaryHighlight);

            }


        }

        //display shadow on the edge of the view

        GradientPaint grad = new GradientPaint(0, 0, new Color(0, 0, 0, 50), 40, 0, new Color(0, 0, 0, 0), false);
        g2.setPaint(grad);
        g2.fillRect(0, 0, 50, 800);
        grad = new GradientPaint(VIEW_WIDTH, 0, new Color(0, 0, 0, 50), VIEW_WIDTH - 40, 0, new Color(0, 0, 0, 0), false);
        g2.setPaint(grad);
        g2.fillRect(760, 0, 50, 800);
        grad = new GradientPaint(0, 0, new Color(0, 0, 0, 50), 0, 40, new Color(0, 0, 0, 0), false);
        g2.setPaint(grad);
        g2.fillRect(0, 0, 800, 50);
        grad = new GradientPaint(0, VIEW_HEIGHT, new Color(0, 0, 0, 50), 0, VIEW_HEIGHT - 40, new Color(0, 0, 0, 0), false);
        g2.setPaint(grad);
        g2.fillRect(0, 760, 800, 50);
        displaySelected(CITYMAP.i1Selected, CITYMAP.i2Selected);


        //display legend
        if (d.getDepot().getIdPointOfInterest() != -1) {
            grad = new GradientPaint(0, 30, new Color(0, 0, 0, 50), 0, 60, new Color(0, 0, 0, 0), false);
            g2.setPaint(grad);
            g2.fillRect(250, 30, 300, 30);

            g.setColor(Color.WHITE);
            g2.fillRoundRect(250, -20, 300, 60, 20, 20);
            int x = 260;
            int y = 10;

            g2.setStroke(new BasicStroke(3));
            g.setColor(Color.BLACK);
            g.drawOval(x, y, POINT_SIZE, POINT_SIZE);
            g.drawString("Pickup point", x + 25, y + 10);
            g.drawPolygon(new int[]{x + 100, x + 100 + POINT_SIZE, x + 100 + POINT_SIZE / 2},
                    new int[]{y, y, y + POINT_SIZE}, 3);
            g.drawString("Delivery point", x + 125, y + 10);
            g.fillRect(x + 200, y - 2, POINT_SIZE + 1, POINT_SIZE);
            g.drawString("Depot point", x + 225, y + 10);
            g2.draw(new Line2D.Float(x + 202, y + 10, x + 202, y + 20));
        }
    }

    /**
     * Display the points the user just added ( new request ) on the map
     *
     * @param i1Selected the intersection of the new pickup
     * @param i2Selected the intersection of the new delivery
     */
    private void displaySelected(Intersection i1Selected, Intersection i2Selected) {
        if (i1Selected != null) {
            int x1 = convertLongitudeToPixel(i1Selected.getLongitude());
            int y1 = convertLatitudeToPixel(i1Selected.getLatitude());
            g.setColor(Color.black);
            g.drawLine(x1 - 5, y1, x1 + 5, y1);
            g.drawLine(x1, y1 - 5, x1, y1 + 5);
        }
        if (i2Selected != null) {
            int x2 = convertLongitudeToPixel(i2Selected.getLongitude());
            int y2 = convertLatitudeToPixel(i2Selected.getLatitude());
            g.setColor(Color.black);
            g.drawLine(x2 - 5, y2, x2 + 5, y2);
            g.drawLine(x2, y2 - 5, x2, y2 + 5);
        }

        displayPoiToAdd();
    }

    /**
     * Highlight the pickup and delivery points of a request on the map
     * @param p1 the intersection of the pickup
     * @param p2 the intersection of the delivery
     */
    private void displayHighlights(PointOfInterest p1, PointOfInterest p2) {

        int x1 = convertLongitudeToPixel(p1.getIntersection().getLongitude());
        int y1 = convertLatitudeToPixel(p1.getIntersection().getLatitude());
        Color Color1 = p1.getColor();
        int x2 = convertLongitudeToPixel(p2.getIntersection().getLongitude());
        int y2 = convertLatitudeToPixel(p2.getIntersection().getLatitude());
        Color Color2 = p2.getColor();
        if (p1 instanceof PickupAddress) {
            g.setColor(Color1);
            g.fillOval(x1 - HIGHLIGHT_POINT_SIZE / 2, y1 - HIGHLIGHT_POINT_SIZE / 2, HIGHLIGHT_POINT_SIZE, HIGHLIGHT_POINT_SIZE);
            g.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x1 - HIGHLIGHT_POINT_SIZE / 2, y1 - HIGHLIGHT_POINT_SIZE / 2, HIGHLIGHT_POINT_SIZE, HIGHLIGHT_POINT_SIZE);
            g.setColor(Color2);
            g.fillPolygon(new int[]{x2 - SECONDARY_POINT_SIZE / 2, x2 + SECONDARY_POINT_SIZE / 2, x2}, new int[]{y2 - SECONDARY_POINT_SIZE / 2, y2 - SECONDARY_POINT_SIZE / 2, y2 + SECONDARY_POINT_SIZE / 2}, 3);
            g.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            g.drawPolygon(new int[]{x2 - SECONDARY_POINT_SIZE / 2, x2 + SECONDARY_POINT_SIZE / 2, x2}, new int[]{y2 - SECONDARY_POINT_SIZE / 2, y2 - SECONDARY_POINT_SIZE / 2, y2 + SECONDARY_POINT_SIZE / 2}, 3);
        } else {
            g.setColor(Color2);
            g.fillOval(x2 - SECONDARY_POINT_SIZE / 2, y2 - SECONDARY_POINT_SIZE / 2, SECONDARY_POINT_SIZE, SECONDARY_POINT_SIZE);
            g.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x2 - SECONDARY_POINT_SIZE / 2, y2 - SECONDARY_POINT_SIZE / 2, SECONDARY_POINT_SIZE, SECONDARY_POINT_SIZE);
            g.setColor(Color1);
            g.fillPolygon(new int[]{x1 - HIGHLIGHT_POINT_SIZE / 2, x1 + HIGHLIGHT_POINT_SIZE / 2, x1}, new int[]{y1 - HIGHLIGHT_POINT_SIZE / 2, y1 - HIGHLIGHT_POINT_SIZE / 2, y1 + HIGHLIGHT_POINT_SIZE / 2}, 3);
            g.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(2));
            g.drawPolygon(new int[]{x1 - HIGHLIGHT_POINT_SIZE / 2, x1 + HIGHLIGHT_POINT_SIZE / 2, x1}, new int[]{y1 - HIGHLIGHT_POINT_SIZE / 2, y1 - HIGHLIGHT_POINT_SIZE / 2, y1 + HIGHLIGHT_POINT_SIZE / 2}, 3);
        }

    }


    /**
     * Display a red cross following the mouse while the user try to add a new point
     */
    public void displayPoiToAdd() {
        if (CITYMAP.getPoiToAdd() != null) {
            int x = convertLongitudeToPixel(CITYMAP.getPoiToAdd().getLongitude());
            int y = convertLatitudeToPixel(CITYMAP.getPoiToAdd().getLatitude());
            g.setColor(Color.RED);
            g.drawLine(x - 5, y, x + 5, y);
            g.drawLine(x, y - 5, x, y + 5);
        }
    }

    /**
     * Draw the roads on the map
     *
     * @param r           the road to be drawn
     * @param c           the color of the road
     * @param thickness   the thickness of the road
     * @param displayTour boolean if the tour is computed or not
     */
    public void displayRoad(Road r, Color c, double thickness, boolean displayTour) {
        int x1 = convertLongitudeToPixel(r.getOrigin().getLongitude());
        int y1 = convertLatitudeToPixel(r.getOrigin().getLatitude()); /* the latitude is reversed*/
        int x2 = convertLongitudeToPixel(r.getDestination().getLongitude());
        int y2 = convertLatitudeToPixel(r.getDestination().getLatitude());
        if (!displayTour) {
            if (r.getName().contains("Boulevard") || r.getName().contains("Avenue") || r.getName().contains("Cours")) {
                thickness = greatRoadThickness;
                c = Color.decode("#ffd800");
            } else if (r.getName().contains("Impasse")) {
                thickness = smallRoadThickness;
                c = Color.WHITE;
            } else {
                thickness = smallRoadThickness;
                c = Color.WHITE;
            }
        } else if (counterInter++ == 10) {
            counterInter = 0;
            double theta = Math.atan2(x1 - x2, y1 - y2);
            int middleRoadX = (x1 + x2) / 2;
            int middleRoadY = (y1 + y2) / 2;
            int fSize = 8;
            g2.fillPolygon(new int[]{middleRoadX,
                    (int) (middleRoadX + fSize * (Math.cos(-theta) - Math.sin(-theta))),
                    (int) (middleRoadX - fSize * (Math.cos(theta) - Math.sin(theta)))}, new int[]{middleRoadY,
                    (int) (middleRoadY + fSize * (Math.sin(-theta) + Math.cos(-theta))),
                    (int) (middleRoadY + fSize * (Math.sin(theta) + Math.cos(theta)))}, 3);
        }
        g.setColor(c);
        g2.setStroke(new BasicStroke((float) thickness));
        g2.draw(new Line2D.Float(x1, y1, x2, y2));

    }

    /**
     * Draw the path between each point of interest
     * @param p the path is be colored
     * @param c the color of the path's roads
     */
    public void displayPath(Path p, Color c) {
        for (Road r : p.getRoads()) {
            displayRoad(r, c, 3, true);
        }
    }

    /**
     * Add requests on the map : draw forms depending on the type of the point
     *
     * @param q       the request to be added on the map
     * @param outline the color of the pair of points (a request)
     */
    public void displayRequest(Request q, Color outline) {
        int x1 = convertLongitudeToPixel(q.getPickup().getIntersection().getLongitude());
        int y1 = convertLatitudeToPixel(q.getPickup().getIntersection().getLatitude());
        int x2 = convertLongitudeToPixel(q.getDelivery().getIntersection().getLongitude());
        int y2 = convertLatitudeToPixel(q.getDelivery().getIntersection().getLatitude());

        g.setColor(q.color);
        g.fillOval(x1 - POINT_SIZE / 2, y1 - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
        g.setColor(outline);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x1 - POINT_SIZE / 2, y1 - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
        g.setColor(q.color);
        g.fillPolygon(new int[]{x2 - POINT_SIZE / 2, x2 + POINT_SIZE / 2, x2}, new int[]{y2 - POINT_SIZE / 2, y2 - POINT_SIZE / 2, y2 + POINT_SIZE / 2}, 3);
        g.setColor(outline);
        g2.setStroke(new BasicStroke(3));
        g.drawPolygon(new int[]{x2 - POINT_SIZE / 2, x2 + POINT_SIZE / 2, x2}, new int[]{y2 - POINT_SIZE / 2, y2 - POINT_SIZE / 2, y2 + POINT_SIZE / 2}, 3);


    }

    /**
     * Add depot point on the map : draw a flag
     */
    public void displayDepot() {
        if (CITYMAP.getDistribution().getDepot().getIntersection() != null) {
            int x = convertLongitudeToPixel(CITYMAP.getDistribution().getDepot().getIntersection().getLongitude());
            int y = convertLatitudeToPixel(CITYMAP.getDistribution().getDepot().getIntersection().getLatitude());
            g.setColor(Color.black);
            g.fillRect(x - 2, y - 25, POINT_SIZE + 1, POINT_SIZE);
            g2.setStroke(new BasicStroke(3));
            g2.draw(new Line2D.Float(x, y - 15, x, y));
        }
    }

    /**
     * Search the closest PointOfInterest to the clicked position on the map
     *
     * @param x the x position of our point
     * @param y the y position of our point
     * @return the closest PointOfInterest to our point
     */
    public PointOfInterest getClosestPointOfInterest(int x, int y) {
        for (PointOfInterest poi : this.CITYMAP.getTour().getPointOfInterests()) {
            int xPoi = convertLongitudeToPixel(poi.getIntersection().getLongitude());
            int yPoi = convertLatitudeToPixel(poi.getIntersection().getLatitude());
            if (x <= xPoi + POINT_SIZE && x >= xPoi - POINT_SIZE && // check if point is inside the shape of the specific point of interest
                    y <= yPoi + POINT_SIZE && y >= yPoi - POINT_SIZE) {
                return poi;
            }
        }
        return null;
    }

    /**
     * Search the closest Intersection to the clicked position on the map
     *
     * @param x the x position of our point
     * @param y the y position of our point
     * @return the closest Intersection to our point
     */
    public Intersection getClosestIntersection(int x, int y) {
        for (Intersection i : this.CITYMAP.getIntersections().values()) {
            int xPoi = convertLongitudeToPixel(i.getLongitude());
            int yPoi = convertLatitudeToPixel(i.getLatitude());
            if (x <= xPoi + POINT_SIZE / 2 && x >= xPoi - POINT_SIZE / 2 &&
                    y <= yPoi + POINT_SIZE / 2 && y >= yPoi - POINT_SIZE / 2) {
                return i;
            }
        }
        return null;
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

    public void fixOrigin() {
        this.originLatClicked = originLat;
        this.originLongClicked = originLong;

    }
}