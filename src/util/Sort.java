package util;
import clinic.*;

/**
 * Class for sort
 * @author Andew Ho, Amit Deshpande
 */

public class Sort <T> {
    /**Key value for a PA sort*/
    public static final char PA = 'A';
    /**Key value for a PP sort*/
    public static final char PP = 'P';
    /**Key value for a PL sort*/
    public static final char PL = 'L';
    /**Key value for a PS sort*/
    public static final char PS = 'S';
    /**Key value for a PO sort*/
    public static final char PO = 'O';
    /**Key value for a PI sort*/
    public static final char PI = 'I';
    /**Key value for a PC sort*/
    public static final char PC = 'C';

    /**
     * Does pre-checks for sorting like proper key and type, does not sort in place
     * @param list - list to be sorted
     * @param key - sorting priority flag
     * @return returns the sorted list based on the flag
     * @throws KeyException - throws error if key isn't valid
     * @throws elementTypeError - throws error if type isn't valid
     */
    public List<T> sorter(List<T> list, char key) throws KeyException, elementTypeError{
        // Appointment- PA,PP,PL,PS,PO
        // Imaging - PI
        // Provider - PC
        List<T> filteredList = new List<>();
        switch(key){
            case (PA):
            case (PP):
            case (PL):
                for(T element: list){
                    if(element instanceof Appointment){
                        filteredList.add(element);
                    }
                }
                mySort(filteredList,0,filteredList.size()-1,key);
                return filteredList;
            case (PS):
                for(T element: list){
                    if(element instanceof Patient){
                        filteredList.add(element);
                    }
                }
                mySort(filteredList,0,filteredList.size()-1,key);
                return filteredList;
            case (PO):
                for (T element: list){
                    if (element instanceof Appointment && !(element instanceof Imaging)){
                        filteredList.add(element);
                    }
                }
                mySort(filteredList,0,filteredList.size()-1,key);
                return filteredList;
            case(PI):
                for(T element: list){
                    if(element instanceof Imaging){
                        filteredList.add(element);
                    }
                }
                mySort(filteredList,0,filteredList.size()-1,key);
                return filteredList;
            case(PC):
                for(T element: list){
                    if(!(element instanceof Provider)){
                        throw new elementTypeError("Not Provider instance");
                    }
                    else{
                        filteredList.add(element);
                    }
                }
                mySort(filteredList,0,filteredList.size()-1,key);
                return filteredList;
            default:
                throw new KeyException("Not a valid key");
        }
    }

    /**
     * Main driver for the sort function, it can handle Appointment, Imagining and Profile objects and sort them based on the key command
     * @param list -the list of objects to be sorted
     * @param left - the starting index for the sort
     * @param right - the ending index for the sort
     * @param key - key, indicates object type and sorting method
     */
    private void mySort(List<T> list,int left, int right,char key){
        if(left < right){
            int part = partition(list,left,right,key);
            mySort(list,left,part-1,key);
            mySort(list,part+1,right,key);
        }
    }

    /**
     * Partition for the Quick sort method
     * @param list - list to be sorted
     * @param left - starting index
     * @param right - ending index
     * @param key - indicates object type and sorting method
     * @return returns the partition index for the algorithm
     */
    private int partition(List<T> list,int left, int right,char key){
        T pivot = list.get(right);
        int i = (left -1);
        for(int j = left;j < right;j++){
            if(compareCaller(list.get(j),list.get(right),key) <= 0){
                i++;
                swap(list,i,j);
            }
        }
        swap(list,i+1,right);
        return i+1;
    }

    /**
     * This function swaps any two objects in the list
     * @param list - the list in which items are to be swapped
     * @param left - the index of an item in the list to be swapped
     * @param right - the index of an item in the list to be swapped
     * @param <T> - generic type of item to be swapped
     */
    private static <T> void swap(List<T> list,int left,int right){
        T temp = list.get(left);
        T temp2 = list.get(right);
        //list.remove(temp);
        list.set(right,temp);
        //list.remove(temp2);
        list.set(left,temp2);
    }

    /**
     * This method handled type checking and does the proper casting
     * @param o1 - First Object to be compared
     * @param o2 - Second Object to be compared
     * @param key - indicates object type and compare method
     * @return returns less than 0 for less, 0 for equal and greater than 0 for greater than
     * @param <T> - generic item to be checked
     */
    private static <T> int compareCaller(T o1, T o2,char key){
        if ((o1 instanceof Patient) && (o2 instanceof Patient)){
            return comparePriority((Patient)o1, (Patient)o2);
        }
        if((o1 instanceof Imaging) && (o2 instanceof Imaging)){
            return comparePriority((Imaging) o1, (Imaging) o2);
        }
        else if((o1 instanceof Appointment) && (o2 instanceof Appointment)){
            return comparePriority((Appointment) o1, (Appointment) o2,key);
        }
        else if((o1 instanceof Provider) && (o2 instanceof Provider)){
            return comparePriority((Provider) o1, (Provider) o2);
        }
        return 0;
    }

    private static int comparePriority(Patient o1, Patient o2){
            int compareProfile = o1.getProfile().compareTo(o2.getProfile());
            if(compareProfile != 0){return compareProfile;}
            return o1.charge().compareTo(o2.charge());
    }

    /**
     * This method is an overload and compares Appointment objects by some key
     * @param o1 - Appointment object to be compared
     * @param o2 - Appointment object to be compared
     * @param key - indicates object type and compare method
     * @return returns less than 0 for less than, 0 for equal and greater than 0 for greater than
     */
    private static int comparePriority(Appointment o1, Appointment o2,char key){
        int compareDate;
        int compareTime;
        switch(key) {
            case (PA):
                compareDate = o1.getDate().compareTo(o2.getDate());
                if(compareDate != 0){ return compareDate;}
                compareTime = o1.getTimeslot().compareTo(o2.getTimeslot());
                if(compareTime != 0){ return compareTime;}
                return o1.getProvider().getProfile().compareTo(o2.getProvider().getProfile());
            case (PP):
                int compareLastname = o1.getPatient().getProfile().getLname().compareTo(o2.getPatient().getProfile().getLname());
                if(compareLastname != 0){ return compareLastname;}
                int compareFirstname = o1.getPatient().getProfile().getFname().compareTo(o2.getPatient().getProfile().getFname());
                if(compareFirstname != 0){ return compareFirstname;}
                int compareDob = o1.getPatient().getProfile().getDob().compareTo(o2.getPatient().getProfile().getDob());
                if(compareDob != 0){ return compareDob;}
                compareDate = o1.getDate().compareTo(o2.getDate());
                if(compareDate != 0){ return compareDate;}
                return o1.getTimeslot().compareTo(o2.getTimeslot());
            case (PL):
                int compareCounty = searchCounty(o1.getProvider().getProfile()).compareTo(searchCounty(o2.getProvider().getProfile()));
                if(compareCounty != 0 ){return compareCounty;}
                compareDate = o1.getDate().compareTo(o2.getDate());
                if(compareDate != 0){ return compareDate;}
                return o1.getTimeslot().compareTo(o2.getTimeslot());
            case(PO):
                int compareCountyName = searchCounty(o1.getProvider().getProfile()).compareTo(searchCounty(o2.getProvider().getProfile()));
                if(compareCountyName != 0){return compareCountyName;}
                compareDate = o1.getDate().compareTo(o2.getDate());
                if(compareDate != 0){ return compareDate;}
                return o1.getTimeslot().compareTo(o2.getTimeslot());
        }
        return 0;
    }

    /**
     * This is an overloaded method that handles priority for Imaging objects
     * @param o1 - Imaging object to be compared
     * @param o2 - Imaging object to be compared
     * @return returns less than 0 for less than, 0 for equal and greater than 0 for greater than
     */
    private static int comparePriority(Imaging o1, Imaging o2){
        // sorted by the county name, then date and time
        int compareCountyName = searchCounty(o1.getProvider().getProfile()).compareTo(searchCounty(o2.getProvider().getProfile()));
        if(compareCountyName != 0){return compareCountyName;}
        int compareDate = o1.getDate().compareTo(o2.getDate());
        if(compareDate != 0){ return compareDate;}
        int compareTimeslot = o1.getTimeslot().compareTo(o2.getTimeslot());
        if (compareTimeslot != 0) { return compareTimeslot; }
        return o1.getPatient().getProfile().getDob().compareTo(o2.getPatient().getProfile().getDob());
    }

    /**
     * Compares two Provider instances
     * @param o1 - instance of Provider to be compared
     * @param o2 - instance of Provider to be compared
     * @return returns less than 0 for less than, 0 for equal, greater than 0 for greater than
     */
    private static int comparePriority(Provider o1, Provider o2){
        int lCompare  = o1.getProfile().getLname().compareTo(o2.getProfile().getLname());
        if(lCompare != 0){ return lCompare;}
        int fCompare  = o1.getProfile().getFname().compareTo(o2.getProfile().getFname());
        if(fCompare != 0){ return fCompare;}
        return o1.getProfile().getDob().compareTo(o2.getProfile().getDob());
    }

    /**
     * Searches for the county from a Profile
     * @param input - Instance of a profile object to be searched for
     * @return returns the county should it be found, "" if it is not found
     */
    private static String searchCounty(Profile input){
        List<Provider> docsList = ClinicManager.getProviders();
        for(Provider doc: docsList){
            if(doc.getProfile().equals(input)){
                return doc.getLocation().getCounty();
            }
        }
        return "";
    }

    /**
     * Searches for rate from a Profile
     * @param input - Instance of Profile object to be searched for
     * @return returns the rate from the given profile, 0 if not found
     */
    private static int searchRate(Profile input){
        TechnicianNode rotation = ClinicManager.getRotationList();
        TechnicianNode temp = rotation.next();
        while(temp != rotation){
            if(rotation.getTechnician().getProfile().equals(input)){
                return rotation.getTechnician().rate();
            }
            temp = temp.next();
        }
        return 0;
    }
}