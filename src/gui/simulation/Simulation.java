package gui.simulation;

import gui.MainFrame;
import gui.figures.FlightCircle;
import gui.pages.MapPage;
import gui.tables.FlightTable;
import model.Airport;
import model.Flight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Iterator;

public class Simulation {

    public Simulation(MainFrame owner,MapPage mapPage){

        this.flightPQ=new PriorityQueue<>(new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                return Integer.compare(o1.getDepartureInt(),o2.getDepartureInt());
            }
        });
        this.occupiedAirports=new ArrayList<>();

        this.owner=owner;
        this.map=mapPage.getMap();
        this.mapPage=mapPage;

        this.timer=new Timer(100,e -> {simulationTick();});
    }

    public void startSimulation(){
        if (started) return;
        mapPage.setSimulationStatus("Simulating");
        paused=false;
        finished=false;
        started=true;
        simulationTime=0;
        owner.pauseTimer();

        flightPQ.addAll(owner.getFlightTable().getFlightList());

        timer.start();
    }

    public void simulationTick(){
        mapPage.setSimulationTime(simulationTime);
        map.tickFlightCircles();

        Iterator<Airport> iterator=occupiedAirports.iterator();

        while (iterator.hasNext()){
            Airport airport=iterator.next();
            airport.tick();
            if (airport.isFree()){
                if (!airport.isQueueEmpty()){
                    Flight flight=airport.getFlightFromQueue();
                    FlightCircle flightCircle=new FlightCircle(map,flight);
                    map.addFlightCircle(flightCircle);
                    airport.occupyAirport();
                }
                else iterator.remove();
            }
        }

        while(!flightPQ.isEmpty() && simulationTime==flightPQ.peek().getDepartureInt()){
            Flight flight=flightPQ.remove();
            Airport from=flight.getFrom();
            if (from.isFree()){
                FlightCircle flightCircle=new FlightCircle(map,flight);
                map.addFlightCircle(flightCircle);
                from.occupyAirport();
                occupiedAirports.add(from);
            }
            else{
                from.addFlightToQueue(flight);
            }
        }

        simulationTime++;
    }

    public void stopSimulation(){
        if (!started || paused) return;
        mapPage.setSimulationStatus("Paused");
        paused=true;
        owner.pauseTimer();
        timer.stop();
    }

    public void resumeSimulation(){
        if (!started || !paused) return;
        mapPage.setSimulationStatus("Simulating");
        paused=false;
        owner.resumerTimer();
        timer.start();
    }

    public void resetSimulation(){
        if (!started) return;
        owner.resumerTimer();
        started=false;
        paused=true;
        finished=true;
        mapPage.setSimulationTime(0);
        mapPage.setSimulationStatus("Finished");

        for (Airport airport : occupiedAirports)
            airport.freeAirport();

        timer.stop();

        flightPQ.clear();
        occupiedAirports.clear();
        map.clearFlightCircles();
    }

    private PriorityQueue<Flight> flightPQ;
    private ArrayList<Airport> occupiedAirports;

    private MapPage.Map map;
    private MainFrame owner;
    private MapPage mapPage;

    private Timer timer;
    private int simulationTime;

    private boolean started;
    private boolean finished;
    private boolean paused;
}
