package clinic;

import java.io.FileNotFoundException;

/**
 * Runner
 * @author Andrew Ho, Amit Deshpande
 */

public class RunProject2 {
    /**
     * Runner
     * @param args - files
     */
    public static void main(String[] args) throws FileNotFoundException{
        new ClinicManager().run(args[0], args[1]);
    }
}
