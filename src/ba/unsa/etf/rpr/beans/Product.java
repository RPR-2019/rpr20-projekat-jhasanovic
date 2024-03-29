package ba.unsa.etf.rpr.beans;

import java.io.Serializable;

public class Product implements Serializable {
    private String name; //ime lijeka
    private Integer id;//kod lijeka
    private Double price;//cijena lijeka
    private Integer quantity;//kolicina lijeka
    private String purpose;//namjena lijeka
    private String notes;//dodatne napomene
    private String administrationMethod;//nacin upotrebe
    private String manufacturer;//proizvodjac
    private String description;//opis lijeka
    private String ingredients;//sastav lijeka
    private String medicationType;//farmaceutski oblik lijeka

    public Product() {
    }

    public Product(String name, Integer id, Double price, Integer quantity, String purpose, String notes, String administrationMethod, String manufacturer, String description, String ingredients, String medicationType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.purpose = purpose;
        this.notes = notes;
        this.administrationMethod = administrationMethod;
        this.manufacturer = manufacturer;
        this.description = description;
        this.ingredients = ingredients;
        this.medicationType = medicationType;
    }


    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAdministrationMethod() {
        return administrationMethod;
    }

    public void setAdministrationMethod(String administrationMethod) {
        this.administrationMethod = administrationMethod;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", ID='" + id + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'';
    }
}
