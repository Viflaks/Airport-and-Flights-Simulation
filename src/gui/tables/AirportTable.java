package gui.tables;

import gui.pages.MapPage;
import model.Airport;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class AirportTable extends AbstractTableModel {

    public AirportTable(MapPage mapPage){
        super();
        this.mapPage=mapPage;
        airportHashMap=new HashMap<>();
        airportList=new ArrayList<>();
        columns=new String[]{"CODE","NAME","X","Y"};
    }

    public void addAirport(Airport airport){
        airportHashMap.put(airport.getCode(), airport);
        airportList.add(airport);
        mapPage.addAirport(airport);

        int lastrow=airportList.size()-1;
        fireTableRowsInserted(lastrow,lastrow);
    }

    public void removeAirport(int index){
        Airport airport=airportList.get(index);

        airportHashMap.remove(airport.getCode());
        mapPage.removeAirport(index);
        airportList.remove(index);

        fireTableRowsDeleted(index,index);
    }
    public Airport getAirport(int index){ return airportList.get(index);}

    public Airport getAirport(String code){
        return airportHashMap.get(code);
    }

    public boolean existsAirport(String code){
        return airportHashMap.containsKey(code);
    }

    public void clearTable(){
        airportHashMap.clear();
        airportList.clear();
    }

    @Override
    public int getRowCount() {
        return airportList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    public ArrayList<Airport> getAirportList(){
        return airportList;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Airport a=airportList.get(rowIndex);
        switch (columnIndex){
            case 0: return a.getCode();
            case 1: return a.getName();
            case 2: return a.getX();
            case 3: return a.getY();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    private final HashMap<String, Airport> airportHashMap;
    private final ArrayList<Airport> airportList;
    private final String[] columns;
    private MapPage mapPage;
}
