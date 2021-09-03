package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CartDAO {
    private static CartDAO instance=null;
    private Connection conn;
    private PreparedStatement sviProizvodiUpit,ukloniProizvodUpit,dodajUKorpuUpit,azurirajKorpuUpit,dajKolicinuUpit,
            isprazniKorpuUpit,dajTotalUpit;

    private CartDAO() throws SQLException {
        //String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/.apotekaapp/apoteka.db";
        String url = "jdbc:sqlite:apoteka.db";
        conn = SqliteHelper.getConn();
        try {
            sviProizvodiUpit = conn.prepareStatement("SELECT * FROM korpa");
        }
        catch(SQLException e){
            kreirajBazu();
            sviProizvodiUpit = conn.prepareStatement("SELECT * FROM korpa");
        }
        ukloniProizvodUpit = conn.prepareStatement("DELETE FROM korpa WHERE id=?");
        dodajUKorpuUpit = conn.prepareStatement("INSERT INTO korpa VALUES(?,?,?,?)");
        azurirajKorpuUpit = conn.prepareStatement("UPDATE korpa SET quantity=? WHERE id=?");
        dajKolicinuUpit = conn.prepareStatement("SELECT quantity FROM korpa WHERE id=?");
        isprazniKorpuUpit = conn.prepareStatement("DELETE FROM korpa");
        dajTotalUpit = conn.prepareStatement("SELECT SUM(price) FROM korpa");
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

    public static CartDAO getInstance() throws SQLException {
        if(instance==null) instance=new CartDAO();
        return instance;
    }

    public ObservableList<CartProduct> getProducts(){
        ObservableList<CartProduct> rezultat = FXCollections.observableArrayList();
        try {
            ResultSet rs = sviProizvodiUpit.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer quantity=rs.getInt(3);
                Double price=rs.getDouble(4);
                CartProduct p = new CartProduct(id,name,quantity,price);
                rezultat.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezultat;
    }
    public ArrayList<CartProduct> getProductsArrayList(){
        ArrayList<CartProduct> rezultat = new ArrayList<>();
        try {
            ResultSet rs = sviProizvodiUpit.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer quantity=rs.getInt(3);
                Double price=rs.getDouble(4);
                CartProduct p = new CartProduct(id,name,quantity,price);
                rezultat.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezultat;
    }


    public void addProductToCart(CartProduct p){
        try {

            dodajUKorpuUpit.setInt(1, p.getID());
            dodajUKorpuUpit.setString(2, p.getName());
            dodajUKorpuUpit.setInt(3, p.getQuantity());
            dodajUKorpuUpit.setDouble(4, p.getPrice());

            dodajUKorpuUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void updateCart(CartProduct p,Integer quantity){
        try {
            azurirajKorpuUpit.setInt(1, quantity);
            azurirajKorpuUpit.setInt(2, p.getID());

            azurirajKorpuUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom ažuriranja korpe\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public int getQuantity(CartProduct p){//vraca kolicinu proizvoda u korpi
        int quantity=0;
        try {
            dajKolicinuUpit.setInt(1, p.getID());

           ResultSet rs = dajKolicinuUpit.executeQuery();
           quantity = rs.getInt(1);

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dohvaćanja količine\nIzuzetak: " + sqlException.getMessage());
        }
        return quantity;
    }

    public int dajTotal(){//vraca ukupnu cijenu korpe
        int total=0;
        try {
            ResultSet rs = dajTotalUpit.executeQuery();
            total = rs.getInt(1);

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dohvaćanja količine\nIzuzetak: " + sqlException.getMessage());
        }
        return total;
    }

    public void isprazni() {
        try {
            isprazniKorpuUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom brisanja podataka iz korpe\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void removeProduct(CartProduct p) {
        try {
            ukloniProizvodUpit.setInt(1, p.getID());
            ukloniProizvodUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom brisanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
}
