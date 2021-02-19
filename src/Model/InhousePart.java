package Model;
/** This class handles the extended Part, InhousePart. */
public class InhousePart extends Part{
    private int machineId;
/** creates an inhouse part. */
    public InhousePart (int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
/**
 * @return the machineId
 * */
    public int getMachineId() {
        return machineId;
    }
/** @param machineId he machineId to set */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
