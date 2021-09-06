package dal;

import sample.SoldProduct;
import sample.SqliteHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class SoldProductDAO {
    private static SoldProductDAO instance=null;
    private Connection conn;
    private PreparedStatement sviProdaniProizvodiUpit,prodaniNaDatumUpit,dodajProdaniUpit,getMaxIDUpit,istiDatumISellerUpit,
    updateSoldProductUpit,dajKolicinuUpit;

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
        dodajProdaniUpit = conn.prepareStatement("INSERT INTO prodani VALUES(?,?,?,?,?,?)");
        getMaxIDUpit = conn.prepareStatement("SELECT IFNULL(MAX(idSold),0) FROM prodani");
        istiDatumISellerUpit = conn.prepareStatement("SELECT COUNT(*) from prodani WHERE seller=? AND date=?");
        updateSoldProductUpit = conn.prepareStatement("UPDATE prodani SET quantity=? WHERE id=?");
        dajKolicinuUpit = conn.prepareStatement("SELECT quantity FROM prodani WHERE id=?");
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
            dodajProdaniUpit.setInt(1, p.getIdSold());
            dodajProdaniUpit.setInt(2, p.getID());
            dodajProdaniUpit.setString(3, p.getName());
            dodajProdaniUpit.setInt(4,p.getQuantity());
            dodajProdaniUpit.setString(5, p.getSellerName());
            dodajProdaniUpit.setString(6, p.getDate());

            dodajProdaniUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja prodanog proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
    public int getMaxID(){
        int max = 0;
        try {
            ResultSet rs = getMaxIDUpit.executeQuery();
            max = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u max upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return max;
    }

    public int getIstiDatumISellerCount(String seller,String date){
        int count = 0;
        try {
            istiDatumISellerUpit.setString(1,seller);
            istiDatumISellerUpit.setString(2,date);
            ResultSet rs = istiDatumISellerUpit.executeQuery();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u count upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return count;
    }

    public void updateSoldProduct(Integer quantity,Integer id){
        try {
            updateSoldProductUpit.setInt(1, quantity);
            updateSoldProductUpit.setInt(2, id);
            updateSoldProductUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom ažuriranja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public int getQuantity(Integer id){//vraca kolicinu proizvoda
        int quantity=0;
        try {
            dajKolicinuUpit.setInt(1, id);

            ResultSet rs = dajKolicinuUpit.executeQuery();
            quantity = rs.getInt(1);
            System.out.println(quantity);

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dohvaćanja količine\nIzuzetak: " + sqlException.getMessage());
        }

        return quantity;
    }

}
