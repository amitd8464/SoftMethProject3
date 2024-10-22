package util;
import junit.framework.TestCase;

/**
 * Test cases
 * @author Andrew Ho, Amit Deshpande
 */
public class validTest extends TestCase {
    /**
     * constructor for validTest
     * @param message - message to be sent for test
     */
    public validTest(String message){
        super(message);
    }

    /**
     * (a) isValid() in the Date class; four invalid and two valid test cases
     **/

    /**
     * 11/14/2024, valid
     */
    public void testValidDate() {
        Date date = new Date(2024, 11, 14);
        assertTrue(date.isValid());
    }

    /**
     * 1/1/2025, valid
     */
    public void testValidDate1() {
        Date date = new Date(2024, 11, 14);
        assertTrue(date.isValid());
    }

    /**
     * 11/32/2024, invalid
     */
    public void testValidDate2() {
        Date date = new Date(2024, 11, 31);
        assertFalse(date.isValid());
    }

    /**
     * 1/32/2025, invalid
     */
    public void testValidDate3() {
        Date date = new Date(2025, 1, 32);
        assertFalse(date.isValid());
    }

    /**
     * 4/31/2025, invalid
     */
    public void testValidDate4() {
        Date date = new Date(2025, 4, 31);
        assertFalse(date.isValid());
    }

    /**
     * // 2/30/2026, invalid
     */
    public void testValidDate5(){
        Date date = new Date(2025, 12, 32);
        assertFalse(date.isValid());
    }

    /**
     * test cases for 4/31/2025, invalid
     */
    public void testValidDate6(){
        Date date = new Date(2025,3,-1);
        assertFalse(date.isValid());
    }
}
