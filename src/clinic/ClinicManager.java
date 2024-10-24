package clinic;

import util.*;
// import util.IncorrectTypeException;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;


/**
 * This is the main driver file 
 * We take in input.txt commands and handle/call other methods for its execution
 * @author Andrew Ho, Amit Deshpande
 */
public class ClinicManager {
    /**Holds list of appointments */
    private static List<Appointment> appointments;
    /** Holds list of Providers */
    private static List<Provider> providers;
    /** Holds list of Patients*/
    private static List<Patient> patients;
    /** linked list of technicianNode*/
    private static TechnicianNode rotationList;

    /** Read out for the beginning of the list*/
    public static final String PA = "\n** List of appointments, ordered by date/time/provider.";
    /** Read out for the beginning of the list*/
    public static final String PP = "\n** List of appointments, ordered by the patient (by last name, first name, date of birth, then appointment date and time),";
    /** Read out for the beginning of the list*/
    public static final String PL = "\n** List of appointments, ordered by the county/date/time.";
    /** Read out for the beginning of the list*/
    public static final String PS = "\n** Billing statement ordered by patient. **";
    /** Read out for the beginning of the list*/
    public static final String PO = "\n** List of office appointments ordered by county/date/time.";
    /** Read out for the beginning of the list*/
    public static final String PI = "\n** List of radiology appointments ordered by county/date/time.";
    /** Read out for the beginning of the list*/
    public static final String PC = "\n** Credit amount ordered by provider. **";

    /**
     * Constructor for ClinicManager
     */
    public ClinicManager(){
        appointments = new List<>();
        providers = new List<>();
        patients = new List<>();
        rotationList = new TechnicianNode(null);
    }

    /**
     * Getter for rotationList
     * @return returns rotationList
     */
    public static TechnicianNode getRotationList() {
        return rotationList;
    }

    /**
     * Getter for provider
     * @return list of providers
     */
    public static List<Provider> getProviders() {
        return providers;
    }

    /**
     * Adds the provider to the provider list
     * @param providerLine - provider to be added
     */
    private static void createProvider(StringTokenizer providerLine) {
        String a = providerLine.nextToken();
        if (a.equals("D")) {
            // doctor
            try {
                Profile docProfile = new Profile(providerLine.nextToken().strip(), providerLine.nextToken().strip(), Date.strToDate(providerLine.nextToken().strip(), false));
                Provider newDoctor = new Doctor(docProfile, providerLine.nextToken().strip(), providerLine.nextToken().strip(), providerLine.nextToken().strip());
                providers.add(newDoctor);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (a.equals("T")) {
            // technician
            try {
                Profile techProfile = new Profile(providerLine.nextToken(), providerLine.nextToken(), Date.strToDate(providerLine.nextToken(), false));
                Provider newTech = new Technician(techProfile, providerLine.nextToken(), Integer.parseInt(providerLine.nextToken()));
                providers.add(newTech);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else
            throw new IllegalArgumentException("Missing data tokens.");
    }

    /**
     * This takes in the file and parses through the information and adds it to the provider list
     * @param filename - name of file to be parsed
     */
    public static void readProviders(String filename) {
        try {
            File file = new File(filename);
            Scanner providerScanner = new Scanner(file);

            StringTokenizer providerLine;
            while (providerScanner.hasNext()) {
                providerLine = new StringTokenizer(providerScanner.nextLine(), " ", false);
                createProvider(providerLine);
            }

            List<Provider> sortedProviders = util.ProviderSort.sort(providers);
            System.out.println("Providers loaded to the list.\n" + sortedProviders);
        } catch (FileNotFoundException e) {
            System.out.println("\"" + filename + "\"" + " does not exist.");
        }

    }

    /**
     * This creates the rotation list
     */
    private void setRotationList() {
        if (providers.get(providers.size() - 1) instanceof Technician tech) {
            rotationList.setValue(tech);
        }
        TechnicianNode head = rotationList;
        TechnicianNode ref = rotationList;
        for (int i = providers.size() - 2; i >= 0; i--) {
            if (providers.get(i) instanceof Technician tech) {
                head.setNext(new TechnicianNode(tech));
                head = head.next();
            }
        }
        // Set final technician's "next" to the first one, creating a circularly linked list:
        head.setNext(ref);
        System.out.println("Rotation list for the technicians.\n" + rotationList);
        System.out.println();
    }

    /**
     * Searches for the NPI from the provider list
     * @param npi - npi to search for
     * @return - returns doctor if found, null if not
     */
    private Doctor findNPI(String npi) {
        for (Provider provider : providers) {
            if (provider instanceof Doctor doctor) {
                if (doctor.getNPI().equals(npi))
                    return doctor;
            }
        }
        return null;
    }

    /**
     * Checks if patient is booked
     * @param patient - patient to be searched
     * @param apptDate - date of appointment to be checked
     * @param timeslot - time slot of the appointment
     * @return
     */
    private boolean patientBooked(Patient patient, Date apptDate, Timeslot timeslot) {
        for (Appointment appt : appointments) {
            if (appt.getDate().equals(apptDate) && appt.getTimeslot().equals(timeslot) && appt.getPatient().equals(patient))
                return true;
        }
        return false;
    }

    private boolean providerBooked(Provider provider, Date apptDate, Timeslot timeslot) {
        for (Appointment appt : appointments) {
            if (appt.getProvider().getProfile().equals(provider.getProfile()) && appt.getDate().equals(apptDate) && appt.getTimeslot().equals(timeslot))
                return true;
        }
        return false;
    }

    private boolean imagingLocationUnavailable(Date date, Timeslot timeslot, Radiology room, Location requestedLocation) {
        for (Appointment appt : appointments) {
            // Is this appointment an imaging appointment and using the requested imaging room?
            if (appt instanceof Imaging imagingAppt && imagingAppt.getRoom().equals(room)) {
                Provider provider = (Provider) appt.getProvider();
                // Is the appointment at the requested location?
                if (provider.getLocation().equals(requestedLocation)) {
                    // Is this appointment on the requested date and timeslot? If so, the location is unavailable
                    if (appt.getDate().equals(date) && appt.getTimeslot().equals(timeslot)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Technician findAvailableTech(Date apptDate, Timeslot timeslot, String fname, String lname, Date dob, String imagingType) throws Exception {
        try {
            Radiology room = Radiology.valueOf(imagingType.toUpperCase());
            boolean techFound = false;
            TechnicianNode ref = rotationList;

            int iteration = 0;
            // while statement stops when all technicians have been checked -- "no tech available"
            while (iteration < TechnicianNode.NUM_OF_TECHNICIANS) {
                // We have to check if the tech is booked. If so, rotate.
                if (providerBooked(rotationList.getTechnician(), apptDate, timeslot)) {
                    rotationList = rotationList.next();
                    iteration++;
                }
                // Then check if location is unavailable. If so, rotate until we find a tech at different location
                else if (imagingLocationUnavailable(apptDate, timeslot, room, rotationList.getTechnician().getLocation())) {
                    Location location = rotationList.getTechnician().getLocation();
                    while (rotationList.getTechnician().getLocation().equals(location)) {
                        rotationList = rotationList.next();
                        iteration++;
                    }
                }
                // Technician is available and location has room available. Return this technician.
                else {
                    // move to the next technician for next time
                    Technician ret = rotationList.getTechnician();
                    rotationList = rotationList.next();
                    return ret;
                }
            }
            if (!techFound) {
                throw new NoTechAvailableException("Cannot find an available technician at all locations for " + room.toString() + " at slot " + timeslot.getNum() + ".");
            }
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(imagingType + " - imaging service not provided.");
        }
        return null;
    }

    private void validateData(String apptDate, String dob) throws Exception{
        try {
            Date date1 = Date.strToDate(apptDate, true);
            Date date2 = Date.strToDate(dob, false);
        }
        catch (Exception e){
            throw e;
        }
    }

    public void handleCancel(Date apptDate, Timeslot timeslot, String fname, String lname, Date dob) {
        Profile profile = new Profile(fname, lname, dob);
        boolean found = false;
        for (Appointment appt : appointments) {
            if (appt.getDate().equals(apptDate) && appt.getTimeslot().equals(timeslot) && appt.getPatient().getProfile().equals(profile)){
                appointments.remove(appt);
                Patient p = (Patient)appt.getPatient();
                p.removeVisit(appt);
                found = true;
                System.out.println(apptDate + " " + timeslot + " " + profile + " - appointment has been canceled.");
            }
        }
        if (!found){ System.out.println(apptDate + " " + timeslot + " " + profile + " - appointment does not exist."); }
    }

    /*
    public void handleReschedule(Date apptDate, Timeslot oldTimeslot, String newTimeslotStr, String fname, String lname, Date dob) throws Exception {
        Patient patient = new Patient(new Profile(fname, lname, dob));
        try {
            boolean found = false;
            Timeslot newTimeslot = Timeslot.stringToTimeSlot(newTimeslotStr);
            // check if this appointment exists:
            // make a temporary appt (doctor does not matter)
            Appointment oldAppt = new Appointment(apptDate, oldTimeslot, patient, providers.get(0));
            for(Appointment appt : appointments){
                if (appt.equals(oldAppt)){
                    found = true;
                    // check if doctor is available and patient is available
                    Provider provider = (Provider)appt.getProvider();
                    if (providerBooked(provider, apptDate, newTimeslot)){
                        System.out.println(provider + " is not available at slot " + newTimeslot.getNum());
                    }
                    else if (patientBooked(patient, apptDate, newTimeslot)){
                        System.out.println(patient + " has an existing appointment at " + apptDate + " " + newTimeslot);
                    }
                    else{
                        // add appt to new timeslot
                        Appointment rescheduled = new Appointment(apptDate, newTimeslot, patient, provider);
                        appointments.add(rescheduled);
                        System.out.println("Rescheduled to " + rescheduled);
                        appointments.remove(appt);
                    }
                    break;
                }
            }
            if (!found) System.out.println(apptDate + " " + oldTimeslot + " " + patient + " does not exist.");
        } catch (InvalidTimeslotException e) {
            System.out.println(e.getMessage());
        }
    }
    */

    public void handleScheduleImaging(Date apptDate, Timeslot timeslot, String fname, String lname, Date dob, String imagingType) {
        try {
            Patient patient = new Patient(new Profile(fname, lname, dob));
            if (patientBooked(patient, apptDate, timeslot)) {
                throw new PatientUnavailableException(patient + " has an existing appointment at the same time slot.");
            }
            else{
                Technician availableTechnician = findAvailableTech(apptDate, timeslot, fname, lname, dob, imagingType);
                Imaging newImagingAppt = new Imaging(apptDate, timeslot, patient, availableTechnician, Radiology.valueOf(imagingType.toUpperCase()));
                appointments.add(newImagingAppt);
                if (patients.contains(patient)) {
                    patients.get(patients.indexOf(patient)).addVisit(new Visit(newImagingAppt));
                }
                else{
                    patients.add(patient);
                    patient.addVisit(new Visit(newImagingAppt));
                }
                System.out.println(newImagingAppt + " booked.");
            }
            // if patient booked
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleScheduleOffice(Date apptDate, Timeslot timeslot, String fname, String lname, Date dob, String npi) {
        // validate NPI
        try {
            Integer.parseInt(npi);
            Doctor doctor = findNPI(npi);
            if (doctor != null) {
                Patient patient = new Patient(new Profile(fname, lname, dob));
                if (patientBooked(patient, apptDate, timeslot)) {
                    System.out.println(patient + " has an existing appointment at the same time slot.");
                }
                else if (providerBooked(doctor, apptDate, timeslot)) {
                    System.out.println(doctor + " is not available at slot " + timeslot.getNum());
                }
                else{
                    Appointment newAppt = new Appointment(apptDate, timeslot, patient, doctor);
                    appointments.add(newAppt);
                    if (patients.contains(patient)) {
                        patients.get(patients.indexOf(patient)).addVisit(new Visit(newAppt));
                    }
                    else{
                        patients.add(patient);
                        patient.addVisit(new Visit(newAppt));
                    }
                    System.out.println(newAppt + " booked.");
                }
            } else
                System.out.println(npi + " - provider doesn't exist.");
        }
        catch (NumberFormatException e) {
            System.out.println(npi + " - provider doesn't exist.");
        }
    }

    /**
     * Takes in a string and handles its splitting and execution
     *
     * @param commandType - a String that indicates which command is being sent
     * @param tokenized_input - StringTokenizer instance that holds rest of the data tokens
     * @return true if command is valid in structure, even if the parts of the command may not be valid, false if the command is of the incorrect structure
     */
    private boolean longerCommandHandler(String commandType, StringTokenizer tokenized_input) {
        if (tokenized_input.countTokens() < 5) return false;
        if (tokenized_input.countTokens() == 6 && (!(commandType.equals("D")) && !(commandType.equals("T")) && !(commandType.equals("R")))){
            return false;
        }
        //System.out.println(tokenized_input.countTokens());
        if (tokenized_input.countTokens() == 5 && !(commandType.equals("C"))) {
            return false;
        }

        try {
            Date apptDate = Date.strToDate(tokenized_input.nextToken(), true);
            Timeslot timeslot = Timeslot.stringToTimeSlot(tokenized_input.nextToken());
            String fname = tokenized_input.nextToken();
            String lname = tokenized_input.nextToken();
            Date dob = Date.strToDate(tokenized_input.nextToken(), false);

            switch (commandType) {
                case "C":         // C - C,9/30/2024,1,John,Doe,12/13/1989
                    if (tokenized_input.countTokens() == 0) {
                        handleCancel(apptDate, timeslot, fname, lname, dob);
                        break;
                    }
                case "R":         // R - R,9/30/2024,1,John,Doe,12/13/1989,2
                    if (tokenized_input.countTokens() == 1) {
                        //handleReschedule(apptDate, timeslot, tokenized_input.nextToken(), fname, lname, dob);
                        break;
                    }
                case "T":         // T - T,9/30/2024,1,John,Doe,12/13/1989,xray
                    if (tokenized_input.countTokens() == 1) {
                        handleScheduleImaging(apptDate, timeslot, fname, lname, dob, tokenized_input.nextToken());
                        break;
                    }
                case "D":         // D - D,9/30/2024,1,John,Doe,12/13/1989,120
                    if (tokenized_input.countTokens() == 1) {
                        handleScheduleOffice(apptDate, timeslot, fname, lname, dob, tokenized_input.nextToken());
                        break;
                    }
                default:
                    return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    /**
     * Handles the printing of certain lists
     * @param input- key of the list to start
     */
    private void listPrinter(String input) {
        switch (input) {
            case("PA"): System.out.println(PA); break;
            case("PP"): System.out.println(PP); break;
            case("PL"): System.out.println(PL); break;
            case("PS"): System.out.println(PS); break;
            case("PO"): System.out.println(PO); break;
            case("PI"): System.out.println(PI); break;
            case("PC"): System.out.println(PC); break;
        }
    }

    /**
     * Run method for ClinicManager
     * @param <T> - generic
     * @param providersFilename - file name for providers
     */
    public <T> void run(String providersFilename, String inputFilename) throws FileNotFoundException{
        readProviders(providersFilename);
        setRotationList();
        File file = new File(inputFilename);
        Scanner in = new Scanner(file);
        System.out.println("Clinic Manager is running...\n\n");
        String userInput;
        do {
            userInput = in.nextLine().replaceAll("\\s+$", "");
            if(userInput.trim().isEmpty()){continue;}
            StringTokenizer tokenized_input = new StringTokenizer(userInput, ",", false);
            //System.out.println("Before commandType: " + tokenized_input.countTokens());
            String commandType = tokenized_input.nextToken();
            //System.out.println("After commandType: " + tokenized_input.countTokens());
            switch (commandType) {
                case "Q":
                    System.out.println("Clinic Manager terminated.");
                    break;
                case ("PA"):
                case ("PP"):
                case ("PL"):
                case ("PO"):
                case ("PI"):
                    if(appointments.isEmpty()){ System.out.println("Schedule calendar is empty.");
                        break;}
                    listPrinter(commandType);
                    List<?> tempList = new List<>();
                    try { Sort<Appointment> sorter = new Sort<>();
                        tempList = sorter.sorter(appointments,commandType.charAt(1));
                    } catch (Exception e) { System.out.println(e.getMessage());}
                    for(Object element: tempList){
                        if(element != null){System.out.println(element);}
                    }
                    System.out.println("** end of list **");
                    break;
                case ("PS"):
                    if(appointments.isEmpty()){ System.out.println("Schedule calendar is empty.");
                        break;}
                    listPrinter(commandType);
                    List<?> tempList2 = new List<>();
                    try { Sort<Patient> sorter = new Sort<>();
                        tempList2 = sorter.sorter(patients,commandType.charAt(1));
                    } catch (Exception e) { System.out.println(e.getMessage());}
                    int counter = 1;
                    for(Object element: tempList2){
                        Patient patient = (Patient)element;
                        if(element != null){System.out.println("(" + counter++ + ") " + patient + " " + patient.charge());}
                    }
                    System.out.println("** end of list **");
                    appointments = new List<>();
                    break;
                case ("PC"):
                    if(appointments.isEmpty()){ System.out.println("Schedule calendar is empty.");
                        break;}
                    listPrinter(commandType);
                    List<?> temperList = new List<>();
                    try { Sort<Provider> sorter = new Sort<>();
                        temperList = sorter.sorter(providers,(char)commandType.charAt(1));}
                    catch (Exception e) { System.out.println(e.getMessage());}
                    for(int i = 0; i < temperList.size(); i++){
                        Provider provider = (Provider)temperList.get(i);
                        System.out.println("(" + (i+1) + ") " + provider.getProfile() + " " + provider.calculateCredits(appointments));}
                    System.out.println("** end of list **");
                    break;
                case ("C"):
                case ("R"):
                case ("T"):
                case ("D"):
                    if (!longerCommandHandler(commandType, tokenized_input)){ System.out.println("Missing data tokens.");}
                    break;
                case ("\n"):
                    break;
                default:System.out.println("Invalid command!");
            }
        } while (!userInput.equals("Q"));
    }
}