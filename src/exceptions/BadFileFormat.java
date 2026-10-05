package exceptions;

public class BadFileFormat extends Exception {
    public BadFileFormat(){
        super("Bad file format, file format must be CSV or JSON");
    }
}
