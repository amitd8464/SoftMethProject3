package clinic;

/**
 * Error class for WeekendDateException
 * @author Andrew Ho, Amit Deshpande
 */
public class WeekendDateException extends Exception{
    /**
     * Constructor for the error WeekendDateException
     * @param message - message to be passed with the error
     */
    public WeekendDateException(String message){
        super(message);
    }
}
