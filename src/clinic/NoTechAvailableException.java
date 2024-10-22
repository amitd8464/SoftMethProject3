package clinic;

/**
 * Error class for NoTechAvailableException
 * @author Andrew Ho,Amit Deshpande
 */
public class NoTechAvailableException extends Exception{
    /**
     * Constructor for NoTechAvailableException
     * @param message - error message to be passed on
     */
    public NoTechAvailableException(String message){
        super(message);
    }
}
