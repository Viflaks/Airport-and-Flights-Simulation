package readers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Airport;
import model.Flight;

import java.io.IOException;
import java.nio.file.Path;

public class JSONReader extends FileLoader{
    public JSONReader(Path path) throws IOException {
        super(path);
        this.gson=new Gson();
        this.root = JsonParser.parseReader(bufferedReader).getAsJsonObject();
    }

    @Override
    public void loadFile() throws IOException {
        if (root.has("airports") && !root.get("airports").isJsonNull()) {
            Airport.Record[] airportRecords = gson.fromJson(root.get("airports"), Airport.Record[].class);
            if (airportRecords != null) {
                indexLine=1;
                for (Airport.Record airportRecord : airportRecords) loadAirportRow(airportRecord.getParameters());
            }
        }

        if (root.has("flights") && !root.get("flights").isJsonNull()) {
            Flight.Record[] flightRecords = gson.fromJson(root.get("flights"), Flight.Record[].class);
            if (flightRecords != null) {
                indexLine+=2;
                for (Flight.Record flightRecord : flightRecords) loadFlightRow(flightRecord.getParameters());
            }
        }
        bufferedReader.close();
    }
    private Gson gson;
    private JsonObject root;
}
