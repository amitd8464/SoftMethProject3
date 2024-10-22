package clinic;

/**
 * Error classs for PatientUnavailableException
 * @author Andrew Ho,Amit Deshpande
 */
public class PatientUnavailableException extends Exception{
    /**
     * Constructor for PatientUnavailableException
     * @param message - error message to be passed on
     */
    public PatientUnavailableException(String message){
        super(message);
    }
}
