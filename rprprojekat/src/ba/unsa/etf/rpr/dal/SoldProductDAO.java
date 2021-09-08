package ba.unsa.etf.rpr.dal;

import ba.unsa.etf.rpr.SoldProduct;
import ba.unsa.etf.rpr.SqliteHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SoldProductDAO {
    private static SoldProductDAO instance = null;
    private final Connection conn;
    private PreparedStatement allSoldProductsQuery;
    private final PreparedStatement addSoldQuery;
    private final PreparedStatement getMaxIDQuery;

    private SoldProductDAO() throws SQLException {
        String url = "jdbc:sqlite:apoteka.db";
        conn = SqliteHelper.getConn();
        try {
            allSoldProductsQuery = conn.prepareStatement("SELECT * FROM prodani");
        } catch (SQLException e) {
            kreirajBazu();
            allSoldProductsQuery = conn.prepareStatement("SELECT * FROM prodani");
        }
        addSoldQuery = conn.prepareStatement("INSERT INTO prodani VALUES(?,?,?,?,?,?)");
        getMaxIDQuery = conn.prepareStatement("SELECT IFNULL(MAX(idSold),0) FROM prodani");
    }

    private void kreirajBazu() {
        Scanner ulaz;
        try {
            ulaz = new Scanner(new FileInputStream("apoteka.db.sql"));
            StringBuilder sqlUpit = new StringBuilder();
            while (ulaz.hasNext()) {
                sqlUpit.append(ulaz.nextLine());
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit.toString());
                        sqlUpit = new StringBuilder();
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

    public void dodajProdani(SoldProduct p){
        try {
            addSoldQuery.setInt(1, p.getIdSold());
            addSoldQuery.setInt(2, p.getId());
            addSoldQuery.setString(3, p.getName());
            addSoldQuery.setInt(4,p.getQuantity());
            addSoldQuery.setString(5, p.getSellerName());
            addSoldQuery.setString(6, p.getDate());

            addSoldQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja prodanog proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
    public int getMaxID(){
        int max = 0;
        try {
            ResultSet rs = getMaxIDQuery.executeQuery();
            max = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u max upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return max;
    }

    private Integer idSold;
    private Integer id;
    private String name;
    private Integer quantity;
    private String sellerName;
    private String date;

    public ArrayList<SoldProduct> getAllSoldProducts() {
        ArrayList<SoldProduct> result = new ArrayList<>();
        try {
            ResultSet rs = allSoldProductsQuery.executeQuery();
            while (rs.next()) {
                Integer idSold = rs.getInt(1);
                Integer id = rs.getInt(2);
                String name = rs.getString(3);
                Integer quantity = rs.getInt(4);
                String sellerName = rs.getString(5);
                String date = rs.getString(6);
                SoldProduct p = new SoldProduct(idSold, id, name, quantity, sellerName, date);
                result.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

}
