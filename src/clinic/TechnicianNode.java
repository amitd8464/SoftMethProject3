package clinic;

/**
 * Class for Technician Node that holds technicians
 * @author Andrew Ho, Amit Deshpande
 */
public class TechnicianNode{
    /** instance of technician in this node*/
    private Technician technician;
    /** the pointer to the next node*/
    private TechnicianNode next;
    /** max number of technicians*/
    public static final int NUM_OF_TECHNICIANS = 6;

    /**
     * Constructor for the technician node
     * @param technician - technician to be in the node
     */
    public TechnicianNode(Technician technician){
        this.technician = technician;
        this.next = null;
    }

    /**
     * Getter for the next connected technician node
     * @return - returns next node
     */
    public TechnicianNode next(){
        return this.next;
    }

    /**
     * Getter for the technician
     * @return the technician in the node
     */
    public Technician getTechnician(){
        return this.technician;
    }

    /**
     * Sets the nodes next pointer to another node
     * @param next - node to set this node's next to
     */
    public void setNext(TechnicianNode next){
        this.next = next;
    }

    /**
     * Setter to set the tech in the node
     * @param tech - tech to be set in
     */
    public void setValue(Technician tech){
        this.technician = tech;
    }

    /**
     * equals compare function for technician nodes
     * @param obj - node to be compared
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TechnicianNode other) return this.technician.equals(other.technician);
        return false;
    }

    /**
     * to string for technician nodes
     * @return string output for a node
     */
    public String toString(){
        String ret = this.getTechnician().getProfile().getFname() + " " +  this.getTechnician().getProfile().getLname() + " (" + this.getTechnician().getLocation().name() + ")" + " --> ";
        TechnicianNode head = this.next();
        while (head != this){
            ret += head.getTechnician().getProfile().getFname() + " " +  head.getTechnician().getProfile().getLname() + " (" + head.getTechnician().getLocation().name() + ")";
            if (head.next() != this)
                ret +=  " --> ";
            head = head.next();
        }
        return ret;
    }
}