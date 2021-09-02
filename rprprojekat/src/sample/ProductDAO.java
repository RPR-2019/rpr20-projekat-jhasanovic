package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductDAO {
    private static ProductDAO instance=null;
    private Connection conn;
    private PreparedStatement sviProizvodiUpit,pretragaUpit,dodajProizvodUpit,ukloniProizvodUpit,azurirajProizvodUpit,promjenaKolicineUpit;

    private ProductDAO() throws SQLException {
        //String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/.apotekaapp/apoteka.db";
        String url = "jdbc:sqlite:apoteka.db";
        conn = DriverManager.getConnection(url);
        try {
            sviProizvodiUpit = conn.prepareStatement("SELECT * FROM proizvod ORDER BY name");
        }
        catch(SQLException e){
            kreirajBazu();
            sviProizvodiUpit = conn.prepareStatement("SELECT * FROM proizvod ORDER BY name");
        }
        pretragaUpit = conn.prepareStatement("SELECT * FROM proizvod WHERE id=?");
        ukloniProizvodUpit = conn.prepareStatement("DELETE FROM proizvod WHERE id=?");
        dodajProizvodUpit = conn.prepareStatement("INSERT INTO proizvod VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        azurirajProizvodUpit = conn.prepareStatement("UPDATE proizvod SET name=?,id=?,price=?,quantity=?," +
                "purpose=?,notes=?,administrationMethod=?,manufacturer=?,description=?,ingredients=?,type=? WHERE id=?");
        promjenaKolicineUpit = conn.prepareStatement("UPDATE proizvod SET quantity=? WHERE id=?");
    }

    private void kreirajBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("apoteka.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        }catch(FileNotFoundException e){
            System.out.println("Ne postoji SQL datoteka, nastavljam sa praznom bazom");
        }
    }

    public static ProductDAO getInstance() throws SQLException {
        if(instance==null) instance=new ProductDAO();
        return instance;
    }

    public ArrayList<Product> pretraga(String s) {
        ArrayList<Product> rezultat = new ArrayList<>();
        try {
            pretragaUpit.setString(1,s);
            ResultSet rs = pretragaUpit.executeQuery();
            while(rs.next()){
                String name = rs.getString(1);
                String id = rs.getString(2);
                Double price=rs.getDouble(3);
                int quantity=rs.getInt(4);
                String purpose=rs.getString(5);
                String notes=rs.getString(6);
                String administrationMethod=rs.getString(7);
                String manufacturer= rs.getString(8);
                String description=rs.getString(9);
                String ingredients=rs.getString(10);
                String medicationType=rs.getString(11);
                Product p = new Product(name,id,price,quantity,purpose,notes,administrationMethod,manufacturer,description,ingredients,medicationType);
                rezultat.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezultat;
    }

    public ObservableList<Product> getProducts(){
        ObservableList<Product> rezultat = FXCollections.observableArrayList();
        try {
            ResultSet rs = sviProizvodiUpit.executeQuery();
            while(rs.next()){
                String name = rs.getString(1);
                String id = rs.getString(2);
                Double price=rs.getDouble(3);
                int quantity=rs.getInt(4);
                String purpose=rs.getString(5);
                String notes=rs.getString(6);
                String administrationMethod=rs.getString(7);
                String manufacturer= rs.getString(8);
                String description=rs.getString(9);
                String ingredients=rs.getString(10);
                String medicationType=rs.getString(11);
                Product p = new Product(name,id,price,quantity,purpose,notes,administrationMethod,manufacturer,description,ingredients,medicationType);
                rezultat.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezultat;
    }

    public void addProduct(Product p){
        try {
            dodajProizvodUpit.setString(1, p.getName());
            dodajProizvodUpit.setString(2, p.getID());
            dodajProizvodUpit.setDouble(3, p.getPrice());
            dodajProizvodUpit.setInt(4, p.getQuantity());
            dodajProizvodUpit.setString(5, p.getPurpose());
            dodajProizvodUpit.setString(6, p.getNotes());
            dodajProizvodUpit.setString(7, p.getAdministrationMethod());
            dodajProizvodUpit.setString(8, p.getManufacturer());
            dodajProizvodUpit.setString(9, p.getDescription());
            dodajProizvodUpit.setString(10, p.getIngredients());
            dodajProizvodUpit.setString(11, p.getMedicationType());

            dodajProizvodUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
    public void updateProduct(Product p,String index){
        try {
            azurirajProizvodUpit.setString(1, p.getName());
            azurirajProizvodUpit.setString(2, p.getID());
            azurirajProizvodUpit.setDouble(3, p.getPrice());
            azurirajProizvodUpit.setInt(4, p.getQuantity());
            azurirajProizvodUpit.setString(5, p.getPurpose());
            azurirajProizvodUpit.setString(6, p.getNotes());
            azurirajProizvodUpit.setString(7, p.getAdministrationMethod());
            azurirajProizvodUpit.setString(8, p.getManufacturer());
            azurirajProizvodUpit.setString(9, p.getDescription());
            azurirajProizvodUpit.setString(10, p.getIngredients());
            azurirajProizvodUpit.setString(11, p.getMedicationType());
            azurirajProizvodUpit.setString(12,index);
            azurirajProizvodUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom ažuriranja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
    public void removeProduct(Product p) {
        try {
            ukloniProizvodUpit.setString(1, p.getID());
            ukloniProizvodUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom brisanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void reduceQuantity(Product p,int kolicina){
        try {
            promjenaKolicineUpit.setInt(1, kolicina);
            promjenaKolicineUpit.setString(2,p.getID());
            promjenaKolicineUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom ažuriranja količine proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
}
