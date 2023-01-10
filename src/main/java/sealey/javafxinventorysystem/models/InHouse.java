package sealey.javafxinventorysystem.models;

/*
* @author Max Sealey
*
* The InHouse class inherits attributes from abstract class Part, and adds Machine ID number.
* */
public class InHouse extends Part {
    private int machineId;

    /*
    * InHouse class constructor, called when an InHouse object is created. Assigns initial values.
    */
    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        this.machineId = getMachineId();
    }

    /*
    * @return machineId machine id number to get (integer)
    * */
    public int getMachineId() {
        return machineId;
    }

    /*
    * @param machineId ID number to assign
    * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
