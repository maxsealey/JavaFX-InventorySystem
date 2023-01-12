package sealey.javafxinventorysystem.models;

/**
 * The Outsourced class inherits attributes from abstract class Part and adds a 'Company Name' attribute.
 *
 * @author Max Sealey
 * */

public class OutSourced extends Part {

    private String companyName;

    /**
     * Outsourced class constructor, called when an Outsourced object is created. Assigns initial values.
     * @param id id
     * @param name name
     * @param price price
     * @param stock stock
     * @param min min
     * @param max max
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max) {

        super(id, name, price, stock, min, max);
        this.companyName = getCompanyName();
    }

    /**
     * @return companyName company name to get (string)
     * */
    public String getCompanyName() {

        return companyName;
    }

    /**
    * @param companyName company name to set (string)
    * */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}
