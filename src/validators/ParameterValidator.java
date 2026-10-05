package validators;

public class ParameterValidator {

    public static boolean isNumber(String number){
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumberPositive(int number){
        return number>0;
    }

    public static boolean isCodeValid(String code){
        if (code.length()!=3) return false;
        return code.equals(code.toUpperCase());
    }

    public static boolean isXValid(double x){
        return x<=180 && x>=-180;
    }

    public static boolean isYValid(double y){
        return y<=90 && y>=-90;
    }

    public static boolean parametersValid(String[] parameters){
        return parameters.length==4;
    }

    public static boolean timeParameterValid(String time){
        if (time.length()==5){
            if (time.charAt(2)!=':') return false;
            return isNumber(time.substring(0,2)) && isNumber(time.substring(3,5));
        }
        else if (time.length()==4){
            if (time.charAt(1)!=':') return false;
            return isNumber(time.substring(0,1)) && isNumber(time.substring(2,4));
        }
        return false;
    }
}
