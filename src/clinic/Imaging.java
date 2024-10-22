package clinic;

import util.Date;

/**
 * Class for imaging that extends Appointment
 */
public class Imaging extends Appointment {
    /** stores the Radiology room*/
    private Radiology room;

    /**
     * Constructor for Appointment
     *
     * @param date     - date of appointment
     * @param timeslot - timeslot of appointment
     * @param patient  - patient of appointment
     * @param provider - provider for appointment
     * @param room - room for the imaging
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room) {
        super(date, timeslot, patient, provider);
        this.room = room;
    }

    /**
     * Getter for room
     * @return - returns Radiology Room
     */
    public Radiology getRoom() {
        return room;
    }

    @Override
    public String toString(){
        return super.toString() + "[" + this.room + "]";
    }
}
