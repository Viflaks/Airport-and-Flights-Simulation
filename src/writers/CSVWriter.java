package writers;

import model.Airport;
import model.Flight;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class CSVWriter extends FileSaver{

    public CSVWriter(Path path, ArrayList<Airport> airportList, ArrayList<Flight.Template> flightList) throws IOException {
        super(path, airportList, flightList);
    }

    @Override
    public void saveFile() throws IOException {
        try{
            bufferedWriter.write("CODE,NAME,X,Y");
            bufferedWriter.newLine();
            for (Airport airport : airportList){
                bufferedWriter.write(airport.getCode());
                bufferedWriter.write(',');
                bufferedWriter.write(airport.getName());
                bufferedWriter.write(',');
                bufferedWriter.write(String.valueOf(airport.getX()));
                bufferedWriter.write(',');
                bufferedWriter.write(String.valueOf(airport.getY()));
                bufferedWriter.newLine();
            }
            bufferedWriter.write("FROM,TO,DEPARTURE,DURATION");
            bufferedWriter.newLine();
            for (Flight.Template flight: flightList){
                bufferedWriter.write(flight.getFrom());
                bufferedWriter.write(',');
                bufferedWriter.write(flight.getTo());
                bufferedWriter.write(',');
                bufferedWriter.write(flight.getDeparture());
                bufferedWriter.write(',');
                bufferedWriter.write(String.valueOf(flight.getDuration()));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        }
        finally {
            bufferedWriter.close();
        }
    }
}
