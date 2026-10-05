package gui.listeners;

import exceptions.BadFileFormat;
import exceptions.FileDoesntExist;
import readers.CSVReader;
import readers.FileLoader;
import readers.JSONReader;
import gui.dialogs.ErrorDialog;
import gui.dialogs.SuccessDialog;
import gui.pages.Page;
import gui.tables.AirportTable;
import gui.tables.FlightTable;
import model.Airport;
import model.Flight;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LoadFunctionListener implements ActionListener {

    public LoadFunctionListener(Page page){
        this.owner=page;
        this.airportTable= owner.getOwner().getAirportTable();
        this.flightTable=owner.getOwner().getFlightTable();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            owner.getOwner().resetTime();
            String pathString= owner.getPathTextFieldInfo();
            Path path= Paths.get(pathString);
            if (Files.notExists(path)) throw new FileDoesntExist();
            if (!pathString.endsWith(".json") && !pathString.endsWith(".csv")) throw new BadFileFormat();
            FileLoader fileLoader;
            if (pathString.endsWith(".json")) fileLoader=new JSONReader(path);
            else fileLoader=new CSVReader(path);
            fileLoader.loadFile();
            String message=fileLoader.getErrorMessage();
            if (!message.isEmpty()) throw new Exception(message);
            airportTable.clearTable();
            flightTable.clearTable();
            owner.getOwner().getMapPage().clearMap();
            ArrayList<Airport> airportList=fileLoader.getAirportList();
            ArrayList<Flight.Template> flightList=fileLoader.getFlightList();
            for (Airport airport : airportList) {
                airportTable.addAirport(airport);
            }
            for (Flight.Template template : flightList){
                flightTable.addFlight(new Flight(template,airportTable));
            }
            new SuccessDialog();
        }
        catch (Exception exception){
            new ErrorDialog(exception.getMessage());
        }
    }

    private Page owner;
    private AirportTable airportTable;
    private FlightTable flightTable;
}
