package view;

import model.*;

import observer.Observable;
import observer.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * @author 4IF-4114
 */
public class RoadmapView extends JPanel implements Observer {

    private Tour tour;
    private final int VIEW_HEIGHT = 770;
    private final int VIEW_WIDTH = 400;
    private final JButton addButton = new JButton("Add");
    private final JButton delButton = new JButton("Remove");
    private final int buttonHeight = 30;
    private final int buttonWidth = 100;
    private JPanel roadmap;

    /**
     * Default constructor
     * @param tour
     * @param window
     */
    public RoadmapView(Tour tour, Window window) {
        super();

        this.tour = tour;
        this.tour.addObserver(this); // this observes tour

        this.setBorder(BorderFactory.createTitledBorder("Roadmap"));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.RED);
        this.setSize(VIEW_WIDTH, VIEW_HEIGHT);

        this.roadmap = new JPanel();
        this.roadmap.setLayout(new BoxLayout(this.roadmap, BoxLayout.Y_AXIS));
        this.roadmap.setBackground(Color.GREEN);
        JScrollPane scrollPanel = new JScrollPane(this.roadmap,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPanel);
        scrollPanel.setBackground(Color.BLACK);
        scrollPanel.setSize(0, VIEW_HEIGHT - this.buttonHeight);

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

    /**
     * @param observed 
     * @param object 
     * @return
     */
    public void update(Observable observed, Object object) {
        System.out.println("Roadmap/update");
        System.out.println(this.tour.getPointOfInterests().size());
        System.out.println(this.tour.getPaths().size());

        int i = 0;

        this.roadmap.removeAll();

        for (PointOfInterest poi : this.tour.getPointOfInterests()) {
            System.out.println(poi.toString());

            JPanel subPanel = new JPanel();
            subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
            subPanel.setBackground(Color.YELLOW);
            if (poi.getIdPointOfInterest() == 0) { // Depot
                subPanel.setBorder(BorderFactory.createTitledBorder("Border"));
            } else {
                subPanel.setBorder(BorderFactory.createTitledBorder("Point of Interest #" + poi.getIdPointOfInterest()));
            }

            subPanel.add(new JLabel("    Latitude: " + poi.getIntersection().getLatitude()));
            subPanel.add(new JLabel("    Longitude: " + poi.getIntersection().getLongitude()));
            subPanel.add(new JLabel("    Duration: " + poi.getDuration()));

            this.roadmap.add(subPanel);

            Path path = this.tour.getPaths().get(i);
            for (Road road : path.getRoads()) {

                JPanel subPanel2 = new JPanel();
                subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));
                subPanel2.setBackground(Color.PINK);

                subPanel2.add(new JLabel("    Name: " + road.getName()));
                subPanel2.add(new JLabel("    Length: " + road.getLength()));
                subPanel2.add(new JLabel("    Intersection: " + road.getDestination().getId()));

                this.roadmap.add(subPanel2);
            }

            i += 1;
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

}