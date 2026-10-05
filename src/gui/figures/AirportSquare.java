package gui.figures;

import gui.pages.MapPage;
import model.Airport;

import java.awt.*;

public class AirportSquare implements Drawable {

    public static int AIRPORT_DIMENSION =16;

    public static void setAIRPORT_DIMENSION(){
        AIRPORT_DIMENSION = 48;
    }

    public static void resetAIRPORT_DIMENSION(){
        AIRPORT_DIMENSION = 16;
    }

    public AirportSquare(MapPage.Map owner, Airport airport){
        this.color=Color.GRAY;
        this.font=new Font("Arial", Font.BOLD, 15);
        this.airport=airport;
        this.visible=false;
        this.map=owner;
    }

    public void draw(Graphics g){
        int realX=map.convertToRealX(airport.getX());
        int realY=map.convertToRealY(airport.getY());

        g.setColor(color);
        g.fillRect(realX- AIRPORT_DIMENSION /2,realY- AIRPORT_DIMENSION /2, AIRPORT_DIMENSION, AIRPORT_DIMENSION);

        g.setColor(Color.black);
        g.setFont(font);
        g.drawString(airport.getCode(),realX+ AIRPORT_DIMENSION /2,realY+ AIRPORT_DIMENSION /2-2);
    }

    public boolean contains(int x, int y) {
        int realX = map.convertToRealX(airport.getX());
        int realY = map.convertToRealY(airport.getY());

        int left   = realX - AIRPORT_DIMENSION / 2;
        int right  = realX + AIRPORT_DIMENSION / 2;
        int top    = realY - AIRPORT_DIMENSION / 2;
        int bottom = realY + AIRPORT_DIMENSION / 2;

        return x >= left && x <= right && y >= top && y <= bottom;
    }

    public void swapColor(){
        if (color.equals(Color.GRAY)) color=Color.RED;
        else color=Color.GRAY;
    }

    public void setColor(Color color){
        this.color=color;
    }

    public void setVisible(boolean visible) {
        this.visible=visible;
    }

    public boolean isVisible(){
        return visible;
    }

    public Airport getAirport(){
        return airport;
    }

    private Color color;
    private Airport airport;
    private boolean visible;
    private Font font;
    private MapPage.Map map;
}
