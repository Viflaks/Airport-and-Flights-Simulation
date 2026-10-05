package gui;

import gui.dialogs.ExitDialog;
import gui.pages.AirportPage;
import gui.pages.FlightPage;
import gui.pages.MapPage;
import gui.tables.AirportTable;
import gui.tables.FlightTable;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static int time=60;

    //Singleton
    private static MainFrame instance;

    public static MainFrame getInstance(){
        if (instance==null) instance =new MainFrame();
        return instance;
    }
    //Singleton

    //MainFrame
    private MainFrame(){
        super("Simulation");
        setSize(1600,1000);
        addWindowListener(new MainAdapter());

        jTabbedPane=new JTabbedPane();
        createPages();
        add(jTabbedPane);

        remainingTime=time;
        timer=new Timer(1000,e->{
            remainingTime--;
            if (remainingTime==5){
                exitDialog=new ExitDialog(this);
            }
            else if(remainingTime<5){
                exitDialog.updateLabel(remainingTime);
            }
        });
        timer.start();

        setVisible(true);
    }

    private void createPages(){
        mapPage=new MapPage(this);

        //Creates Tables
        airportTable=new AirportTable(mapPage);
        flightTable=new FlightTable();

        airportPage=new AirportPage(this,airportTable);
        flightPage=new FlightPage(this,flightTable);

        jTabbedPane.add(airportPage,"Airports");
        jTabbedPane.add(flightPage,"Flights");
        jTabbedPane.add(mapPage,"Map");
    }

    public MapPage getMapPage(){ return mapPage; }

    public AirportTable getAirportTable(){
        return airportTable;
    }

    public FlightTable getFlightTable(){
        return flightTable;
    }

    public void resetTime(){
        remainingTime=time;
        if (exitDialog!=null) exitDialog.dispose();
    }

    public void pauseTimer(){
        resetTime();
        timer.stop();
    }

    public void resumerTimer(){
        timer.start();
    }

    private JTabbedPane jTabbedPane;
    private AirportPage airportPage;
    private FlightPage flightPage;
    private MapPage mapPage;

    private int remainingTime;
    private ExitDialog exitDialog;
    private Timer timer;

    private AirportTable airportTable;
    private FlightTable flightTable;
}
