package sealey.javafxinventorysystem.models;

/*
 * @author Max Sealey
 *
 * The Outsourced class inherits attributes from abstract class Part, and adds the company name.
 * */

public class OutSourced extends Part {

    private String companyName;

    /*
     * Outsourced class constructor, called when an Outsourced object is created. Assigns initial values.
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max) {

        super(id, name, price, stock, min, max);
        this.companyName = getCompanyName();
    }

    /*
     * @return companyName company name to get (string)
     * */
    public String getCompanyName() {

        return companyName;
    }

    /*
    * @param companyName company name to set (string)
    * */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}
