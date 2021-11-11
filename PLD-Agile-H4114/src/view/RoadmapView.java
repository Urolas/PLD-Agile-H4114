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
    private final int VIEW_HEIGHT = 600;
    private CityMap cityMap;
    private final int VIEW_WIDTH = 300;
    private final int VERTICAL_MARGIN = 5;
    private final int HORIZONTAL_MARGIN = 15;
    private JButton addButton;
    private JButton delButton;
    private final int BUTTON_HEIGHT = 30;
    private final int BUTTON_WIDTH = 100;
    private JPanel roadmap;
    private boolean start = true;
    private int arrivalTime;
    private GridBagConstraints gc;
    private Window window;

    /**
     * Default constructor
     * @param tour
     * @param window
     */
    public RoadmapView(CityMap citymap, Window window) {
        super();
        this.window = window;
        this.cityMap = citymap;
        this.cityMap.addObserver(this); // this observes tour
        this.cityMap.distribution.addObserver(this); // this observes tour

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.setSize(VIEW_WIDTH, VIEW_HEIGHT);

        GridBagLayout grid = new GridBagLayout();
        gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.NORTH;

        this.roadmap = new JPanel(grid);
        this.roadmap.setBackground(Color.WHITE);
        JLabel title = new JLabel("ROADMAP");
        title.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        title.setBounds(20,5,100,30);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        JScrollPane scrollPanel = new JScrollPane(this.roadmap,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(30);
        this.add(scrollPanel);
        scrollPanel.setBackground(Color.BLACK);
        scrollPanel.setSize(0, VIEW_HEIGHT - this.BUTTON_HEIGHT);

        this.add(scrollPanel);


        window.getContentPane().add(this);
    }


    public void update(Observable observed, Object object) {

        this.start = true;
        int i = 0;
        this.arrivalTime = this.cityMap.distribution.getDepot().getDepartureTime().toSecondOfDay();

        this.roadmap.removeAll();

        if (this.cityMap.tour.getPointOfInterests().size() == 0) { // Load distribution

            Set<Request> requestList = this.cityMap.distribution.getRequests();
            if(requestList.size() != 0 ) {
                addRequestToRoadmap(requestList);
            }

        }
        else { //Compute Tour

            List<PointOfInterest> pointList = this.cityMap.tour.getPointOfInterests();
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
        int number = 0;

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(null);
        firstPanel.setPreferredSize(new Dimension(280-HORIZONTAL_MARGIN*2,80));


        int hours = arrivalTime / 3600;
        int minutes = (arrivalTime % 3600) / 60;
        int seconds = arrivalTime % 60;

        JLabel title = new JLabel("Starting Point");
        title.setBounds(50,15,150,20);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel depTime = new JLabel("Departure Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
        depTime.setBounds(40,35,200,20);
        depTime.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel colorPanel = new JPanel();
        colorPanel.setBounds(0,0,20,120);
        firstPanel.add(colorPanel);
        colorPanel.setBackground(Color.BLACK);


        firstPanel.add(title);
        firstPanel.add(depTime);

        gc.gridx = 0;
        gc.weighty = 0;
        int posCard = 0;
        gc.gridy = posCard++;
        gc.insets = new Insets(VERTICAL_MARGIN,0,VERTICAL_MARGIN,0);

        roadmap.add(firstPanel,gc);

        for (Request request : requestList) {
            number++;

            JPanel subPanel1 = new JPanel();
            subPanel1.setLayout(null);
            subPanel1.setPreferredSize(new Dimension(280-HORIZONTAL_MARGIN*2,80));

            JLabel title1 = new JLabel("Pickup Point "+ number );
            title1.setBounds(50,15,150,20);
            title1.setFont(new Font("Segoe UI", Font.BOLD, 16));

            JLabel title2 = new JLabel("Delivery Point "+ number );
            title2.setBounds(50,45,150,20);
            title2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            subPanel1.add(title2);
            subPanel1.add(title1);

            gc.gridx = 0;
            gc.weighty = 0;
            if (number == requestList.size()){
                gc.weighty = 1;
            }
            gc.gridy = posCard++;
            roadmap.add(subPanel1,gc);

            JPanel colorPanel1 = new JPanel();
            colorPanel1.setBounds(0,0,20,120);
            subPanel1.add(colorPanel1);
            colorPanel1.setBackground(request.getDelivery().getColor());
        }
    }

    public void addPointOfInterestToRoadMap(List<PointOfInterest> pointList) {

        List<Path> pathList = this.cityMap.tour.getPaths();
        gc.gridy = 0;
        gc.gridx = 0;
        gc.insets = new Insets(VERTICAL_MARGIN,0,VERTICAL_MARGIN,0);
        for (int poiNum = 0; poiNum < pointList.size(); poiNum++) {
            PointOfInterest poi = pointList.get(poiNum);

            JPanel subPanel = new JPanel();
            subPanel.setLayout(null);

            JPanel colorPanel = new JPanel();
            colorPanel.setBounds(0,0,20,120);
            subPanel.add(colorPanel);

            JLabel titleLabel = new JLabel();

            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            titleLabel.setBounds(40,10,250,20);
            if (poi.getIdPointOfInterest() == 0) { // Depot
                subPanel.setPreferredSize(new Dimension(280-HORIZONTAL_MARGIN*2,60));
                if (this.start) {
                    titleLabel.setText("START (Depot Point)");


                } else {
                    titleLabel.setText("END (Depot Point)");
                }
            } else {
                subPanel.setPreferredSize(new Dimension(280-HORIZONTAL_MARGIN*2,120));

                if (poi instanceof DeliveryAddress) {
                    titleLabel.setText("Delivery Point " + ((poi.getIdPointOfInterest()-2)/2+1));

                } else if (poi instanceof PickupAddress) {
                    titleLabel.setText("Pickup Point " + ((poi.getIdPointOfInterest()-1)/2+1));
                }
            }
            
            subPanel.add(titleLabel);
            int durationRoad=0;

            if(poiNum<pointList.size() && !start) {
                Path path = (Path) (pathList.get(poiNum-1));
                arrivalTime += (int)(path.getLength() / 15000. * 3600.);

            }

            int hours = arrivalTime / 3600;
            int minutes = (arrivalTime % 3600) / 60;
            int seconds = arrivalTime % 60;
            if(cityMap.primaryHighlight==poi){
                subPanel.setBackground(new Color(poi.getColor().getRed(),poi.getColor().getGreen(),poi.getColor().getBlue(),200));
                colorPanel.setBackground(poi.getColor());

            } else if (cityMap.secondaryHighlight==poi){
                subPanel.setBackground(new Color(poi.getColor().getRed(),poi.getColor().getGreen(),poi.getColor().getBlue(),100));
                colorPanel.setBackground(poi.getColor());

            } else{
                colorPanel.setBackground(poi.getColor());

            }
            int departureTime = arrivalTime+poi.getDuration();

            if(this.start){
                JLabel departureTimeLabel = new JLabel("Departure Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                departureTimeLabel.setBounds(35,30,250,20);
                departureTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(departureTimeLabel);
                colorPanel.setBackground(Color.BLACK);
                gc.weighty = 0;
            }else if(poiNum!=pointList.size()-1) {
                JLabel arrivalTimeLabel = new JLabel("Arrival Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                arrivalTimeLabel.setBounds(35,35,250,20);
                arrivalTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(arrivalTimeLabel);
              
                JLabel durLabel = new JLabel("Duration: " + String.format("%02dmin%02dsec", (poi.getDuration() % 3600) / 60, poi.getDuration() % 60));
                durLabel.setBounds(35,55,200,20);
                durLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(durLabel);
              
                JLabel departureTimeLabel = new JLabel("Departure Time: " + String.format("%02d:%02d:%02d", departureTime / 3600, (departureTime % 3600)/60, departureTime % 60));
                departureTimeLabel.setBounds(35,75,250,20);
                departureTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(departureTimeLabel);
                gc.weighty = 0;

                JButton upButton = new JButton("▲");
                upButton.setBounds(200,25,30,30);
                upButton.addActionListener(window.getButtonListener());
                upButton.setMargin(new Insets(0,0,5,0));
                upButton.setActionCommand("up"+poi.getIdPointOfInterest());
                subPanel.add(upButton);

                JButton downButton = new JButton("▼");
                downButton.setBounds(200,60,30,30);
                downButton.addActionListener(window.getButtonListener());
                downButton.setMargin(new Insets(0,0,5,0));
                downButton.setActionCommand("down"+poi.getIdPointOfInterest());
                subPanel.add(downButton);
            }else{
                JLabel arrivalTimeLabel = new JLabel("Arrival Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                arrivalTimeLabel.setBounds(35,30,250,20);
                arrivalTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(arrivalTimeLabel);
                gc.weighty = 1;
                colorPanel.setBackground(Color.BLACK);
            }



            arrivalTime += poi.getDuration();
            if(this.start){
                this.start = false;
            }

            arrivalTime +=durationRoad;
            roadmap.add(subPanel,gc);
            
            gc.gridy ++;
        }
    }
}
