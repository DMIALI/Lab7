package Utils;

/**
 * Class that convert variable
 */
public class PrimitiveTypeAsserter {
    public static boolean checkInteger(String primitive){
        try{
            Integer c = Integer.valueOf(primitive);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    public static boolean checkLong(String primitive){
        try{
            Long c = Long.valueOf(primitive);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean checkDouble(String primitive){
        try{
            Double c = Double.valueOf(primitive);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean checkFloat(String primitive){
        try{
            Float c = Float.valueOf(primitive);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}