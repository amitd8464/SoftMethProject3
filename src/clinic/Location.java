package clinic;

/**
 * Stores constants of office locations of providers
 * @author Amit Deshpande
 */
public enum Location {

    /**
     * BRIDGEWATER value for county and zip for the clinics
     */
    BRIDGEWATER("Somerset", "08807"),

    /**
     * EDISON value for county and zip for the clinics
     */
    EDISON("Middlesex", "08817"),

    /**
     * PISCATAWAY value for county and zip for the clinics
     */
    PISCATAWAY("Middlesex", "08854"),

    /**
     * PRINCETON value for county and zip for the clinics
     */
    PRINCETON("Mercer", "08542"),

    /**
     * MORRISTOWN value for county and zip for the clinics
     */
    MORRISTOWN("Morris", "07960"),

    /**
     * CLARK value for county and zip for the clinics
     */
    CLARK("Union", "07066");

    /**
     * country - String for the county
     */
    private final String county;
    /**
     * zip - String for the zip
     */
    private final String zip;

    /**
     * Constructor for Location
     * @param county - county of provider
     * @param zip - zipcode of the provider
     */
    Location(String county, String zip){
        this.county = county;
        this.zip = zip;
    }

    /**
     * Getter method for this.county
     * @return county - county of the provider
     */
    public String getCounty(){
        return county;
    }

    /**
     * Getter method for this.zip
     * @return zip - zipcode of the provider
     */
    public String getZip(){
        return zip;
    }

    /**
     * toString getter
     * @return - returns the location as a string
     */
    public String toString(){
        return this.name() + ", " + this.county + " " + this.zip;
    }
}
