package gui.dialogs;

import gui.MainFrame;
import gui.tables.AirportTable;
import model.Airport;
import validators.ParameterValidator;

public class AirportDialog extends InsertDialog{

    public AirportDialog(MainFrame owner){
        super(owner,"Insert Airport",new String[]{"CODE","NAME","X","Y"});
        this.airportTable=owner.getAirportTable();
        update.addActionListener(e-> {
            try {
                owner.resetTime();
                String message= proccessInputs();
                if (!message.isEmpty()) throw new Exception(message);
                String code=getTextField(0);
                String name=getTextField(1);
                double x=Double.parseDouble(getTextField(2));
                double y=Double.parseDouble(getTextField(3));
                Airport airport = new Airport(code,name,x,y);
                airportTable.addAirport(airport);
                new SuccessDialog();
            } catch (Exception exception) {
                new ErrorDialog(exception.getMessage());
            }
        });
    }

    public String proccessInputs(){
        StringBuilder stringBuilder = new StringBuilder();
        String code = getTextField(0);
        if (!ParameterValidator.isCodeValid(code)) stringBuilder.append("Invalid code input\n");
        if (airportTable.existsAirport(code)) stringBuilder.append("Airport with given code already exists\n");
        if (!ParameterValidator.isNumber(getTextField(2)) || !ParameterValidator.isNumber(getTextField(3))) {
            stringBuilder.append("Bad coordinates input\n");
            return stringBuilder.toString();
        }
        int x = Integer.parseInt(getTextField(2));
        int y = Integer.parseInt(getTextField(3));
        if (!ParameterValidator.isXValid(x)) stringBuilder.append("X coordinate must be in range [-180,180]\n");
        if (!ParameterValidator.isYValid(y)) stringBuilder.append("Y coordinate must be in range [-90,90]\n");
        return stringBuilder.toString();
    }

    private AirportTable airportTable;
}
