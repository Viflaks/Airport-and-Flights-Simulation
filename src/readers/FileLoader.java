package readers;

import model.Airport;
import model.Flight;
import validators.ParameterValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

abstract public class FileLoader {

    public FileLoader(Path path) throws IOException {
        this.bufferedReader= Files.newBufferedReader(path);
        this.stringBuilder=new StringBuilder();
        this.indexLine=0;

        this.codeMap=new HashSet<>();
        this.airportList=new ArrayList<>();
        this.flightList=new ArrayList<>();
    }

    abstract public void loadFile() throws IOException;

    public void loadFlightRow(String[] parameters){
        indexLine++;
        if (!ParameterValidator.parametersValid(parameters)){
            expandError("Bad number of parameters");
            return;
        }
        if (!codeMap.contains(parameters[0])){
            expandError("Airport with startCode does not exists");
        }
        if (!codeMap.contains(parameters[1])){
            expandError("Airport with finishCode does not exists");
        }

        if (parameters[0].equals(parameters[1])){
            expandError("Airport codes cannot be the same");
        }

        if (!ParameterValidator.timeParameterValid(parameters[2])){
            expandError("Invalid time parameter");
        }
        if (!ParameterValidator.isNumber(parameters[3])){
            expandError("Invalid durationTime parameter");
            return;
        }
        int duration=Integer.parseInt(parameters[3]);
        if (!ParameterValidator.isNumberPositive(duration)){
            expandError("durationTime parameter must be positive");
        }
        flightList.add(new Flight.Template(parameters[0],parameters[1],parameters[2],duration));
    }

    public void loadAirportRow(String[] parameters){
        indexLine++;
        if (!ParameterValidator.parametersValid(parameters)){
            expandError("Bad number of parameters");
            return;
        }
        if (!ParameterValidator.isCodeValid(parameters[0])){
            expandError("Invalid airport code");
        }
        if (codeMap.contains(parameters[0])){
            expandError("Airport with that code already exists");
        }
        if (!ParameterValidator.isNumber(parameters[2]) || !ParameterValidator.isNumber(parameters[3])){
            expandError("Invalid coordinate input");
            return;
        }
        double x=Double.parseDouble(parameters[2]);
        double y=Double.parseDouble(parameters[3]);
        if (!ParameterValidator.isXValid(x)){
            expandError("X coordinate must be in range [-180,180]");
        }
        if (!ParameterValidator.isYValid(y)){
            expandError("Y coordinate must be in range [-90,90]");
        }
        codeMap.add(parameters[0]);
        airportList.add(new Airport(parameters[0],parameters[1],x,y));
    }

    public void expandError(String message){
        stringBuilder.append("Error on line: ");
        stringBuilder.append(indexLine);
        stringBuilder.append(", ");
        stringBuilder.append(message);
        stringBuilder.append('\n');
    }

    public String getErrorMessage(){
        return stringBuilder.toString();
    }

    public ArrayList<Airport> getAirportList(){
        return airportList;
    }

    public ArrayList<Flight.Template> getFlightList(){
        return flightList;
    }

    protected BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    protected int indexLine;

    private HashSet<String> codeMap;
    private ArrayList<Airport> airportList;
    private ArrayList<Flight.Template> flightList;
}
