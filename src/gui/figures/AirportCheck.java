package gui.figures;

import gui.pages.MapPage;

import javax.swing.*;
import java.awt.*;

public class AirportCheck extends JCheckBox {
    public AirportCheck(MapPage.Map owner,AirportSquare airportSquare){
        super(airportSquare.getAirport().getVisual());
        this.owner=owner;
        this.airportSquare=airportSquare;
        addItemListener(e-> {
            this.owner.getMapPage().getOwner().resetTime();
            this.airportSquare.setVisible(this.isSelected());
            this.owner.repaint();
        });
    }
    private AirportSquare airportSquare;
    private MapPage.Map owner;
}
