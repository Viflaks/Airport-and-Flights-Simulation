package readers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class CSVReader extends FileLoader{

    public CSVReader(Path path) throws IOException {
        super(path);
    }

    @Override
    public void loadFile() throws IOException {
        if (bufferedReader.readLine()==null) return;
        String line;
        String[] finishLine={"FROM","TO","DEPARTURE","DURATION"};
        while ((line=bufferedReader.readLine())!=null){
            String[] parameters=line.split(",");
            if (Arrays.equals(parameters, finishLine)) break;
            loadAirportRow(parameters);
        }
        indexLine++;
        while ((line=bufferedReader.readLine())!=null){
            String[] parameters=line.split(",");
            loadFlightRow(parameters);
        }
        bufferedReader.close();
    }
}
