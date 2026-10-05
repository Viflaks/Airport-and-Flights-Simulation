package gui.tables;
import model.Airport;
import model.Flight;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class FlightTable extends AbstractTableModel {

    public FlightTable(){
        super();
        flightList=new ArrayList<>();
        airportAmount=new HashMap<>();
        columns=new String[]{"FROM","TO","DEPARTURE","DURATION"};
    }

    public void addFlight(Flight flight){
        flightList.add(flight);
        Airport from=flight.getFrom();
        Airport to=flight.getTo();
        airportAmount.merge(from,1,Integer::sum);
        airportAmount.merge(to,1,Integer::sum);

        int lastrow=flightList.size()-1;
        fireTableRowsInserted(lastrow,lastrow);
    }
    public void deleteFlight(int index){
        Flight flight=flightList.get(index);
        Airport from=flight.getFrom();
        Airport to=flight.getTo();
        airportAmount.computeIfPresent(from, (k, v) -> v > 1 ? v - 1 : null);
        airportAmount.computeIfPresent(to, (k, v) -> v > 1 ? v - 1 : null);

        flightList.remove(index);
        fireTableRowsDeleted(index,index);
    }

    public void clearTable(){
        flightList.clear();
    }

    public boolean containsAirport(Airport airport){
        return airportAmount.containsKey(airport);
    }

    @Override
    public int getRowCount() {
        return flightList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flight f=flightList.get(rowIndex);
        switch (columnIndex){
            case 0: return f.getFrom();
            case 1: return f.getTo();
            case 2: return f.getDeparture();
            case 3: return f.getDuration();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public ArrayList<Flight> getFlightList(){
        return flightList;
    }

    private final ArrayList<Flight> flightList;
    private final HashMap<Airport, Integer> airportAmount;
    private final String[] columns;
}
