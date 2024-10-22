package clinic;

import util.Date;

/**
 * Stores information about an appointment
 * Includes date, time, patient and provider of appt
 * @author Andrew Ho, Amit Deshpande
 */
public class Appointment implements Comparable <Appointment>{


    /**
     * date - instance of date for the date
     */
    protected Date date;

    /**
     * timeslot - instance of timeslot for the timeslot
     */
    protected Timeslot timeslot;

    /**
     * patient - instance of profile for the patients profile
     */
    protected Person patient;

    /**
     * provider - instance of provider for the doctor profile
     */
    protected Person provider;

    /**
     * Constructor for Appointment
     * @param date - date of appointment
     * @param timeslot - timeslot of appointment
     * @param patient - patient of appointment
     * @param provider - provider for appointment
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider){
        if (!(patient instanceof Patient)){
            throw new IllegalArgumentException("patient must be an instance of Patient");
        }
        else if (!(provider instanceof Provider)){
            throw new IllegalArgumentException("provider must be an instance of Provider");
        }
        else{
            this.date = date;
            this.timeslot = timeslot;
            this.patient = patient;
            this.provider = provider;
        }
    }

    /**
     * Getter method for this.date
     * @return date of this appointment instance
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * Getter method for this.timeslot
     * @return time of this appointment instance
     */
    public Timeslot getTimeslot(){
        return this.timeslot;
    }

    /**
     * Getter method for this.provider
     * @return provider of this appointment instance
     */
    public Person getProvider(){
        return this.provider;
    }

    /**
     * Getter method for this.patient (Profile object)
     * @return patient profile of this appointment instance
     */
    public Person getPatient(){
        return this.patient;
    }

    /**
     * Override for equals() method for Appointment object
     * @param obj - generic object
     * @return true if it is equal, else false, returns false if it is the incorrect object
     */
    @Override
    final public boolean equals(Object obj){
        if(obj instanceof Appointment userAppointment){
            return ((userAppointment.date.equals(this.date)) &&
                    (userAppointment.timeslot.equals(this.timeslot)) &&
                    (userAppointment.patient.equals(this.patient)));
        }
        else if(obj == null){
            return false;
        }
        else{
            throw new IllegalArgumentException("equals() was given an object that wasn't an Appointment");
        }
    }

    /**
     * Override for toString() method for Appointment object
     * This example format: 10/30/2024 10:45 AM John Doe 12/13/1989 [PATEL, BRIDGEWATER, Somerset 08807, FAMILY]
     * @return String representation of Appointment object
     */
    @Override
    public String toString(){
        return this.date.toString() + " " + this.timeslot.toString() + " " + this.patient.toString() + " " + this.provider.toString();
    }

    /**
     * Override for compareTo() method for Appointment object
     * @param appt - instance of appointment being compared to
     * @return 0 if both instances are equal, positive int if this instance is greater, negative int if this instance is lesser
     */
    @Override
    final public int compareTo(Appointment appt){
        if(appt == null){
            throw new NullPointerException("compareTo() cannot take a null argument");
        }
        else{
            int comparisionValue = appt.date.compareTo(this.date);
            if(comparisionValue == 0){
                comparisionValue = appt.timeslot.compareTo(this.timeslot);
            }
            if(comparisionValue == 0){
                comparisionValue = appt.patient.compareTo(this.patient);
            }
            if(comparisionValue == 0){
                comparisionValue = appt.provider.compareTo(this.provider);
            }
            return comparisionValue;
        }
    }


}