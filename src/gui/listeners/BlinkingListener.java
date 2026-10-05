package gui.listeners;

import gui.figures.AirportSquare;
import gui.figures.FlightCircle;
import gui.pages.MapPage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BlinkingListener extends MouseAdapter {

    public BlinkingListener(MapPage.Map map){
        this.map=map;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int x=e.getX();
        int y=e.getY();

        if (map.isZoom()){
            int realX=map.convertToGeoX(x);
            int realY=map.convertToGeoY(y);

            int newLowX= 0;
            int newHighX=0;
            int newLowY=0;
            int newHighY=0;
            if (realX<-120){
                newLowX=-180;
                newHighX=-60;
            }
            else if(realX>120){
                newLowX=60;
                newHighX=180;
            }
            else{
                newLowX=realX-60;
                newHighX=realX+60;
            }
            if (realY<-60){
                newLowY=-90;
                newHighY=-30;
            }
            else if(realY>60){
                newLowY=30;
                newHighY=90;
            }
            else{
                newLowY=realY-30;
                newHighY=realY+30;
            }
            AirportSquare.setAIRPORT_DIMENSION();
            FlightCircle.setFLIGHT_DIMENSION();
            map.setxHigher(newHighX);
            map.setxLower(newLowX);
            map.setyHigher(newHighY);
            map.setyLower(newLowY);
            map.setZoom(false);
            return;
        }

        ArrayList<AirportSquare> airportSquares=map.getAirportSquares();
        for (AirportSquare airportSquare : airportSquares){
            if (airportSquare.contains(x,y)){
                processAirportSelection(airportSquare);
                return;
            }
        }
    }

    public void processAirportSelection(AirportSquare airportSquare){
        AirportSquare selected=map.getSelected();
        if (selected!=null){
            selected.setColor(Color.GRAY);
        }
        if (airportSquare.equals(selected)){
            map.getMapPage().getOwner().resumerTimer();
            map.clearSelected();
        }
        else{
            map.getMapPage().getOwner().pauseTimer();
            map.setSelected(airportSquare);
        }
    }

    private MapPage.Map map;
}
