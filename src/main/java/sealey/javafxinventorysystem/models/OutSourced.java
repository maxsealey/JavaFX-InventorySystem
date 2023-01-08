package sealey.javafxinventorysystem.models;

public class OutSourced extends Part {
    private String companyName;
    public OutSourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        this.companyName = getCompanyName();
    }

    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
