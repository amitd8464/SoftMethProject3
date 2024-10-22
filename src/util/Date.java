package util;
import java.util.Calendar;
import java.util.StringTokenizer;
import clinic.WeekendDateException;
import clinic.InvalidDateException;
import clinic.DatePastOrTodayException;
import clinic.DatePastSixMonthsException;

/**
 * Public class for Date implement Comparable
 */
public class Date implements Comparable<Date> {
    /** int year for the year of the date*/
    private int year;
    /** int month for the year of the date*/
    private int month;
    /** int day for the year of the date*/
    private int day;

    private static final int MONTH_OFF_SET = 1;
    /**
     * Constructor for Date instances
     * @param year - int year of the date
     * @param month - int month of the date
     * @param day - int day of the date
     */
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Getter for this.year
     * @return year of Date instance
     */
    public int getYear(){ return this.year;}

    /**
     * Getter for this.month
     * @return month of Date instance
     */
    public int getMonth(){ return this.month;}

    /**
     * Getter for this.day
     * @return day of Date instance
     */
    public int getDay(){ return this.day;}

    /**
     * Takes Date object and returns the string for it
     * @return String: month/day/year
     */
    @Override public String toString(){ return this.month + "/" + this.day + "/" + this.year;}

    /**
     * This is the override for equals() for the Date function
     * @param obj - generic object
     * @return true if equal, false if not, returns false if it is the incorrect object
     */
    @Override public boolean equals(Object obj){
        if(obj instanceof Date userDate){
            return (userDate.year == this.year) && (userDate.month == this.month) && (userDate.day == this.day);
        }
        return false;
    }

    /**
     * Compares two date instances
     * @param dateToCompare the object to be compared.
     * @return 0 if equal, Greater than 0 if Date this instance is greater, Less than 0 if dateToCompare is less
     */
    @Override
    public int compareTo(Date dateToCompare) {
        if(dateToCompare == null) {
            throw new NullPointerException("compareTo() cannot take null object");
        }
        else{
            int comparisionValue = Integer.compare(this.year, dateToCompare.year);
            if(comparisionValue == 0){
                comparisionValue = Integer.compare(this.month, dateToCompare.month);
            }
            if(comparisionValue == 0){
                comparisionValue = Integer.compare(this.day, dateToCompare.day);;
            }
            return comparisionValue;
        }
    }

    /**
     * Checks if the given date is valid based on the given month and day, doesn't check year
     * @return true if it is a valid date, false if the day is not within the range of the given month
     */
    public boolean isValid() { //check if the date is a valid calendar date
        // check valid year - project 1 was told this wasn't checked
        //  valid month
        // check valid day and handle if leap year
        switch(this.month - 1){
            // months with 31 days
            case(Calendar.JANUARY) ,(Calendar.MARCH), (Calendar.MAY), (Calendar.JULY), (Calendar.AUGUST), (Calendar.OCTOBER), (Calendar.DECEMBER):
                return this.day >= 1 && this.day <= 31;
            // months with 30 days
            case(Calendar.APRIL), (Calendar.JUNE), (Calendar.SEPTEMBER), (Calendar.NOVEMBER):
                return this.day >= 1 && this.day <= 30;
            // February 28 or 29 days
            case(Calendar.FEBRUARY):
                // leap year check
                if (this.year % 4 == 0){
                    if(this.year % 100 == 0){
                        if(this.year % 400 == 0){
                            return this.day >= 1 && this.day <= 29;
                        }
                    }
                    return this.day >= 1 && this.day <= 29;
                }
                return this.day >= 1 && this.day <= 28;
            default: // passed in something that is not a valid month
                return false;
        }
    }

    /**
     * Checks if this instance of date is before today, today or after today, depending on param
     * @param direction - direction to check chronology
     * @return true if direction = -1 and date is past today, if direction = 0 and date is today, if direction = 1 and date is in the future; false otherwise
     */
    public boolean chronology(int direction){
        Calendar now = Calendar.getInstance();
        Calendar appointment = Calendar.getInstance();
        appointment.set(this.year,this.month - MONTH_OFF_SET,this.day, 0, 0, 0);

        return switch (direction) {
            case -1 -> (appointment.before(now));
            case 0 -> (appointment.equals(now));
            case 1 -> (appointment.after(now));
            default -> throw new IllegalArgumentException("direction must be a value of -1, 0 or 1");
        };
    }

    /**
     * Checks if a given Date instance lands on a weekend
     * @return true if it lands on a weekend, false if it falls on a weekday
     */
    public boolean isWeekend(){
        Calendar appointment = Calendar.getInstance();
        appointment.set(this.year,this.month - MONTH_OFF_SET,this.day);
        int day = appointment.get(Calendar.DAY_OF_WEEK);
        return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
    }

    /**
     * Checks if the Date is within six months of the current date
     * @return true if Date is within six months, false if it is out of bounds of six months
     */
    public boolean withinSixMonths(){
        Calendar now = Calendar.getInstance();
        Calendar six = Calendar.getInstance();
        six.add(Calendar.MONTH,6);
        Calendar appointment = Calendar.getInstance();
        appointment.set(this.year,this.month - MONTH_OFF_SET,this.day);
        return appointment.after(now) && appointment.before(six);
    }

    /**
     * Private method to convert a possible string to a date instance
     * @param strDate - a String to be converted to the Date instance
     * @param appointment - a boolean, to see if we can to check if this date can also be a valid appointment
     * @return a created and filled Date object or null should there be an error, or it's invalid
     * @throws Exception should the string not be a date
     */
    public static Date strToDate (String strDate, boolean appointment) throws Exception{
        StringTokenizer tokenizedDate = new StringTokenizer(strDate,"/",false);
        if(tokenizedDate.countTokens() != 3){
            return null;
        }
        int month,day,year;
        try{   // checks date format upon conversion
            month = Integer.parseInt(tokenizedDate.nextToken());
            day = Integer.parseInt(tokenizedDate.nextToken());
            year = Integer.parseInt(tokenizedDate.nextToken());
        }
        catch(NumberFormatException e){
            return null; // format of date is wrong, so it is an invalid command, prob not a number
        }
        Date userDate = new Date(year, month, day);

        if (!userDate.isValid()){ // this should check if day works for the month
            String dateType = appointment ? "Appointment date: " : "Patient dob: ";
            throw new InvalidDateException(dateType + strDate + " is not a valid calendar date ");
        }
        // date is today -- not legal for appointment OR date of birth
        if (userDate.chronology(0)){
            String dateType = appointment ? "Appointment date: " : "Patient dob: ";
            throw new DatePastOrTodayException(dateType + strDate + " is today or a date before today.");
        }
        // userDate is an appointment and in the past
        if (appointment && userDate.chronology(-1)){
            throw new DatePastOrTodayException("Appointment date: " + strDate + " is today or a date before today.");
        }
        // userDate is a DOB and in the future
        if (!appointment && userDate.chronology(1)){
            throw new DatePastOrTodayException("Patient dob: " + strDate + " is today or a date after today.");
        }
        if(!userDate.withinSixMonths() && appointment){
            throw new DatePastSixMonthsException("Appointment date: " + strDate + " is not within six months.");
        }
        else if (userDate.isWeekend() && appointment){
            throw new WeekendDateException("Appointment date: " + strDate + " is Saturday or Sunday.");
        }
        return userDate;
    }

    public static void main(String[] args){
        Date date1 = new Date(2024, 10, 30);
        Date date2 = new Date(2024, 10, 28);

        if (date2.compareTo(date1) < 0){ // date2 is less than (before) date1
            System.out.println("Works");
        }
        else System.out.println("Not works");
    }
}