package gui.dialogs;

import gui.MainFrame;
import gui.tables.AirportTable;
import gui.tables.FlightTable;
import model.Airport;
import model.Flight;
import validators.ParameterValidator;

public class FlightDialog extends InsertDialog {

    public FlightDialog(MainFrame owner) {
        super(owner, "Insert Flight", new String[]{"FROM", "TO", "DEPARTURE", "DURATION"});
        this.airportTable = owner.getAirportTable();
        this.flightTable = owner.getFlightTable();
        update.addActionListener(e -> {
            try {
                owner.resetTime();
                String message = proccesInputs();
                if (!message.isEmpty()) throw new Exception(message);
                Airport from = airportTable.getAirport(getTextField(0));
                Airport to = airportTable.getAirport(getTextField(1));
                String departure = getTextField(2);
                int duration = Integer.parseInt(getTextField(3));
                flightTable.addFlight(new Flight(from, to, departure, duration));
                new SuccessDialog();
            } catch (Exception exception) {
                new ErrorDialog(exception.getMessage());
            }
        });
    }

    public String proccesInputs() {
        StringBuilder stringBuilder = new StringBuilder();
        String from=getTextField(0);
        String to=getTextField(1);
        String departure=getTextField(2);
        String duration=getTextField(3);
        if (!airportTable.existsAirport(from))
            stringBuilder.append("Airport with given fromCode does not exists\n");
        if (!airportTable.existsAirport(to))
            stringBuilder.append("Airport with given toCode does not exists\n");
        if (from.equals(to))
            stringBuilder.append("Airports cant be the same\n");
        if (!ParameterValidator.timeParameterValid(departure))
            stringBuilder.append("Invalid departureTime input\n");
        if (!ParameterValidator.isNumber(duration))
            stringBuilder.append("Invalid durationTime input\n");
        else if (!ParameterValidator.isNumberPositive(Integer.parseInt(duration)))
            stringBuilder.append("durationTime must be a positive number\n");
        return stringBuilder.toString();
    }

    private AirportTable airportTable;
    private FlightTable flightTable;
}
