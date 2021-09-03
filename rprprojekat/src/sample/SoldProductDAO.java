package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class SoldProductDAO {
    private static SoldProductDAO instance=null;
    private Connection conn;
    private PreparedStatement sviProdaniProizvodiUpit,prodaniNaDatumUpit,dodajProdaniUpit;

    private SoldProductDAO() throws SQLException {
        //String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/.apotekaapp/apoteka.db";
        String url = "jdbc:sqlite:apoteka.db";
        conn = SqliteHelper.getConn();
        try {
            sviProdaniProizvodiUpit = conn.prepareStatement("SELECT * FROM prodani");
        }
        catch(SQLException e){
            kreirajBazu();
            sviProdaniProizvodiUpit = conn.prepareStatement("SELECT * FROM prodani");
        }
        prodaniNaDatumUpit = conn.prepareStatement("SELECT * FROM prodani WHERE date=?");
        dodajProdaniUpit = conn.prepareStatement("INSERT INTO prodani VALUES(?,?,?,?)");
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

    /*public ObservableList<SoldProduct> getSoldProducts(){
        ObservableList<Product> rezultat = FXCollections.observableArrayList();
        try {
            ResultSet rs = sviProdaniProizvodiUpit.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                String seller=rs.getString(3);
                String date=rs.getString(4);

                SoldProduct p = new SoldProduct(id,name,seller,date);
                rezultat.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezultat;
    }*/

   /* public ObservableList<Product> getSoldProductsByDate(LocalDateTime d){//format 2021-mjesec-danTsat:minuta:sekunda
        ObservableList<Product> rezultat = FXCollections.observableArrayList();
        try {
            ResultSet rs = prodaniNaDatumUpit.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                String seller=rs.getString(3);
                String date=rs.getString(4);

                SoldProduct p = new SoldProduct(id,name,seller,date);
                rezultat.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rezultat;
    }*/

    public void dodajProdani(SoldProduct p){
        try {
            dodajProdaniUpit.setInt(1, p.getID());
            dodajProdaniUpit.setString(2, p.getName());
            dodajProdaniUpit.setString(3, p.getSellerName());
            dodajProdaniUpit.setString(4, p.getDate());

            dodajProdaniUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja prodanog proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
}
