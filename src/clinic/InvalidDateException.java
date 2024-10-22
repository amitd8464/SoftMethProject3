package clinic;

/**
 * Error class for InvalidDateException
 * @author Andrew Ho,Amit Deshpande
 */
public class InvalidDateException extends Exception{
    /**
     * Constructor for InvalidDateException
     */
    public InvalidDateException(){ // just default prob not used
        super("Appointment date: is not a valid calendar date");
    }

    /**
     * Constructor for InvalidDateException
     * @param message - message to be passed on
     */
    public InvalidDateException(String message){
        super(message);
    }
}
