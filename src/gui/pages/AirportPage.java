package gui.pages;

import exceptions.CantDeleteAirport;
import gui.MainFrame;
import gui.dialogs.AirportDialog;
import gui.dialogs.ErrorDialog;
import gui.dialogs.SuccessDialog;
import gui.tables.AirportTable;
import gui.tables.FlightTable;
import model.Airport;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class AirportPage extends Page {

    public AirportPage(MainFrame owner,AirportTable airportTable){
        super(owner,Color.cyan,"Airports","Add Airport","Remove Airports",airportTable);
        addButton.addActionListener(e -> {
            owner.resetTime();
            new AirportDialog(owner);
        });
        removeButton.addActionListener(e->{
            owner.resetTime();
            deleteAirports();
        });
    }

    public void deleteAirports(){
        try{
            int[] selectedAirport=table.getSelectedRows();
            if (selectedAirport.length==0) return;
            AirportTable airportTable=getOwner().getAirportTable();
            FlightTable flightTable=getOwner().getFlightTable();
            int[] model=new int[selectedAirport.length];
            for (int i=0;i<selectedAirport.length;i++){
                model[i]=table.convertRowIndexToModel(selectedAirport[i]);
                Airport airport=airportTable.getAirport(model[i]);
                if (flightTable.containsAirport(airport)) throw new CantDeleteAirport(airport.getCode());
            }
            Arrays.sort(model);
            for (int i=model.length-1;i>=0;i--){
                airportTable.removeAirport(model[i]);
            }
            new SuccessDialog();
        }
        catch (CantDeleteAirport e){
            new ErrorDialog(e.getMessage());
        }
    }
}
