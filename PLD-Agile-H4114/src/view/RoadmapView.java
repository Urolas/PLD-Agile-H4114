package view;

import model.*;

import observer.Observable;
import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import  java.util.*;
import java.util.List;

/**
 * @author 4IF-4114
 */
public class RoadmapView extends JPanel implements Observer {

    private Tour tour;
    private Distribution distribution;
    private final int VIEW_HEIGHT = 770;
    private final int VIEW_WIDTH = 300;
    private final JButton addButton = new JButton("Add");
    private final JButton delButton = new JButton("Remove");
    private final int BUTTON_HEIGHT = 30;
    private final int BUTTON_WIDTH = 100;
    private JPanel roadmap;
    private boolean start = true;
    private int arrivalTime;

    /**
     * Default constructor
     * @param tour
     * @param window
     */
    public RoadmapView(CityMap citymap, Window window) {
        super();

        this.tour = citymap.getTour();
        this.tour.addObserver(this); // this observes tour
        this.distribution = citymap.getDistribution();
        this.distribution.addObserver(this); // this observes distribution

        this.setBorder(BorderFactory.createTitledBorder("Roadmap"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.GRAY);
        this.setSize(VIEW_WIDTH, VIEW_HEIGHT);

        this.roadmap = new JPanel(new BorderLayout());
        this.roadmap.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPanel = new JScrollPane(this.roadmap,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPanel);
        scrollPanel.setBackground(Color.BLACK);
        scrollPanel.setSize(0, VIEW_HEIGHT - this.BUTTON_HEIGHT);

        JPanel buttonPanel = new JPanel();
        this.add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.BLUE);
        buttonPanel.add(this.addButton);
        buttonPanel.add(this.delButton);

        this.add(scrollPanel);
        this.add(buttonPanel);

        window.getContentPane().add(this);
    }


    public void update(Observable observed, Object object) {
        System.out.println("Roadmap/update");
        System.out.println(this.tour.getPointOfInterests().size());
        System.out.println(this.tour.getPaths().size());
        System.out.println(this.distribution.getRequests().size());

        this.start = true;
        int i = 0;
        this.arrivalTime = this.distribution.getDepot().getDepartureTime().toSecondOfDay();

        this.roadmap.removeAll();

        if (this.tour.getPointOfInterests().size() == 0) { // Load distribution

            Set<Request> requestList = this.distribution.getRequests();
            if(requestList.size() != 0 ) {
                addRequestToRoadmap(requestList);
            }

        }
        else { //Compute Tour

            List<PointOfInterest> pointList = this.tour.getPointOfInterests();
            addPointOfInterestToRoadMap(pointList);

        }

        this.revalidate();
        this.repaint();
    }

    public int getViewHeight() {
        return VIEW_HEIGHT;
    }

    public int getViewWidth() {
        return VIEW_WIDTH;
    }

    public void addRequestToRoadmap(Set<Request> requestList){ //Add Request to roadmap in order


        JPanel panel = new JPanel(new GridLayout(0, 1));
        int number = 0;

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));
        firstPanel.setBackground(Color.WHITE);
        firstPanel.setBorder(BorderFactory.createTitledBorder("Starting point"));

        int hours = arrivalTime / 3600;
        int minutes = (arrivalTime % 3600) / 60;
        int seconds = arrivalTime % 60;

        firstPanel.add(new JLabel("    Departure Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)));
        firstPanel.add(new JLabel("    Latitude: "+ this.distribution.getDepot().getIntersection().getLatitude()));
        firstPanel.add(new JLabel("    Longitude: "+ this.distribution.getDepot().getIntersection().getLongitude()));

        panel.add(firstPanel);

        for (Request request : requestList) {
            number++;
            System.out.println(request);

            JPanel subPanel1 = new JPanel();
            subPanel1.setLayout(new BoxLayout(subPanel1, BoxLayout.Y_AXIS));
            subPanel1.setBackground(Color.WHITE);
            subPanel1.setBorder(BorderFactory.createTitledBorder("Pickup Point #"+ number ));

            JPanel subPanel2 = new JPanel();
            subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));
            subPanel2.setBackground(Color.WHITE);
            subPanel2.setBorder(BorderFactory.createTitledBorder("Delivery Point #"+ number ));

            subPanel1.add(new JLabel("    Latitude: " + request.getPickup().getIntersection().getLatitude()));
            subPanel1.add(new JLabel("    Longitude: " + request.getPickup().getIntersection().getLongitude()));
            subPanel2.add(new JLabel("    Latitude: " + request.getDelivery().getIntersection().getLatitude()));
            subPanel2.add(new JLabel("    Longitude: " + request.getDelivery().getIntersection().getLongitude()));

            panel.add(subPanel1);
            panel.add(subPanel2);

        }
        this.roadmap.add(panel,BorderLayout.NORTH);

    }

    public void addPointOfInterestToRoadMap(List<PointOfInterest> pointList) {


        JPanel panel = new JPanel(new GridLayout(0, 1));
        List<Path> pathList = this.tour.getPaths();

        for (int poiNum = 0; poiNum < pointList.size(); poiNum++) {
            PointOfInterest poi = pointList.get(poiNum);

            JPanel subPanel = new JPanel();
            subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
            subPanel.setBackground(Color.WHITE);

            if (poi.getIdPointOfInterest() == 0) { // Depot
                if (this.start) {
                    subPanel.setBorder(BorderFactory.createTitledBorder("START"));
                } else {
                    subPanel.setBorder(BorderFactory.createTitledBorder("END"));
                }
            } else {
                if (poi instanceof DeliveryAddress) {
                    subPanel.setBorder(BorderFactory.createTitledBorder("Delivery Point #" + poi.getIdPointOfInterest()));
                } else if (poi instanceof PickupAddress) {
                    subPanel.setBorder(BorderFactory.createTitledBorder("Pickup Point #" + poi.getIdPointOfInterest()));
                }
            }

            if(poiNum<pointList.size() && !start) {
                Path path = (Path) (pathList.get(poiNum-1));
                arrivalTime += (int)(path.getLength() / 15000. * 3600.);

            }

            int hours = arrivalTime / 3600;
            int minutes = (arrivalTime % 3600) / 60;
            int seconds = arrivalTime % 60;

            int departureTime = arrivalTime+poi.getDuration();

            subPanel.add(new JLabel("    Latitude: " + poi.getIntersection().getLatitude()));
            subPanel.add(new JLabel("    Longitude: " + poi.getIntersection().getLongitude()));
            if(this.start){
                subPanel.add(new JLabel("    Departure Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)));
            }else if(poiNum!=pointList.size()-1){
                subPanel.add(new JLabel("    Arrival Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)));
                subPanel.add(new JLabel("    Duration: " + String.format("%02dmin%02dsec", (poi.getDuration() % 3600) / 60, poi.getDuration() % 60)));
                subPanel.add(new JLabel("    Departure Time: " + String.format("%02d:%02d:%02d", departureTime / 3600, (departureTime % 3600)/60, departureTime % 60)));
            }else{
                subPanel.add(new JLabel("    Arrival Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds)));
            }

            arrivalTime += poi.getDuration();

            if(this.start){
                this.start = false;
            }

            panel.add(subPanel);


        }
        this.roadmap.add(panel,BorderLayout.NORTH);


    }


}
