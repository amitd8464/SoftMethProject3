package clinic;

import java.text.DecimalFormat;
import util.Date;

/**
 * Stores the patient's Profile along with a linked list of visits
 * @author Amit Deshpande
 */
public class Patient extends Person{
    /**
     * visits - instance of Visit for list of appointments
     */
    private Visit visits;

    /**
     * Constructor for Appointment
     * @param profile - Profile instance for this patient
     */
    public Patient(Profile profile){
        super(profile);
        this.visits = null; // head is null initially, because there are no visits
    }

    /**
     * Getter for this.visits
     * @return instance variable visits for this instance
     */
    public Visit getVisits(){
        return this.visits;
    }

    /**
     * Override for equals method for this class
     * @param obj - generic object
     * @return true if this instance equals the param instance, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Patient other){
            return (this.getProfile().equals(other.getProfile()));
        }
        else{
            throw new IllegalArgumentException("equals() was given an object that wasn't an Patient");
        }
    }

    /**
     * Override for toString for this class
     * @return string representation of Patient
     */
    @Override
    public String toString(){
        return this.getProfile().toString();
    }

    /**
     * Determines the total charge of visits for this Patient
     *
     * @return total charge (int)
     */
    public String charge(){
        Visit head = this.visits;
        int charge = 0;
        while (head != null){
            Person p = head.getAppointment().getProvider();
            Provider provider = (Provider) p;

            charge += provider.rate();
            head = head.getNext();
        }
        // [due: $410.00]
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "[due: $" + df.format(charge) + "]";
    }

    /**
     * Adds a visit to the linked list of visits for this patient
     * @param newVisit - instance of Visit (latest medical visit by patient)
     */
    public void addVisit(Visit newVisit){
        Visit head = this.visits;
        if (head == null){
            this.visits = newVisit;
        }
        else{
            while (head.getNext() != null){
                head = head.getNext();
            }
            head.setNext(newVisit);
        }
    }

    /**
     * Removes a visit from the appointment list
     * @param appt - appointment to be removed
     */
    public void removeVisit(Appointment appt){

        Visit head = this.visits;
        if (head == null) return; // visits is empty, nothing to remove. this check should never actually be needed.

        if (head.getAppointment().equals(appt)) { // head itself is the appointment, set head to next.
            head.setNext(head.getNext());
            return;
        }
        Visit current = head;
        while (current.getNext() != null && !(current.getNext().getAppointment().equals(appt))) {
            current = current.getNext();
        }

        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
        }
    }

    public static void main(String[] args){
        /*
        Patient a = new Patient(new Profile("Amit", "Deshpande", new Date(2003, 8, 2)));
        Date apptDate = new Date(2024, 10, 20);
        Doctor doc = new Doctor(new Profile("Andrew", "Patel", new Date(1999, 2, 2)), "BRIDGEWATER", "FAMILY", "01");
        a.addVisit(new Visit(new Appointment(apptDate, new Timeslot(9, 30), a, doc)));
        a.addVisit(new Visit(new Appointment(apptDate, new Timeslot(10, 0), a, doc)));
        Appointment apptToRemove = new Appointment(apptDate, new Timeslot(10, 30), a, doc);
        a.addVisit(new Visit(apptToRemove));
        a.removeVisit(apptToRemove);

        System.out.println("provider rate: " + doc.rate());
        System.out.println("Amit's bill: " + a.charge());

         */
    }
}