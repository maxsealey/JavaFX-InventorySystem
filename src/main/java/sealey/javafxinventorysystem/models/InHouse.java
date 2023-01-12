package sealey.javafxinventorysystem.models;

/**
* The InHouse class inherits attributes from abstract class Part and adds Machine ID attribute.
 * @author Max Sealey
* */
public class InHouse extends Part {

    private int machineId;

    /**
     * InHouse class constructor, called when an InHouse object is created. Assigns initial values.
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     */

    public InHouse(int id, String name, double price, int stock, int min, int max) {

        super(id, name, price, stock, min, max);
        this.machineId = getMachineId();
    }

    /**
    * @return machineId machine id number to get (integer)
    * */
    public int getMachineId() {

        return machineId;
    }

    /**
    * @param machineId ID number to assign
    * */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }
}
