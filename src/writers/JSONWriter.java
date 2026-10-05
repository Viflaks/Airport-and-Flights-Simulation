package writers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.Airport;
import model.Flight;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class JSONWriter extends FileSaver{

    public JSONWriter(Path path, ArrayList<Airport> airportList, ArrayList<Flight.Template> flightList) throws IOException {
        super(path, airportList, flightList);
        this.gson=new GsonBuilder().setPrettyPrinting().create();
        this.root = new JsonObject();
    }

    @Override
    public void saveFile() throws IOException  {
        try{
            root.add("airports",gson.toJsonTree(airportList));
            root.add("flights",gson.toJsonTree(flightList));
            gson.toJson(root, bufferedWriter);
            bufferedWriter.flush();
        }
        finally {
            bufferedWriter.close();
        }
    }

    private Gson gson;
    private JsonObject root;
}
