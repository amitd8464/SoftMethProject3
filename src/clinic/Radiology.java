package clinic;

/**
 * Enum for Radiology
 * @author Amit Deshpande, Andrew Ho
 */
public enum Radiology {
    /** Catscan*/
    CATSCAN,
    /** ultrasound*/
    ULTRASOUND,
    /** xray*/
    XRAY;

    /**
     * Getter for the enum as a string
     * @return - string of the enum
     */
    public String toString(){ return this.name(); }
}
