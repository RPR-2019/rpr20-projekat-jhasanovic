package sample;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class SoldProduct{
    private String ID;
    private String name;
    private String sellerName;
    private LocalDateTime datum;

    public SoldProduct(String id, String name, String sellerName, LocalDateTime datum) {
        this.ID = id;
        this.name = name;
        this.sellerName = sellerName;
        this.datum = datum;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {
        this.ID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }
}
