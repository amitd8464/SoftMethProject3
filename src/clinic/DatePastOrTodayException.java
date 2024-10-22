package clinic;

/**
 * Error class
 * @author Andrew Ho, Amit Deshpande
 */
public class DatePastOrTodayException extends Exception{
    /**
     * Constructor for error
     * @param message- error message
     */
    public DatePastOrTodayException(String message){
        super(message);
    }
}
