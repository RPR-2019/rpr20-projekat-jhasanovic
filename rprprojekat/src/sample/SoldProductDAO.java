package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SoldProductDAO {
    private static SoldProductDAO instance=null;
    private Connection conn;
    private PreparedStatement sviProdaniProizvodiUpit;

    private SoldProductDAO() throws SQLException {
        //String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/.apotekaapp/apoteka.db";
        String url = "jdbc:sqlite:apoteka.db";
        conn = DriverManager.getConnection(url);
        try {
            sviProdaniProizvodiUpit = conn.prepareStatement("SELECT * FROM prodani");
        }
        catch(SQLException e){
            kreirajBazu();
            sviProdaniProizvodiUpit = conn.prepareStatement("SELECT * FROM prodani");
        }

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

    public static SoldProductDAO getInstance() throws SQLException {
        if(instance==null) instance=new SoldProductDAO();
        return instance;
    }

    /*public ArrayList<Product> pretraga(String s) {
        ArrayList<Product> rezultat = new ArrayList<>();
        try {
            pretragaUpit.setString(1,s);
            ResultSet rs = pretragaUpit.executeQuery();
            while(rs.next()){
                String name = rs.getString(1);
                String id = rs.getString(2);
                int price=rs.getInt(3);
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
                int price=rs.getInt(3);
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
            dodajProizvodUpit.setInt(3, p.getPrice());
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

    public void removeProduct(Product p) {
        try {
            ukloniProizvodUpit.setString(1, p.getID());
            ukloniProizvodUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom brisanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }*/
}
