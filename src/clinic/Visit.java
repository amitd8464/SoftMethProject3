package clinic;

/**
 * A linked list to representing consecutive appointment visits
 * @author Amit Deshpande
 */
public class Visit {

    /**
     * appointment - instance of appointment for the visit
     */
    private Appointment appointment;
    /**
     * next - instance of Visit for the visit
     */
    private Visit next;

    /**
     * Constructor for Visit class
     * @param appointment - instance of appointment for the Visit
     */
    public Visit(Appointment appointment){
        this.appointment = appointment;
        this.next = null;
    }

    /**
     * Getter for this.next
     * @return next instance of Visit in linked list
     */
    public Visit getNext(){
        return this.next;
    }

    /**
     * Setter for this.next
     * @param next - sets next instance in linked list
     */
    public void setNext(Visit next){
        this.next = next;
    }

    /**
     * Getter for this.appointment
     * @return instance of Appointment for this Visit
     */
    public Appointment getAppointment(){
        return this.appointment;
    }
}
