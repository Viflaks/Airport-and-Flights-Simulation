package writers;

import model.Airport;
import model.Flight;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

abstract public class FileSaver {
    public FileSaver(Path path, ArrayList<Airport> airportList, ArrayList<Flight.Template> flightList) throws IOException {
        this.bufferedWriter= Files.newBufferedWriter(path);
        this.airportList=airportList;
        this.flightList=flightList;
    }

    abstract public void saveFile() throws IOException;

    protected BufferedWriter bufferedWriter;
    protected ArrayList<Airport> airportList;
    protected ArrayList<Flight.Template> flightList;
}
