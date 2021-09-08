package sample;

public class SoldProduct{
    private Integer idSold;
    private Integer id;
    private String name;
    private Integer quantity;
    private String sellerName;
    private String date;

    public SoldProduct(Integer idSold, Integer id, String name, Integer quantity, String sellerName, String date) {
        this.idSold = idSold;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.sellerName = sellerName;
        this.date = date;
    }

    public Integer getIdSold() {
        return idSold;
    }

    public void setIdSold(Integer idSold) {
        this.idSold = idSold;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

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
