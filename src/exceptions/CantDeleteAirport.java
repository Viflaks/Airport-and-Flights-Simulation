package exceptions;

public class CantDeleteAirport extends RuntimeException {
    public CantDeleteAirport(String airport){
        super("Cant delete airport with code: "+airport+"\nfirst delete all flights that take of from that airport/land onto that airport");
    }
}
