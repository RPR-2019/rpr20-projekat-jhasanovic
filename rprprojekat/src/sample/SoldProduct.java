package sample;

import java.util.Date;

public class SoldProduct extends Product {
    private String sellerName;
    private Date datum;

    public SoldProduct(String name, String ID, Double price, Integer quantity, String purpose, String notes, String administrationMethod, String manufacturer, String description, String ingredients, String medicationType, String sellerName, Date datum) {
        super(name, ID, price, quantity, purpose, notes, administrationMethod, manufacturer, description, ingredients, medicationType);
        this.sellerName = sellerName;
        this.datum = datum;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
