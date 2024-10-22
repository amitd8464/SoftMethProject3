package clinic;

import util.Date;

/**
 * Stores information about the patient (first name, last name and date of birth)
 * @author Amit Deshpande
 */
public class Profile implements Comparable<Profile>{
    /**
     * fname - String for first name
     */
    private String fname;
    /**
     * lname - String for last name
     */
    private String lname;
    /**
     * dob - instance of Date for date of birth
     */
    private Date dob;

    /**
     * Constructor for Profile
     * @param fname - first name of patient
     * @param lname - last name of patient
     * @param dob - date of birth of patient
     */
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Getter for this.fname
     * @return fname for this instance
     */
    public String getFname(){
        return this.fname;
    }

    /**
     * Getter for this.lname
     * @return lname for this instance
     */
    public String getLname(){
        return this.lname;
    }

    /**
     * Getter for this.dob
     * @return dob for this instance
     */
    public Date getDob(){
        return this.dob;
    }

    /**
     * Override for equals() method for Profile
     * @param obj - any Object, presumably Profile object
     * @return true if obj equals instance, false otherwise
     */
    @Override
    public final boolean equals(Object obj){
        if(obj instanceof Profile instance){
            return ((instance.fname.equalsIgnoreCase(this.fname)) &&
                    (instance.lname.equalsIgnoreCase(this.lname)) &&
                    (instance.dob.equals(this.dob)));
        }
        return false;
    }

    /**
     * Override for toString() method for Profile
     * @return a string representation of this instance
     */
    @Override
    public String toString(){
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }

    /**
     * Override for compareTo() method for Profile
     * @param obj - an instance of Profile
     * @return 0 if both instances are equal, positive int if this instance is greater, negative int if this instance is lesser
     */
    @Override
    public int compareTo(Profile obj){
        if (obj == null){
            throw new NullPointerException("compareTo() cannot take a null object as argument");
        }
        else{
            int lnameCompare = this.lname.compareToIgnoreCase(obj.lname);
            if (lnameCompare != 0){
                return lnameCompare;
            }

            int fnameCompare = this.fname.compareToIgnoreCase(obj.fname);
            if (fnameCompare != 0){
                return fnameCompare;
            }
            return this.dob.compareTo(obj.dob);
        }
    }

    /**
     * Testbed main for Profile
     * @param args - main arguments
     */
    public static void main(String[] args){
        /*
        // test case one
        Date date1 = new Date(12,13,1989);
        Profile person1 = new Profile("Kat","Doe", date1);
        Date date2 = new Date(12,13,1989);
        Profile person2 = new Profile("John","Doe", date1);
        System.out.println("Test 1 (expected): negative value");
        System.out.println("Test 1 (actual): " + person2.compareTo(person1));

        date1 = new Date(12,13,1989);
        person1 = new Profile("John","Doe", date1);
        date2 = new Date(12,13,1989);
        person2 = new Profile("John","Anderson", date2);
        System.out.println("Test 2 (expected): negative value");
        System.out.println("Test 2 (actual): " + person2.compareTo(person1));

        date1 = new Date(12,13,1989);
        person1 = new Profile("John","Doe", date1);
        date2 = new Date(11,13,1989);
        person2 = new Profile("John","Doe", date2);
        System.out.println("Test 3 (expected): negative value");
        System.out.println("Test 3 (actual): " + person2.compareTo(person1));

        date1 = new Date(12,13,1989);
        person1 = new Profile("John","Doe", date1);
        date2 = new Date(12,13,1989);
        person2 = new Profile("Kat","Doe", date2);
        System.out.println("Test 4 (expected): positive value");
        System.out.println("Test 4 (actual): " + person2.compareTo(person1));

        date1 = new Date(12,13,1989);
        person1 = new Profile("John","Anderson", date1);
        date2 = new Date(12,13,1989);
        person2 = new Profile("John","Doe", date2);
        System.out.println("Test 5 (expected): positive value");
        System.out.println("Test 5 (actual): " + person2.compareTo(person1));

        date1 = new Date(12,13,1989);
        person1 = new Profile("John","Doe", date1);
        date2 = new Date(12,13,1990);
        person2 = new Profile("John","Doe", date2);
        System.out.println("Test 6 (expected): positive value");
        System.out.println("Test 6 (actual): " + person2.compareTo(person1));

        date1 = new Date(12,13,1989);
        person1 = new Profile("John","Doe", date1);
        date2 = new Date(12,13,1989);
        person2 = new Profile("John","Doe", date2);
        System.out.println("Test 7 (expected): 0");
        System.out.println("Test 7 (actual): " + person2.compareTo(person1));
        * */
    }
}
