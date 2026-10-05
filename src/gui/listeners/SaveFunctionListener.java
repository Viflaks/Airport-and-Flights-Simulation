package gui.listeners;

import exceptions.BadFileFormat;
import exceptions.FileDoesntExist;
import gui.dialogs.ErrorDialog;
import gui.dialogs.SuccessDialog;
import gui.pages.Page;
import model.Airport;
import model.Flight;
import writers.CSVWriter;
import writers.FileSaver;
import writers.JSONWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveFunctionListener implements ActionListener {

    public SaveFunctionListener(Page owner){
        this.owner=owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            owner.getOwner().resetTime();
            String pathString= owner.getPathTextFieldInfo();
            Path path= Paths.get(pathString);
            if (Files.notExists(path)) throw new FileDoesntExist();
            if (!pathString.endsWith(".json") && !pathString.endsWith(".csv")) throw new BadFileFormat();
            ArrayList<Airport> airportList=owner.getOwner().getAirportTable().getAirportList();
            ArrayList<Flight> flightList=owner.getOwner().getFlightTable().getFlightList();
            ArrayList<Flight.Template> flightTemplates=convertToTemplate(flightList);
            FileSaver fileSaver;
            if (pathString.endsWith(".json")) fileSaver=new JSONWriter(path,airportList,flightTemplates);
            else fileSaver=new CSVWriter(path,airportList,flightTemplates);
            fileSaver.saveFile();
            new SuccessDialog();
        } catch (Exception exception) {
            new ErrorDialog(exception.getMessage());
        }
    }

    public ArrayList<Flight.Template> convertToTemplate(ArrayList<Flight> flightList){
        ArrayList<Flight.Template> flightTemplates=new ArrayList<>();
        for (Flight flight : flightList) flightTemplates.add(new Flight.Template(flight));
        return flightTemplates;
    }

    private Page owner;
}
