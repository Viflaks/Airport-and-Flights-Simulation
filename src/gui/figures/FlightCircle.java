package gui.figures;

import gui.pages.MapPage;
import model.Flight;

import javax.swing.*;
import java.awt.*;

public class FlightCircle implements Drawable{

    public static int FLIGHT_DIMENSION=16;

    public static void setFLIGHT_DIMENSION(){
        FLIGHT_DIMENSION = 48;
    }

    public static void resetFLIGHT_DIMENSION(){
        FLIGHT_DIMENSION = 16;
    }

    public FlightCircle(MapPage.Map owner,Flight flight){
        this.xCurr=flight.getFrom().getX();
        this.yCurr=flight.getFrom().getY();

        this.xFinish=flight.getTo().getX();
        this.yFinish=flight.getTo().getY();

        this.xSpeed=(xFinish-xCurr)/flight.getDuration();
        this.ySpeed=(yFinish-yCurr)/flight.getDuration();

        this.finished=flight.getDuration();
        this.currTime=0;

        this.map=owner;
    }

    @Override
    public void draw(Graphics g) {
        int realX=map.convertToRealX(xCurr);
        int realY=map.convertToRealY(yCurr);

        g.setColor(Color.BLUE);
        g.fillOval(realX-FLIGHT_DIMENSION/2,realY-FLIGHT_DIMENSION/2,FLIGHT_DIMENSION,FLIGHT_DIMENSION);
    }

    public boolean reachedFinish(){
        return currTime==finished;
    }

    public void tick(){
        xCurr+=xSpeed;
        yCurr+=ySpeed;
        currTime++;
    }

    private double xCurr;
    private double yCurr;

    private double xSpeed;
    private double ySpeed;

    private double xFinish;
    private double yFinish;

    private int finished;
    private int currTime;

    private MapPage.Map map;

}
