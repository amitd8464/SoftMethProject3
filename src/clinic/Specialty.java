package clinic;

/**
 * Stores constants representing specialities of Providers
 * @author Amit Deshpande
 */
public enum Specialty {


    /**
     * Family value for the cost of the appointment
     */
    FAMILY(250),

    /**
     * Pediatrician value for the cost of the appointment
     */
    PEDIATRICIAN(300),

    /**
     * Allergist value for the cost of the appointment
     */
    ALLERGIST(350);

    /**
     * charge int to hold the cost charge for the appointment
     */
    private final int charge;

    /**
     * Constructor for Specialty
     * @param charge - charge of visit for given specialty
     */
    Specialty(int charge){
        this.charge = charge;
    }

    /**
     * Getter method for this.charge
     * @return charge - charge of visit for given specialty
     */
    public int getCharge(){
        return charge;
    }
}
