package clinic;

import java.text.DecimalFormat;
import util.List;

/**
 * Class for technician that extends Provider
 * @author Andrew Ho, Amit Deshpande
 */
public class Technician extends Provider {
    /** the rate per visit for the technician */
    private int ratePerVisit;

    /**
     * Cosntructor for the technician
     * @param profile - technician profile
     * @param location - technnician location
     * @param ratePerVisit - technician rate
     */
    public Technician(Profile profile, String location, int ratePerVisit){
        super(profile, Location.valueOf(location));
        this.ratePerVisit = ratePerVisit;
    }

    /**
     * Getter for the rate
     * @return returns rate
     */
    @Override
    public int rate() {
        return ratePerVisit;
    }

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
     * equals method for Technician
     * @param obj - technician object to be compared
     * @return - return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Technician other) return (this.getProfile().equals(other.getProfile()) && this.getLocation().equals(other.getLocation()));
        return false;
    }

    /**
     * Getter for string version of technician
     * @return - string of technician
     */
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.00");
        return "[" + this.getProfile() + ", " + this.getLocation() + "][rate: $" + df.format((float)this.rate()) + "]";
    }
}
