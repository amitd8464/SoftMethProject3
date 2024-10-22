package clinic;

/**
 * Error class for DatePastSixMonthsException
 * @author Andrew Ho, Amit Deshpande
 */
public class DatePastSixMonthsException extends Exception {
    /**
     * Constructor for error DatePastSixMonthsException
     * @param message - message to be thrown
     */
    public DatePastSixMonthsException(String message){
        super(message);
    }
}
