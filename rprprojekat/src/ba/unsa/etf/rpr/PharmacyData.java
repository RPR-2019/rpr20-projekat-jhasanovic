package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.beans.Product;
import ba.unsa.etf.rpr.beans.SoldProduct;
import ba.unsa.etf.rpr.beans.User;

import java.util.ArrayList;
import java.util.List;

public class PharmacyData {
    private List<Product> products = new ArrayList<>();
    private List<SoldProduct> soldProducts = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public PharmacyData() {
    }

    public PharmacyData(List<Product> products, List<SoldProduct> soldProducts, List<User> users) {
        this.products = products;
        this.soldProducts = soldProducts;
        this.users = users;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<SoldProduct> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProduct> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
