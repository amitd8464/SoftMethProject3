package clinic;

import util.List;

/**
 * Class for provider that extends Person
 * @author Andrew Ho, Amit Deshpande
 */
public abstract class Provider extends Person {

    /**
     * location - office location of practice
     * */
    private Location location;

    /**
     * rate() - abstract method that returns the provider’s charging rate per visit for seeing patients
     * @return the rate
     * */
    public abstract int rate();
    public abstract String calculateCredits(List<Appointment> appointments);

    /**
     * Constructor for Provider
     * @param profile - profile for the provider
     * @param location - location for the provider
     */
    public Provider(Profile profile, Location location) {
        super(profile);
        this.location = location;
    }

    /**
     * Getter method for the location
     * @return 0 returns the location
     */
    public Location getLocation(){
        return this.location;
    }
}
