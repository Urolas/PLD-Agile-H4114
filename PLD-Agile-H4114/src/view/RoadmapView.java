/**
 * RoadmapView
 *
 * @author 4IF-4114
 */
package view;

import model.*;

import observer.Observable;
import observer.Observer;

import javax.swing.*;
import java.awt.*;

import java.util.List;
import java.util.Set;

/**
 * The roadmap view of the window, where the imported requests are shown
 */
public class RoadmapView extends JPanel implements Observer {

    private boolean start = true;
    private int arrivalTime;
    private final int VIEW_HEIGHT = 600;
    private final CityMap CITYMAP;
    private final int VIEW_WIDTH = 300;
    private final int VERTICAL_MARGIN = 5;
    private final int HORIZONTAL_MARGIN = 15;
    private final int BUTTON_HEIGHT = 30;
    private final JPanel ROADMAP;
    private final GridBagConstraints GC;
    private final Window WINDOW;

    /**
     * Constructor of the RoadmapView
     *
     * @param citymap the current citymap with the data
     * @param window  the application window
     */
    public RoadmapView(CityMap citymap, Window window) {
        super();
        this.WINDOW = window;
        this.CITYMAP = citymap;
        this.CITYMAP.addObserver(this); // this observes tour
        this.CITYMAP.distribution.addObserver(this); // this observes distribution

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
        this.setSize(VIEW_WIDTH, VIEW_HEIGHT);

        GridBagLayout grid = new GridBagLayout();
        GC = new GridBagConstraints();
        GC.anchor = GridBagConstraints.NORTH;

        this.ROADMAP = new JPanel(grid);
        this.ROADMAP.setBackground(Color.WHITE);
        JLabel title = new JLabel("ROADMAP");
        title.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        title.setBounds(20, 5, 100, 30);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        JScrollPane scrollPanel = new JScrollPane(this.ROADMAP,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(30);
        this.add(scrollPanel);
        scrollPanel.setBackground(Color.BLACK);
        scrollPanel.setSize(0, VIEW_HEIGHT - this.BUTTON_HEIGHT);

        this.add(scrollPanel);


        window.getContentPane().add(this);
    }

    /**
     * Updates the roadmap view (refresh the UI) (when the tour is computed for exemple)
     *
     * @param observed the Observable to check if there's changes about the map/requests and update the related data
     * @param object   the modified object
     */
    public void update(Observable observed, Object object) {

        this.start = true;
        this.arrivalTime = this.CITYMAP.distribution.getDepot().getDepartureTime().toSecondOfDay();

        this.ROADMAP.removeAll();

        //When the user just import the Distribution (List of request)
        if (this.CITYMAP.tour.getPointOfInterests().size() == 0) {

            Set<Request> requestList = this.CITYMAP.distribution.getRequests();
            if (requestList.size() != 0) {
                addRequestToRoadmap(requestList);
            }

        //After the tour is computed, add details to the roadmap and follow another order
        } else {

            List<PointOfInterest> pointList = this.CITYMAP.tour.getPointOfInterests();
            addPointOfInterestToRoadMap(pointList);

        }

        this.revalidate();
        this.repaint();
    }




    /**
     * After importing the requests .xml file, add the requests to the roadmap view
     *
     * @param requestList the list of the requests from the imported data
     */
    public void addRequestToRoadmap(Set<Request> requestList) { //Add Request to roadmap in order
        int number = 0;

        //Create the roadmap style panel
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(null);
        firstPanel.setPreferredSize(new Dimension(280 - HORIZONTAL_MARGIN * 2, 80));

        //Convert the arrival time from seconds to hours:minutes:seconds
        int hours = arrivalTime / 3600;
        int minutes = (arrivalTime % 3600) / 60;
        int seconds = arrivalTime % 60;

        //Set the font and size of each JLabel (text)
        JLabel title = new JLabel("Starting Point");
        title.setBounds(50, 15, 150, 20);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel depTime = new JLabel("Departure Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
        depTime.setBounds(40, 35, 200, 20);
        depTime.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel colorPanel = new JPanel();
        colorPanel.setBounds(0, 0, 20, 120);
        firstPanel.add(colorPanel);
        colorPanel.setBackground(Color.BLACK);

        firstPanel.add(title);
        firstPanel.add(depTime);

        GC.gridx = 0;
        GC.weighty = 0;
        int posCard = 0;
        GC.gridy = posCard++;
        GC.insets = new Insets(VERTICAL_MARGIN, 0, VERTICAL_MARGIN, 0);

        ROADMAP.add(firstPanel, GC);

        //Loop checking each request from the requestLit after the tour is computed
        for (Request request : requestList) {
            number++;

            JPanel subPanel1 = new JPanel();
            subPanel1.setLayout(null);
            subPanel1.setPreferredSize(new Dimension(280 - HORIZONTAL_MARGIN * 2, 80));

            //Set the font and size of some JLabel

            JLabel title1 = new JLabel("Pickup Point " + number);
            title1.setBounds(50, 15, 150, 20);
            title1.setFont(new Font("Segoe UI", Font.BOLD, 16));

            JLabel title2 = new JLabel("Delivery Point " + number);
            title2.setBounds(50, 45, 150, 20);
            title2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            subPanel1.add(title2);
            subPanel1.add(title1);

            GC.gridx = 0;
            GC.weighty = 0;
            if (number == requestList.size()) {
                GC.weighty = 1;
            }
            GC.gridy = posCard++;
            ROADMAP.add(subPanel1, GC);

            JPanel colorPanel1 = new JPanel();
            colorPanel1.setBounds(0, 0, 20, 120);
            subPanel1.add(colorPanel1);
            colorPanel1.setBackground(request.getDelivery().getColor());
        }
    }

    /**
     * After computing a tour, add the points and their data to the roadmap, following their order from the path
     * @param pointList list of the points of interest
     */
    public void addPointOfInterestToRoadMap(List<PointOfInterest> pointList) {

        List<Path> pathList = this.CITYMAP.tour.getPaths();
        GC.gridy = 0;
        GC.gridx = 0;
        GC.insets = new Insets(VERTICAL_MARGIN, 0, VERTICAL_MARGIN, 0);

        //Loop to check every point of interest of the list
        for (int poiNum = 0; poiNum < pointList.size(); poiNum++) {
            PointOfInterest poi = pointList.get(poiNum);

            JPanel subPanel = new JPanel();
            subPanel.setLayout(null);

            JPanel colorPanel = new JPanel();
            colorPanel.setBounds(0, 0, 20, 120);
            subPanel.add(colorPanel);

            JLabel titleLabel = new JLabel();

            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            titleLabel.setBounds(40, 10, 250, 20);
            if (poi.getIdPointOfInterest() == 0) { // The point of interest is a depot
                subPanel.setPreferredSize(new Dimension(280 - HORIZONTAL_MARGIN * 2, 60));

                //If the point of interest is the start
                if (this.start) {
                    titleLabel.setText("START (Depot Point)");

                //If the point of interest is the end
                } else {
                    titleLabel.setText("END (Depot Point)");
                }
            } else {
                subPanel.setPreferredSize(new Dimension(280 - HORIZONTAL_MARGIN * 2, 120));

                //If the point of interest is a delivery address
                if (poi instanceof DeliveryAddress) {
                    titleLabel.setText("Delivery Point " + ((poi.getIdPointOfInterest() - 2) / 2 + 1));

                //If the point of interest is a pickup address
                } else if (poi instanceof PickupAddress) {
                    titleLabel.setText("Pickup Point " + ((poi.getIdPointOfInterest() - 1) / 2 + 1));
                }
            }

            subPanel.add(titleLabel);

            //Calculate the arrival time to each point by adding the path's duration
            if (poiNum < pointList.size() && !start) {
                Path path = (pathList.get(poiNum - 1));
                arrivalTime += (int) (path.getLength() / 15000. * 3600.);
            }

            //Convert the arrival time (seconds) to hours:minutes:seconds
            int hours = arrivalTime / 3600;
            int minutes = (arrivalTime % 3600) / 60;
            int seconds = arrivalTime % 60;

            //Set the style for each panel
            if (CITYMAP.primaryHighlight == poi) {
                subPanel.setBackground(new Color(poi.getColor().getRed(), poi.getColor().getGreen(), poi.getColor().getBlue(), 200));
                colorPanel.setBackground(poi.getColor());

            } else if (CITYMAP.secondaryHighlight == poi) {
                subPanel.setBackground(new Color(poi.getColor().getRed(), poi.getColor().getGreen(), poi.getColor().getBlue(), 100));
                colorPanel.setBackground(poi.getColor());

            } else {
                colorPanel.setBackground(poi.getColor());

            }

            //Calculate the time to depart from the point
            int departureTime = arrivalTime + poi.getDuration();

            //If the point of interest is the start
            if (this.start) {
                JLabel departureTimeLabel = new JLabel("Departure Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                departureTimeLabel.setBounds(35, 30, 250, 20);
                departureTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(departureTimeLabel);
                colorPanel.setBackground(Color.BLACK);
                GC.weighty = 0;

            //If the point of interest is the end
            } else if (poiNum != pointList.size() - 1) {
                JLabel arrivalTimeLabel = new JLabel("Arrival Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                arrivalTimeLabel.setBounds(35, 35, 250, 20);
                arrivalTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(arrivalTimeLabel);

                JLabel durationLabel = new JLabel("Duration: " + String.format("%02dmin%02dsec", (poi.getDuration() % 3600) / 60, poi.getDuration() % 60));
                durationLabel.setBounds(35, 55, 200, 20);
                durationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(durationLabel);

                JLabel departureTimeLabel = new JLabel("Departure Time: " + String.format("%02d:%02d:%02d", departureTime / 3600, (departureTime % 3600) / 60, departureTime % 60));
                departureTimeLabel.setBounds(35, 75, 250, 20);
                departureTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(departureTimeLabel);
                GC.weighty = 0;

                JButton upButton = new JButton("▲");
                upButton.setBounds(200, 25, 30, 30);
                upButton.addActionListener(WINDOW.getButtonListener());
                upButton.setMargin(new Insets(0, 0, 5, 0));
                upButton.setActionCommand("up" + poi.getIdPointOfInterest());
                subPanel.add(upButton);

                JButton downButton = new JButton("▼");
                downButton.setBounds(200, 60, 30, 30);
                downButton.addActionListener(WINDOW.getButtonListener());
                downButton.setMargin(new Insets(0, 0, 5, 0));
                downButton.setActionCommand("down" + poi.getIdPointOfInterest());
                subPanel.add(downButton);

            //If the point of interest is a delivery point or a pickup point
            } else {
                JLabel arrivalTimeLabel = new JLabel("Arrival Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
                arrivalTimeLabel.setBounds(35, 30, 250, 20);
                arrivalTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                subPanel.add(arrivalTimeLabel);
                GC.weighty = 1;
                colorPanel.setBackground(Color.BLACK);
            }

            //Add the duration of the current point for the arrival time of the next point
            arrivalTime += poi.getDuration();
            if (this.start) {
                this.start = false;
            }


            ROADMAP.add(subPanel, GC);

            GC.gridy++;
        }
    }
    public int getViewWidth() {
        return VIEW_WIDTH;
    }
}
