package Controllers;

public class Product {
    private String name;
    private String ID;
    private String category;
    private String price;
    private String quantity;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Product(String name, String ID, String category, String price, String quantity) {
        this.name = name;
        this.ID = ID;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", ID='" + ID + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'';
    }
}
