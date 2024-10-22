package clinic;
//package clinic;

/**
 * Class for Timeslot implements Comparable
 */
public class Timeslot implements Comparable<Timeslot> {
    /** hour to hold the given hour*/
    private int hour;
    /** minute to hold the given minute*/
    private int minute;

    /** static final for the morning start hour*/
    static final int MORNING_START_HOUR = 9;
    /** static final for the afternoon start hour*/
    static final int AFTERNOON_START_HOUR = 14;
    /** static final for the start minute*/
    static final int START_MINUTE = 0;
    /** static final for the 30 minute increments*/
    static final int MINUTE_INCREMENT = 30;
    /** static final for the minutes in an hour*/
    static final int MINUTES_IN_HOUR = 60;
    /** static final for the max slots in the morning */
    static final int NUMBER_OF_TIMESLOTS_IN_MORNING = 6;

    /**
     * Constructor for Timeslot
     * @param hour - given hour to be
     * @param minute - given minute to be
     */
    public Timeslot(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    };

    /**
     * equals compare function for two Timeslot objects
     * @param obj - Timeslot to be compared
     * @return returns true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Timeslot other) return (this.hour == other.hour && this.minute == other.minute);
        return false;
    }

    /**
     * compare function for timeslot
     * @param other the object to be compared.
     * @return returns less than 0, if less, 0 if equal, greater than 0 if greater
     */
    @Override
    public int compareTo(Timeslot other){
        Integer one = (Integer) this.hour;
        Integer two = (Integer) other.hour;
        int compareHour = one.compareTo(two);
        if(compareHour != 0){
            return compareHour;
        }
        Integer one1 = (Integer) this.minute;
        Integer two2 = (Integer) other.minute;
        return one1.compareTo(two2);
    }

    /**
     * Converts a given string into an TimeSlot
     * @param timeslotStr - String to be converted to a timeslotStr
     * @return a valid Timeslot equivalent, or null upon error
     * @exception Exception - throws if not valid timeslot
     */
    public static Timeslot stringToTimeSlot(String timeslotStr) throws Exception{
        int intTime;
        try{ // handles non numbers
            intTime = Integer.parseInt(timeslotStr);
        }
        catch (NumberFormatException e){
            throw new InvalidTimeslotException(timeslotStr + " is not a valid time slot.");
        }
        if(intTime < 1 || intTime > 12){ // handles if the timeslotStr is in the range
            throw new InvalidTimeslotException(timeslotStr + " is not a valid time slot.");
        }
        boolean morning = true;

        if (intTime > NUMBER_OF_TIMESLOTS_IN_MORNING){
            morning = false;
            intTime -= NUMBER_OF_TIMESLOTS_IN_MORNING;
        }
        intTime--;
        int totalMinutes = (Timeslot.MINUTE_INCREMENT) * intTime;
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        return morning ? new Timeslot(Timeslot.MORNING_START_HOUR + hours, minutes) : new Timeslot(Timeslot.AFTERNOON_START_HOUR + hours, minutes);
    }

    /**
     * to string for Timeslot
     * @return to string for the timeslot
     */
    public String toString(){
        String period = this.hour <= 12 ? " AM" : " PM";
        int printHour = this.hour > 12 ? (this.hour - 12) : this.hour;
        String printMinutes = this.minute == 0 ? "00" : String.valueOf(this.minute);
        return printHour + ":" + printMinutes + period;
    }

    /**
     * gets the number of the timeslot
     * @return returns the timeslot number 1-12
     */
    public int getNum(){
        boolean morning = this.hour <= 11;
        if (morning)
            return ((this.hour - MORNING_START_HOUR) * 2) + (this.minute == MINUTE_INCREMENT ? 1 : 0) + 1;
        else
            return (((this.hour - AFTERNOON_START_HOUR) * 2) + (this.minute == MINUTE_INCREMENT ? 1 : 0) + 1) + 6;
    }

    public int getHour(){ return this.hour; }
    public int getMinute(){ return this.minute; }

}