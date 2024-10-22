package clinic;

import util.List;

import java.text.DecimalFormat;

/**
 * Doctor class
 * @author Andrew Ho, Amit Deshpande
 */
public class Doctor extends Provider{
    /**
     * specialty - encapsulate the rate per visit based on specialty
     * */
    private Specialty specialty;
    /**
     * npi - National Provider Identification unique to the doctor
     * */
    private String npi;

    /**
     * Constructor for Doctor
     * @param profile - doctor's profile
     * @param location - location of doctor
     * @param specialtyName - doctors specialization
     * @param npi - national provider identification
     */
    public Doctor(Profile profile, String location, String specialtyName, String npi) {
        super(profile, Location.valueOf(location));
        this.specialty = Specialty.valueOf(specialtyName);
        this.npi = npi;
    }

    /**
     * getter for rate
     * @return returns rate
     */
    @Override
    public int rate() {
        return specialty.getCharge();
    }

    /**
     * Calculates and returns the correct credits from the cost and teh number of appointments
     *
     * @param appointments - list of possible appointments
     * @return - returns total cost
     */
    @Override
    public String calculateCredits(List<Appointment> appointments){
        int rate = this.rate();
        int totalCredits = 0;
        for (Appointment appt : appointments){
            if (appt.getProvider().equals(this)){
                totalCredits += rate;
            }
        }
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "[credit amount: $" + df.format(totalCredits) + "]";
    }

    /**
     * Getter for specialty
     * @return - returns the doctors specialty
     */
    public Specialty getSpecialty(){
        return this.specialty;
    }

    /**
     * Getter for doctors NPI
     * @return - returns the NPI
     */
    public String getNPI(){
        return this.npi;
    }

    /**
     * Getter the profile string
     * @return - returns the profile to string
     */
    public String toString(){
        return "[" + this.getProfile() + ", " + this.getLocation() + "][" + this.getSpecialty() + ", #" + this.npi + "]";
    }
}
