package clinic;

/**
 * Class for person implementing Comparable
 * @author Andrew Ho, Amit Deshpande
 */
public class Person implements Comparable<Person> {

    /** instance of profile for each person*/
    private Profile profile;

    /**
     * Constructor for person
     * @param profile - profile of the person
     */
    public Person(Profile profile){
        this.profile = profile;
    }

    /**
     * Getter for profile
     * @return returns instance of profile
     */
    public Profile getProfile(){
        return this.profile;
    }

    /**
     * compareTo function for profile
     * @param o the object to be compared.
     * @return - returns less than 0 if less, 0 if equal, greater than 0 if greater
     */
    @Override
    public int compareTo(Person o) {
        return this.getProfile().compareTo(o.getProfile());
    }
}