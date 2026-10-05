package model;

import java.util.ArrayDeque;

public class Airport {
    public static class Record{
        public String[] getParameters(){
            return new String[]{code,name,x,y};
        }
        private String code;
        private String name;
        private String x;
        private String y;

    }

    public Airport(String code, String name, double x, double y) {
        this.code = code;
        this.name = name;
        this.x = x;
        this.y = y;
        this.occupied=0;
        this.flightQueue=new ArrayDeque<>();
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getCode(){
        return this.code;
    }

    @Override
    public String toString() {
        return code;
    }

    public String getVisual(){
        return code+", "+name+", X:"+x+", Y:"+y;
    }

    public void occupyAirport(){
        occupied=10;
    }

    public void tick(){
        if (occupied>0) occupied--;
    }

    public void addFlightToQueue(Flight flight){
        flightQueue.add(flight);
    }

    public boolean isQueueEmpty(){
        return flightQueue.isEmpty();
    }

    public Flight getFlightFromQueue(){
        return flightQueue.remove();
    }

    public boolean isFree(){
        return occupied==0;
    }

    public void freeAirport(){
        occupied=0;
    }

    private String code;
    private String name;
    private double x;
    private double y;
    private int occupied;
    private ArrayDeque<Flight> flightQueue;

}
