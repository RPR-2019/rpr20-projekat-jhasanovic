package sample;

public class SoldProduct{
    private Integer ID;
    private String name;
    private String sellerName;
    private String date;

    public SoldProduct(Integer id, String name, String sellerName, String date) {
        this.ID = id;
        this.name = name;
        this.sellerName = sellerName;
        this.date = date;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
