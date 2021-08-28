package Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PharmacyModel {
  private ObservableList<Product> products= FXCollections.observableArrayList();
  private SimpleObjectProperty<Product> trenutni=new SimpleObjectProperty<>();

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public Product getTrenutni() {
        return trenutni.get();
    }

    public SimpleObjectProperty<Product> trenutniProperty() {
        return trenutni;
    }

    public void setTrenutni(Product trenutni) {
        this.trenutni.set(trenutni);
    }

    public void napuni(){
        products.add(new Product("Paracetamol","1","Tablete",2,0));
        products.add(new Product("Flonidan","2","Tablete",2,11));
        products.add(new Product("Analgin","3","Tablete",2,15));
    }
}
