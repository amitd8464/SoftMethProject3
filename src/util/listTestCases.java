package util;

import clinic.Provider;
import clinic.Doctor;
import clinic.Profile;
import clinic.Technician;
import junit.framework.TestCase;

/**
 * add() and remove()methods in the List class;
 *  add() – one test case for adding a Doctor object and one for adding a Technician object to a
 * List object.
 *  remove() – one test case to remove a Doctor object and one to remove a Technician object from a
 * List object.
 */

public class listTestCases extends TestCase {
    /**
     * constructor for test
     * @param message - message to be send for test
     */
    public listTestCases(String message) {
        super(message);
    }

    /**
     * test case 1
     */
    public void testList(){
        Date birthday = new Date(1995,7,15);
        Profile soul = new Profile("Bob","Arm",birthday);
        Technician bob = new Technician(soul,"EDISON",3);
        List<Provider> list = new List<Provider>();
        list.add(bob);
        assertTrue(list.contains(bob)); // test bob is there
        list.remove(bob);
        assertFalse(list.contains(bob)); // bob isn't here
    }

    /**
     * test list 2
     */
    public void testList1(){
        Date birthday = new Date(1995,7,15);
        Profile soul = new Profile("Bob","Arm",birthday);
        Doctor bob = new Doctor(soul,"EDISON","FAMILY","23");
        List<Provider> list = new List<Provider>();
        list.add(bob);
        assertTrue(list.contains(bob)); // test bob is there
        list.remove(bob);
        assertFalse(list.contains(bob)); // test bob isn't here
    }
}
