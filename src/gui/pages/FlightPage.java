package gui.pages;

import gui.MainFrame;
import gui.dialogs.FlightDialog;
import gui.dialogs.SuccessDialog;
import gui.tables.FlightTable;

import java.awt.*;
import java.util.Arrays;

public class FlightPage extends Page{

    public FlightPage(MainFrame owner,FlightTable flightTable){
        super(owner,Color.yellow,"Flights","Add Flight","Remove Flights",flightTable);
        addButton.addActionListener(e -> {
            owner.resetTime();
            new FlightDialog(owner);
        });
        removeButton.addActionListener(e ->{
            owner.resetTime();
            deleteFlights();
        });
    }

    public void deleteFlights(){
        int[] selectedAirport=table.getSelectedRows();
        if (selectedAirport.length==0) return;
        FlightTable flightTable=getOwner().getFlightTable();
        int[] model=new int[selectedAirport.length];
        for (int i=0;i<selectedAirport.length;i++){
            model[i]=table.convertRowIndexToModel(selectedAirport[i]);
        }
        Arrays.sort(model);
        for (int i=model.length-1;i>=0;i--){
            flightTable.deleteFlight(model[i]);
        }
        new SuccessDialog();
    }
}