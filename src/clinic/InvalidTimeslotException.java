package clinic;

/**
 * Error class for InvalidTimeslotException
 * @author Andrew Ho,Amit Deshpande
 */
public class InvalidTimeslotException extends Exception{
    /**
     * Constructor for InvalidTimeslotException
     * @param message - error message to be passed on
     */
    public InvalidTimeslotException(String message){
        super(message);
    }
}
