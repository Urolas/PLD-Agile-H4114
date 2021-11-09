package controller;


import org.xml.sax.SAXException;
import view.MapView;
import view.Window;
import filecontrol.XMLDeserializer;
import filecontrol.XMLException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author 4IF-4114
 */
public class DistributionState implements State {

    /**
     * Default constructor
     */
    public DistributionState() {
    }
    @Override
    public void computeTour(Controller controller, Window window) {
        controller.getCitymap().computeTour();
        controller.setCurrentState(controller.tourState);
    }
    public void loadDistribution(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadDistribution(c.getCitymap());
        c.setCurrentState(c.distributionState);
    }
    public void loadMap(Controller c, Window w) throws XMLException, ParserConfigurationException, IOException, SAXException {
        XMLDeserializer.loadCityMap(c.getCitymap());
        c.setCurrentState(c.citymapState);
    }



    public void keyStroke(MapView mapView, int keyCode){

        if (keyCode == 37){
            System.out.println("Touche appuyée gauche");
        }
        if (keyCode == 38){
            System.out.println("Touche appuyée haut");
        }
        if (keyCode == 39){
            System.out.println("Touche appuyée droite");
        }
        if (keyCode == 40){
            System.out.println("Touche appuyée bas");
        }

    }

    public  void enableButtons(Window window, ListOfCommands loc) {
        window.enableButton("Load a city map", true);
        window.enableButton("Load a distribution", true);
        window.enableButton("Compute a tour", true);
        window.enableButton("Modify the distribution", false);
        window.enableButton("Remove", false);
        window.enableButton("Redo", false);
        window.enableButton("Undo", false);
    }

}